package com.wix.reactnativenotifications.gcm;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wix.reactnativenotifications.core.notification.IPushNotification;
import com.wix.reactnativenotifications.core.notification.PushNotification;

import java.util.Map;

import static com.wix.reactnativenotifications.Defs.LOGTAG;

public class GcmMessageHandlerService extends FirebaseMessagingService {

    private final static String SILENT_PUSH_KEY = "is_silent";

    public static String getSilentPushKey() {
        return SILENT_PUSH_KEY;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        onMessageReceived("", bundle);
    }
сч
    public void onMessageReceived(String s, Bundle bundle) {

        try {
            final IPushNotification notification = PushNotification.get(getApplicationContext(), bundle);
            boolean isSilent = bundle.getString(SILENT_PUSH_KEY, "false").equals("true");
            notification.onReceived(isSilent);
        } catch (IPushNotification.InvalidNotificationException e) {
            // A GCM message, yes - but not the kind we know how to work with.
            Log.v(LOGTAG, "GCM message handling aborted", e);
        }
    }
}
