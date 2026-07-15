/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.model;

import static info.zamojski.soft.towercollector.PrivacyAccessIds.MeasurementBase_PreciseLoc_Latitude;
import static info.zamojski.soft.towercollector.PrivacyAccessIds.MeasurementBase_PreciseLoc_Longitude;

import android.content.Context;

import java.util.List;

import info.zamojski.soft.towercollector.utils.NetworkTypeUtils;
import me.tianshili.annotationlib.DataAccess;
import me.tianshili.annotationlib.DataType;

public abstract class MeasurementBase {

    /**
     * Geographic Latitude.
     */
    @DataAccess(
            id = MeasurementBase_PreciseLoc_Latitude,
            dataType = {DataType.Location_PreciseLocation})
    protected double latitude;
    /**
     * Geographic Longitude.
     */
    @DataAccess(
            id = MeasurementBase_PreciseLoc_Longitude,
            dataType = {DataType.Location_PreciseLocation})
    protected double longitude;
    /**
     * Measured at Unix Timestamp with milliseconds.
     */
    protected long measuredAt;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(long measuredAt) {
        this.measuredAt = measuredAt;
    }

    protected String getDescription(Context context, List<? extends CellBase> cells, String lineSeparator) {
        boolean appendNewLine = false;
        StringBuilder sb = new StringBuilder();
        for (CellBase c : cells) {
            if (appendNewLine) {
                sb.append(lineSeparator);
            } else {
                appendNewLine = true;
            }
            sb.append(context.getString(NetworkTypeUtils.getNetworkGroupNameResId(c.getNetworkType())))
                    .append(": ");
            if (c.getMcc() != Cell.UNKNOWN_CID)
                sb.append(c.getMcc())
                        .append('-');
            sb.append(c.getMnc())
                    .append('-')
                    .append(c.getLac())
                    .append('-')
                    .append(c.getCid());
            if (c.isCidLong()) {
                sb.append(" (")
                        .append(c.getShortCid())
                        .append('-')
                        .append(c.getRnc())
                        .append(')');
            }
        }
        return sb.toString();
    }
}
