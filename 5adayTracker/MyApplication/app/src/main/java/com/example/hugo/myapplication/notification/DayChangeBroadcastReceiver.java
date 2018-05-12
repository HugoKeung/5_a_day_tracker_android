package com.example.hugo.myapplication.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hugo on 09/06/17.
 */

public class DayChangeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        boolean dateChange = action.equals(Intent.ACTION_DATE_CHANGED);
        if (dateChange){NotificationsUtil.makeNotification(context);}
    }
}
