/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.broadcast;

import org.checkerframework.checker.PNL.qual.Label;
import org.checkerframework.checker.PNL.qual.Sink;
import org.checkerframework.checker.PNL.qual.Source;

import android.content.Intent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.Collections;
import java.util.List;

import info.zamojski.soft.towercollector.MyApplication;
import info.zamojski.soft.towercollector.events.MeasurementsCollectedEvent;
import info.zamojski.soft.towercollector.files.formatters.json.IJsonFormatter;
import info.zamojski.soft.towercollector.files.formatters.json.JsonBroadcastFormatter;
import info.zamojski.soft.towercollector.model.Measurement;
import timber.log.Timber;

public class ExternalBroadcastSender implements Runnable {

    private static final String MEASUREMENTS_COLLECTED_ACTION = "info.zamojski.soft.towercollector.MEASUREMENTS_COLLECTED";
    private static final String MEASUREMENTS_EXTRA_KEY = "measurements";

    private IJsonFormatter formatter;

    private void sendMeasurementsCollectedBroadcast(Measurement measurement) {
        Timber.i("sendMeasurementsCollectedBroadcast(): Sending broadcast to external apps");
        if (formatter == null) {
            formatter = new JsonBroadcastFormatter();
        }
        try {
            String extra = formatter.formatList(Collections.<@Label(sources = {Source.Approximate_location, Source.Precise_location}, sinks = {Sink.Ephemerally_processed, Sink.User_to_user_encrypted, Sink.Encrypted_in_transit, Sink.Sh_initiated_by_user, Sink.Sh_only_with_consent, Sink.Only_transfer_anonymous_data, Sink.Sh_with_service_providers, Sink.Collected_App_functionality, Sink.Collected_Analytics, Sink.Shared_App_functionality, Sink.Shared_Analytics}) Measurement>singletonList(measurement));
            // Send broadcast
            Intent intent = new Intent();
            intent.setAction(MEASUREMENTS_COLLECTED_ACTION);
            intent.putExtra(MEASUREMENTS_EXTRA_KEY, extra);
            MyApplication.getApplication().sendBroadcast(intent);
            Timber.d("sendMeasurementsCollectedBroadcast(): Broadcast %s", extra);
        } catch (JSONException ex) {
            Timber.e(ex, "sendMeasurementsCollectedBroadcast(): Failed to serialize list of measurements to JSON");
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(MeasurementsCollectedEvent event) {
        sendMeasurementsCollectedBroadcast(event.getMeasurement());
    }

    @Override
    public void run() {
        start();
    }

    public void start() {
        EventBus.getDefault().register(this);
    }

    public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
