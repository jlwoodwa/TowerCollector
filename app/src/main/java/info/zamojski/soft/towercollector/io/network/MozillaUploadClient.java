/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.io.network;

import static info.zamojski.soft.towercollector.PrivacyAccessIds.CollectorService_LocationListener_PreciseLoc;
import static info.zamojski.soft.towercollector.PrivacyAccessIds.CollectorService_NetMonsterListener_ApproxLoc;
import static info.zamojski.soft.towercollector.PrivacyAccessIds.CollectorService_registerApi_ApproxLoc;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import info.zamojski.soft.towercollector.BuildConfig;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public class MozillaUploadClient extends ClientBase implements IUploadClient {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final String uploadUrl;

    public MozillaUploadClient(String url) {
        this.uploadUrl = url;
    }

    @Override
    public RequestResult uploadMeasurements(String content) {
        Timber.d("uploadMeasurements(): Sending post request");
        try {
            OkHttpClient client = new ExtendedOkHttpClientBuilder()
                    .newBuilder()
                    .connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();

            // add json as request content
            RequestBody requestBody = RequestBody.create(content, JSON);
            Request request = new Request.Builder()
                    .url(uploadUrl)
                    .header("User-Agent", "TowerCollector/" + BuildConfig.VERSION_NAME)
                    .post(requestBody)
                    .build();

            @DataTransmission(
                    accessId = {CollectorService_registerApi_ApproxLoc, CollectorService_NetMonsterListener_ApproxLoc, CollectorService_LocationListener_PreciseLoc},
                    collectionAttribute = {TransmittedOffDevice.True, CollectedFor.AppFunctionality, CollectedFor.Analytics, OptionalCollection.False, NotStoredInBackend.False, EncryptionInTransit.False, UserRequestDelete.False, UserToUserEncryption.False},
                    sharingAttribute = {SharedWithThirdParty.True, SharedFor.AppFunctionality, SharedFor.Analytics, OnlySharedWithServiceProviders.False, OnlySharedForLegalPurposes.False, OnlyInitiatedByUser.False, OnlyAfterGettingUserConsent.False, OnlyTransferringAnonymousData.False})
            Response response = client.newCall(request).execute();
            return handleResponse(response.code(), response.body().string());
        } catch (SocketTimeoutException | ConnectException ex) {
            Timber.d(ex, "uploadMeasurements(): Timeout encountered");
            return RequestResult.ConnectionError;
        } catch (IOException ex) {
            Timber.e(ex, "uploadMeasurements(): Errors encountered");
            reportExceptionWithSuppress(ex);
            return RequestResult.Failure;
        }
    }

    private RequestResult handleResponse(int code, String body) {
        if (code >= 200 && code <= 299) {
            return RequestResult.Success;
        }
        if (code >= 500 && code <= 599) {
            return RequestResult.ServerError;
        }
        if (code == 400) {
            RuntimeException ex = new RequestException(body);
            reportException(ex);
            return RequestResult.ConfigurationError;
        }
        if (code == 403) {
            RuntimeException ex = new RequestException(body);
            reportException(ex);
            return RequestResult.LimitExceeded;
        }
        // don't report captive portals
        if (code != 302) {
            RuntimeException ex = new RequestException(body);
            reportException(ex);
        }
        return RequestResult.ConnectionError;
    }
}
