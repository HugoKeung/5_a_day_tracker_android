package com.example.hugo.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hugo.myapplication.data.RecordDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hugo on 04/06/17.
 */

public class ProgressBarUtil {



    public static void setProgressBar (ProgressBar pb, double value){
        int show = (int)(value*10);
        pb.setProgress(show);
    }

    public static void setRV(TextView tv, ProgressBar pb, ImageView iv, Date date, Context ctx){
        RecordDbHelper dbHelper = new RecordDbHelper(ctx);


        double portion = dbHelper.getDayRecord(date);
        if (portion >=5){

            iv.setVisibility(View.VISIBLE);
        }
        else iv.setVisibility(View.INVISIBLE);

        tv.setText(DateUtil.dateTo_ddMM(date));
        setProgressBar(pb, portion);

    }

}
