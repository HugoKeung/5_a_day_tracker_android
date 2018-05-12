package com.example.hugo.myapplication;

import android.content.Intent;
import android.os.Bundle;


import com.example.hugo.myapplication.notification.DayChangeBroadcastReceiver;

public class MainActivity extends BaseActivityCamera {

    DayChangeBroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        broadcastReceiver = new DayChangeBroadcastReceiver();

    }

}
