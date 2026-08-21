/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.dao;

public class MeasurementInsertionFailedException extends RuntimeException {

    private static final long serialVersionUID = -6166195022983337711L;

    public MeasurementInsertionFailedException(String detailMessage, String data) {
        super(detailMessage + " [data=" + data + "]");
    }
}
