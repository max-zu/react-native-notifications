package com.wix.reactnativenotifications.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.wix.reactnativenotifications.ActionReceiver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmytrobazunov on 2/2/18.
 */

public class NotificationCategory {

    private String identifier;

    public NotificationCategory(Bundle category) {
        identifier = category.getString("identifier");
        actions = new ArrayList<>();
        for (Parcelable action : category.getParcelableArrayList("actions")) {
            actions.add(new Action((Bundle) action,identifier));

        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    private List<Action> actions;


    @Override
    public String toString() {
        return "NotificationCategory{" +
                "identifier='" + identifier + '\'' +
                ", actions=" + actions +
                '}';
    }
}
