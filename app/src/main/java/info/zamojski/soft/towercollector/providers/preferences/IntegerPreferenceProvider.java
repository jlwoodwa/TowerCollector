/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.providers.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;
import org.checkerframework.checker.PNL.qual.Label;
import org.checkerframework.checker.PNL.qual.Sink;
import org.checkerframework.checker.PNL.qual.Source;


public class IntegerPreferenceProvider extends PreferenceProviderBase<@Label(sources = {Source.Approximate_location}, sinks = {Sink.Ephemerally_processed, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Shared_App_functionality, Sink.Shared_Analytics}) Integer> {

    public IntegerPreferenceProvider(Context context) {
        super(context);
    }

    @Override
    Integer getPreferenceDefaultValue(@IntegerRes int defaultValueKey) {
        Resources resources = context.getResources();
        return resources.getInteger(defaultValueKey);
    }

    @Override
    Integer getPreferenceValue(SharedPreferences prefs, @StringRes int valueKey, Integer defaultValue) {
        String key = context.getString(valueKey);
        return prefs.getInt(key, defaultValue);
    }

    @Override
    void setPreferenceValue(Editor editor, @StringRes int valueKey, Integer value) {
        String key = context.getString(valueKey);
        editor.putInt(key, value);
    }

}
