package com.example.hugo.myapplication;

import android.database.Cursor;
import android.icu.text.AlphabeticIndex;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hugo.myapplication.data.DayRecord;
import com.example.hugo.myapplication.data.RecordDbHelper;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeekActivityFragment extends Fragment {

    private DayViewAdapter mAdapter;

    public WeekActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_week, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_week_list);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecordDbHelper dbHelper = new RecordDbHelper(getActivity());
        ArrayList<DayRecord> list = dbHelper.getWeekRecord();

        mAdapter = new DayViewAdapter(getActivity(), list);

        //make cursor then put cursor into adapter
        rv.setAdapter(mAdapter);

        return view;
    }


}
