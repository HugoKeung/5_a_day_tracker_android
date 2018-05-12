package com.example.hugo.myapplication.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.app.NotificationManagerCompat;
import android.text.format.DateUtils;

import com.example.hugo.myapplication.DateUtil;
import com.example.hugo.myapplication.MainActivity;
import com.example.hugo.myapplication.MonthActivity;
import com.example.hugo.myapplication.R;
import com.example.hugo.myapplication.data.RecordDbHelper;

import java.util.Date;

/**
 * Created by hugo on 09/06/17.
 */

public class NotificationsUtil {

    private static final int END_OF_DAY_PENDING_INTENT_ID = 1234;
    private static final int END_OF_DAY_NOTIFICATION_ID = 2345;

    private static PendingIntent contentIntent(Context context){
        Intent myIntent = new Intent(context, MonthActivity.class);
        myIntent.putExtra("from_notification", true);
        return PendingIntent.getActivity(context, END_OF_DAY_PENDING_INTENT_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void makeNotification(Context context){

        RecordDbHelper dbHelper = new RecordDbHelper(context);

        Date yesterday = DateUtil.yesterday();
        double portion = dbHelper.getDayRecord(yesterday);
//TODO new notification image
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(makeBitMap(context))
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)
                .setContentTitle("Daily notification from 5 a day tracker")
                .setContentText("You have consumed " +  portion +" of fruits and vegetables yesterday");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(END_OF_DAY_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static Bitmap makeBitMap(Context context){
        Resources res = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, android.R.drawable.checkbox_off_background);
        return bitmap;
    }


}
