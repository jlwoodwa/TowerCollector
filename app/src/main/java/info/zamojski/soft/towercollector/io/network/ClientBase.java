/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.io.network;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import info.zamojski.soft.towercollector.MyApplication;

public abstract class ClientBase {

    protected static final int CONN_TIMEOUT = 30000;
    protected static final int READ_TIMEOUT = 30000;

    protected void reportExceptionWithSuppress(IOException ex) {
        Throwable originalException = ex.getCause();
        // suppress known exceptions
        if (isSuppressed(ex) || isSuppressed(originalException)) {
            return;
        }
        reportException(ex);
    }

    /**
     * Reports a failed upload request to ACRA, which transmits off-device.
     * <p>
     * Callers must pass the exception <em>directly</em> ({@code reportException(new
     * RequestException())}) and never park it in a local first: a local declared inside a branch
     * on the server's response code has that branch's pc joined into its label, so the (otherwise
     * constant, response-free) exception arrives here carrying the upload's own
     * {Approximate_location, Precise_location} label and fails handleSilentException's
     * declaration. The same {@code new} expression passed as an argument is not pc-joined.
     * Fixtures: Reports/artifacts/fixtures/clientbase/CB6.java (repro) and CB7.java (fix).
     */
    protected void reportException(Exception ex) {
        MyApplication.handleSilentException(ex);
    }

    private boolean isSuppressed(Throwable throwable) {
        return (throwable instanceof UnknownHostException
                || throwable instanceof SocketTimeoutException
                || throwable instanceof SocketException
                || throwable instanceof SSLException
                || throwable instanceof EOFException);
    }
}
