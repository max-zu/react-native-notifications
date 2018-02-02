package com.wix.reactnativenotifications.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wix.reactnativenotifications.ActionReceiver;

/**
 * Created by dmytrobazunov on 2/2/18.
 */

public class Action {
    private String title;
    private String identifier;
    private String categoryIdentifier;

    public Action(Bundle action, String categoryIdentifier) {
        title = action.getString("title");
        identifier = action.getString("identifier");
        this.categoryIdentifier = categoryIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public PendingIntent getPendingIntent(Context context, Bundle data) {
        Intent receive = new Intent(context, ActionReceiver.class);
        receive.setAction(categoryIdentifier + "-" + identifier);
        receive.putExtra("data", data);
        return PendingIntent.getBroadcast(context, 12345, receive, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public String toString() {
        return "Action{" +
                "title='" + title + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
