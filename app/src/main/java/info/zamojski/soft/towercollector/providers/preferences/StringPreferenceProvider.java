/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.providers.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import androidx.annotation.StringRes;
import org.checkerframework.checker.PNL.qual.Label;
import org.checkerframework.checker.PNL.qual.Sink;

public class StringPreferenceProvider extends PreferenceProviderBase<@Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) String> {

    public StringPreferenceProvider(Context context) {
        super(context);
    }

    @Override
    String getPreferenceDefaultValue(@StringRes int defaultValueKey) {
        Resources resources = context.getResources();
        return resources.getString(defaultValueKey);
    }

    @Override
    String getPreferenceValue(SharedPreferences prefs, @StringRes int valueKey, String defaultValue) {
        String key = context.getString(valueKey);
        return prefs.getString(key, defaultValue);
    }

    @Override
    void setPreferenceValue(Editor editor, @StringRes int valueKey, String value) {
        String key = context.getString(valueKey);
        editor.putString(key, value);
    }

}
