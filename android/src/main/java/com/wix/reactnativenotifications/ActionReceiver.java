package com.wix.reactnativenotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wix.reactnativenotifications.core.AppLifecycleFacadeHolder;
import com.wix.reactnativenotifications.core.JsIOHelper;

/**
 * Created by dmytrobazunov on 2/1/18.
 */

public class ActionReceiver extends BroadcastReceiver {

    public ActionReceiver() {

        Log.d("NOTIFICATIONS", "Created");

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NOTIFICATIONS", intent.toString());
        JsIOHelper mJsIOHelper = new JsIOHelper();
        intent.putExtra("action", intent.getAction());
        mJsIOHelper.sendEventToJS(intent.getAction(), intent.getExtras(), AppLifecycleFacadeHolder.get().getRunningReactContext());

    }
}
