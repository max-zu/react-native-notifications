package com.wix.reactnativenotifications.gcm;

import android.content.Intent;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.wix.reactnativenotifications.Defs.TOKEN_RECEIVED_EVENT_NAME;

/**
 * Instance-ID + token refreshing handling service. Contacts the GCM to fetch the updated token.
 *
 * @author amitd
 */
public class GcmInstanceIdListenerService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendTokenToJS(refreshedToken);
    }

    protected void sendTokenToJS(String refreshedToken) {
        final ReactInstanceManager instanceManager = ((ReactApplication) getApplicationContext()).getReactNativeHost().getReactInstanceManager();
        final ReactContext reactContext = instanceManager.getCurrentReactContext();

        // Note: Cannot assume react-context exists cause this is an async dispatched service.
        if (reactContext != null && reactContext.hasActiveCatalystInstance()) {
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(TOKEN_RECEIVED_EVENT_NAME, refreshedToken);
        }
    }
}
