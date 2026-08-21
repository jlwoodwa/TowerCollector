/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.io.network;

public class RequestException extends RuntimeException {

    private static final String MESSAGE = "Server rejected the upload request";

    // The server reply body can echo the uploaded measurements, and this exception is
    // reported off-device by ACRA. It carries no response data at all: callers log the
    // body locally with Timber before constructing this.
    public RequestException() {
        super(MESSAGE);
    }
}
