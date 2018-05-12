package com.example.hugo.myapplication;

import android.icu.text.AlphabeticIndex;
import android.icu.text.SimpleDateFormat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hugo.myapplication.data.RecordDbHelper;

import java.util.Date;



/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecordDbHelper dbHelper = new RecordDbHelper(getActivity());
        TextView tv = (TextView) view.findViewById(R.id.portion_fraction);
        ProgressBar pb = (ProgressBar) view.findViewById(R.id.today_progress);
        double todayPortion = dbHelper.getDayRecord(new Date());

        String result = String.format("%.1f", todayPortion);

        ProgressBarUtil.setProgressBar(pb, todayPortion);
        tv.setText(result + " / 5 portions consumed today");

        return view;
    }

}
