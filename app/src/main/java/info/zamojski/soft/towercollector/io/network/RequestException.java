/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.io.network;

import timber.log.Timber;

public class RequestException extends RuntimeException {

    private static final String MESSAGE = "Server rejected the upload request";

    public RequestException(String responseBody) {
        // The reply body can echo the uploaded measurements, and this exception is reported
        // off-device by ACRA. Keep the reported message constant; log the body locally only.
        super(MESSAGE);
        Timber.d("RequestException(): Server response: %s", responseBody);
    }
}
