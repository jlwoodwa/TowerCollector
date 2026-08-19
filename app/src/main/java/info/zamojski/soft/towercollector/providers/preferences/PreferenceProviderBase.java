/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.providers.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;

import info.zamojski.soft.towercollector.MyApplication;
import org.checkerframework.checker.PNL.Begin;
import org.checkerframework.checker.PNL.PolyBegin;
import org.checkerframework.checker.PNL.qual.Label;
import org.checkerframework.checker.PNL.qual.Sink;
import org.checkerframework.checker.PNL.qual.Source;
import org.checkerframework.checker.PNL.qual.Tag;
import timber.log.Timber;

abstract class PreferenceProviderBase<T> {

    protected Context context;

    PreferenceProviderBase(Context context) {
        this.context = context;
    }

    @Begin(sources = {Source.Approximate_location, Source.Precise_location, Source.Name, Source.Email, Source.User_ID, Source.Address, Source.Phone, Source.Race_And_Ethnicity, Source.Policical_or_religiousBeliefs, Source.Sexual_orientation, Source.other_personal_info, Source.Payment_info, Source.Purchase_history, Source.credit_score, Source.other_finincial_info, Source.photos_and_videos, Source.Audio, Source.Contacts, Source.App_interactions, Source.In_app_search_history, Source.installed_apps, Source.other_user_gen_content, Source.web_browsing_history, Source.calendar_events, Source.fittness_info, Source.Device_IDs, Source.Emails_messages, Source.SMS, Source.in_app_messages, Source.files_and_docs}, sinks = {}, tag = Tag.OPTIONAL)
    public @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T getPreference(@StringRes int valueKey, int defaultValueKey) {
        return getPreference(valueKey, defaultValueKey, true);
    }

    /**
     * Pure read: never writes. A preference whose stored type does not match falls back to the
     * default here; repairing the stored value is {@link #resetPreferenceToDefault}'s job, so that
     * this method has no side effect and may therefore be called from any context.
     */
    @Begin(sources = {Source.Approximate_location, Source.Precise_location, Source.Name, Source.Email, Source.User_ID, Source.Address, Source.Phone, Source.Race_And_Ethnicity, Source.Policical_or_religiousBeliefs, Source.Sexual_orientation, Source.other_personal_info, Source.Payment_info, Source.Purchase_history, Source.credit_score, Source.other_finincial_info, Source.photos_and_videos, Source.Audio, Source.Contacts, Source.App_interactions, Source.In_app_search_history, Source.installed_apps, Source.other_user_gen_content, Source.web_browsing_history, Source.calendar_events, Source.fittness_info, Source.Device_IDs, Source.Emails_messages, Source.SMS, Source.in_app_messages, Source.files_and_docs}, sinks = {}, tag = Tag.OPTIONAL)
    @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T getPreference(@StringRes int valueKey, int defaultValueKey, boolean loggerEnabled) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T defaultValue = getPreferenceDefaultValue(defaultValueKey);
        try {
            @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T value = getPreferenceValue(prefs, valueKey, defaultValue);
            if (loggerEnabled)
                Timber.d("getPreference(): Preference `%s` loaded with value `%s`", context.getString(valueKey), value);
            return value;
        } catch (ClassCastException ex) {
            if (loggerEnabled)
                Timber.e(ex, "getPreference(): Error while loading preference `%s`, returning default", context.getString(valueKey));
//            MyApplication.handleSilentException(ex);
            return defaultValue;
        }
    }

    /**
     * Pure write: persists a preference's default value, repairing a stored value whose type does
     * not match. Split out of {@link #getPreference} so that reading a preference has no side
     * effect; call this explicitly where a repair is wanted.
     */
    @PolyBegin
    public void resetPreferenceToDefault(@StringRes int valueKey, @IntegerRes int defaultValueKey) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        setPreferenceValue(editor, valueKey, getPreferenceDefaultValue(defaultValueKey));
        editor.apply();
    }

    @PolyBegin
    public void setPreference(@StringRes int valueKey, @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T value) {
        Timber.d("setPreference(): Preference `%s` value set to `%s`", context.getString(valueKey), value);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        setPreferenceValue(editor, valueKey, value);
        editor.apply();
    }

    @Begin(sources = {Source.Approximate_location, Source.Precise_location, Source.Name, Source.Email, Source.User_ID, Source.Address, Source.Phone, Source.Race_And_Ethnicity, Source.Policical_or_religiousBeliefs, Source.Sexual_orientation, Source.other_personal_info, Source.Payment_info, Source.Purchase_history, Source.credit_score, Source.other_finincial_info, Source.photos_and_videos, Source.Audio, Source.Contacts, Source.App_interactions, Source.In_app_search_history, Source.installed_apps, Source.other_user_gen_content, Source.web_browsing_history, Source.calendar_events, Source.fittness_info, Source.Device_IDs, Source.Emails_messages, Source.SMS, Source.in_app_messages, Source.files_and_docs}, sinks = {}, tag = Tag.OPTIONAL)
    abstract @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T getPreferenceDefaultValue(@IntegerRes int defaultValueKey);

    @Begin(sources = {Source.Approximate_location, Source.Precise_location, Source.Name, Source.Email, Source.User_ID, Source.Address, Source.Phone, Source.Race_And_Ethnicity, Source.Policical_or_religiousBeliefs, Source.Sexual_orientation, Source.other_personal_info, Source.Payment_info, Source.Purchase_history, Source.credit_score, Source.other_finincial_info, Source.photos_and_videos, Source.Audio, Source.Contacts, Source.App_interactions, Source.In_app_search_history, Source.installed_apps, Source.other_user_gen_content, Source.web_browsing_history, Source.calendar_events, Source.fittness_info, Source.Device_IDs, Source.Emails_messages, Source.SMS, Source.in_app_messages, Source.files_and_docs}, sinks = {}, tag = Tag.OPTIONAL)
    abstract @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T getPreferenceValue(SharedPreferences prefs, @StringRes int valueKey, @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T defaultValue);

    @PolyBegin
    abstract void setPreferenceValue(SharedPreferences.Editor editor, @StringRes int valueKey, @Label(sources = {}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.User_can_request_deletion, Sink.Sh_for_legal_reasons, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Collected_Developer_communications, Sink.Collected_Advertising_or_marketing, Sink.Collected_Security_and_compliance, Sink.Collected_Personalization, Sink.Collected_Account_management, Sink.Shared_App_functionality, Sink.Shared_Analytics, Sink.Shared_Developer_communications, Sink.Shared_Advertising_or_marketing, Sink.Shared_Security_and_compliance, Sink.Shared_Personalization, Sink.Shared_Account_management, Sink.Shared_with_service_providers}) T value);
}
