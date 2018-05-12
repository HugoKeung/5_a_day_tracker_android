package com.example.hugo.myapplication;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hugo.myapplication.data.DayRecord;
import com.example.hugo.myapplication.data.RecordDbHelper;

import java.util.ArrayList;

/**
 * Created by hugo on 16/06/17.
 */

public class AboutFragment extends Fragment {
    AllFoodAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_all_food_list);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecordDbHelper dbHelper = new RecordDbHelper(getActivity());
        Cursor cursor = dbHelper.foodPortionCursor();

        mAdapter = new AllFoodAdapter(getActivity(), cursor);

        //make cursor then put cursor into adapter
        rv.setAdapter(mAdapter);

        return view;
    }
}
