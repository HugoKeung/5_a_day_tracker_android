package com.example.hugo.myapplication;

import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.hugo.myapplication.data.RecordDbHelper;

import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ManualActivityFragment extends Fragment {
    AutoCompleteTextView autotv;
    NumberPicker np;
    RecordDbHelper dbHelper;


    public ManualActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getExtras();

        View view =  inflater.inflate(R.layout.fragment_manual, container, false);
        autotv = (AutoCompleteTextView) view.findViewById(R.id.user_input);
        np = (NumberPicker) view.findViewById(R.id.numberPicker);
        dbHelper = new RecordDbHelper(getActivity());
        setNumberPicker();
        setAutoComplete();
        Button button = (Button) view.findViewById(R.id.confirm_input);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                newManualEntry(v);
            }
        });

        if (bundle != null){
            autotv.setText(bundle.getString("result_set"));
            np.setValue(1);
        }

        return view;
    }

    private void setNumberPicker(){

        np.setMinValue(0);
        np.setMaxValue(3);
        np.setWrapSelectorWheel(false);

    }

    private void setAutoComplete(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dbHelper.allFood());

        autotv.setAdapter(adapter);
    }

    public void newManualEntry(View view){

        dbHelper.newEntry(autotv.getText().toString(), np.getValue());

        Intent myIntent = new Intent(getActivity().getApplicationContext(), MainActivity.class);

        startActivity(myIntent);
    }
}
