/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.io.network;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import static info.zamojski.soft.towercollector.PrivacyAccessIds.*;
import info.zamojski.soft.towercollector.io.network.compatibility.ExtendedOkHttpClientBuilder;
import me.tianshili.annotationlib.DataTransmission;
import me.tianshili.annotationlib.collectionAttribute.CollectedFor;
import me.tianshili.annotationlib.collectionAttribute.EncryptionInTransit;
import me.tianshili.annotationlib.collectionAttribute.NotStoredInBackend;
import me.tianshili.annotationlib.collectionAttribute.OptionalCollection;
import me.tianshili.annotationlib.collectionAttribute.TransmittedOffDevice;
import me.tianshili.annotationlib.collectionAttribute.UserRequestDelete;
import me.tianshili.annotationlib.collectionAttribute.UserToUserEncryption;
import me.tianshili.annotationlib.sharingAttribute.OnlyAfterGettingUserConsent;
import me.tianshili.annotationlib.sharingAttribute.OnlyInitiatedByUser;
import me.tianshili.annotationlib.sharingAttribute.OnlySharedForLegalPurposes;
import me.tianshili.annotationlib.sharingAttribute.OnlySharedWithServiceProviders;
import me.tianshili.annotationlib.sharingAttribute.OnlyTransferringAnonymousData;
import me.tianshili.annotationlib.sharingAttribute.SharedFor;
import me.tianshili.annotationlib.sharingAttribute.SharedWithThirdParty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public class OcidUploadClient extends ClientBase implements IUploadClient {

    private static final MediaType CSV = MediaType.parse("text/csv");

    private final String url;
    private final String appId;
    private final String apiKey;
    private long fileSize;

    public OcidUploadClient(String url, String appId, String apiKey) {
        this.url = url;
        this.appId = appId;
        this.apiKey = apiKey;
    }

    @Override
    public RequestResult uploadMeasurements(String content) {
        return uploadMeasurementsEncrypted(content);
    }

    private RequestResult uploadMeasurementsEncrypted(String content) {
        Timber.d("uploadMeasurementsEncrypted(): Sending encrypted post request");
        return uploadMeasurementsCommon(new ExtendedOkHttpClientBuilder().newBuilder(), url, content);
    }

    private RequestResult uploadMeasurementsCommon(OkHttpClient.Builder clientBuilder, String url, String content) {
        try {
            OkHttpClient client = clientBuilder
                    .connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();

            RequestBody dataFile = RequestBody.create(content, CSV);
            this.fileSize = dataFile.contentLength();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("key", apiKey)
                    .addFormDataPart("appId", appId)
                    .addFormDataPart("datafile", "TowerCollector_measurements_" + System.currentTimeMillis() + ".csv", dataFile)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            @DataTransmission(
                    accessId = {CollectorService_registerApi_ApproxLoc, CollectorService_NetMonsterListener_ApproxLoc, CollectorService_LocationListener_PreciseLoc, CollectorService_telephonyTriple},
                    collectionAttribute = {TransmittedOffDevice.True, CollectedFor.AppFunctionality, CollectedFor.Analytics, OptionalCollection.False, NotStoredInBackend.False, EncryptionInTransit.False, UserRequestDelete.False, UserToUserEncryption.False},
                    sharingAttribute = {SharedWithThirdParty.True, SharedFor.AppFunctionality, SharedFor.Analytics, OnlySharedWithServiceProviders.False, OnlySharedForLegalPurposes.False, OnlyInitiatedByUser.False, OnlyAfterGettingUserConsent.False, OnlyTransferringAnonymousData.False})
            Response response = client.newCall(request).execute();
            return handleResponse(response.code(), response.body().string());
        } catch (SocketTimeoutException | ConnectException ex) {
            Timber.d(ex, "uploadMeasurements(): Timeout encountered");
            return RequestResult.ConnectionError;
        } catch (IOException ex) {
            Timber.d(ex, "uploadMeasurements(): Errors encountered");
            reportExceptionWithSuppress(ex);
            return RequestResult.Failure;
        }
    }

    private RequestResult handleResponse(int code, String body) {
        body = (body == null ? "" : body.trim());

        if (code == 200 && "0,OK".equalsIgnoreCase(body)) {
            return RequestResult.Success;
        }
        if (code >= 500 && code <= 599) {
            return RequestResult.ServerError;
        }
        if (code == 401 || code == 403 || "Err: Invalid token".equalsIgnoreCase(body)) {
            return RequestResult.InvalidApiKey;
        }
        if (code == 400) {
            Timber.d("handleResponse(): Server rejected the upload request: %s", body);
            RuntimeException ex = new RequestException();
            reportException(ex);
            return RequestResult.ConfigurationError;
        }
        // don't report captive portals
        if (code != 302) {
            if (body.equalsIgnoreCase("Exceeded filesize limit."))
                body += ". Actual size=" + fileSize + " bytes.";
            Timber.d("handleResponse(): Server rejected the upload request: %s", body);
            reportException(new RequestException());
        }
        return RequestResult.ConnectionError;
    }
}
