package com.example.hugo.myapplication;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hugo.myapplication.data.RecordDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class MonthActivityFragment extends Fragment {

    TextView tv;
    ProgressBar pb;
    ImageView iv;
    private PortionDetailAdapter mAdapter;

    public MonthActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();



        final CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendar_month);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_month);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        final RecordDbHelper dbHelper = new RecordDbHelper(getActivity());
        pb = (ProgressBar) view.findViewById(R.id.rv_progress);
        tv = (TextView) view.findViewById(R.id.rv_date);
        iv = (ImageView) view.findViewById(R.id.rv_check);
        //update adapeter when change date
        mAdapter = new PortionDetailAdapter(getActivity(), dbHelper.detailedDayRecord(new Date()));


        if (bundle == null) {
            ProgressBarUtil.setRV(tv, pb, iv, new Date(), getActivity());
            mAdapter = new PortionDetailAdapter(getActivity(), dbHelper.detailedDayRecord(new Date()));
        }
        else{
            Date yesterday = DateUtil.yesterday();

            ProgressBarUtil.setRV(tv, pb, iv, yesterday, getActivity());
            mAdapter = new PortionDetailAdapter(getActivity(), dbHelper.detailedDayRecord(yesterday));
            Calendar cal = Calendar.getInstance();
            cal.setTime(yesterday);

            long milliTime = cal.getTimeInMillis();
            calendarView.setDate (milliTime, true, true);

        }
        rv.setAdapter(mAdapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Date selectedDate = DateUtil.intToDate(year, month+1, dayOfMonth);

                ProgressBarUtil.setRV(tv,pb, iv, selectedDate, getActivity());
                mAdapter.swapCursor(dbHelper.detailedDayRecord(selectedDate));


            }
        });

        return view;
    }



}
