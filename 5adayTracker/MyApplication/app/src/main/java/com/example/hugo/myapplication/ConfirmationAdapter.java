package com.example.hugo.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;

import com.example.hugo.myapplication.data.RecordDbHelper;

import java.util.ArrayList;

/**
 * Created by hugo on 04/08/17.
 */

public class ConfirmationAdapter extends RecyclerView.Adapter<ConfirmationAdapter.ConfirmationViewHolder>{

    Context ctx;
    ArrayList<String> list;
    ArrayList<Integer> numList;


    public ConfirmationAdapter(Context ctx, ArrayList<String> list){
        this.list = list;
        this.ctx = ctx;
        numList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            numList.add(1);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ConfirmationViewHolder holder, int position) {

        RecordDbHelper dbHelper = new RecordDbHelper(ctx);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, dbHelper.allFood());

        final int current = position;
        String nameOfFood = list.get(position);
        holder.autotv.setText(nameOfFood);
        holder.autotv.setAdapter(adapter);
        holder.autotv.setTag(position);
        holder.autotv.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.set(current, s.toString());


            }
        });

        holder.np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numList.set(current, newVal);

            }
        });
        holder.np.setValue(1);

    }

    @Override
    public ConfirmationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.rv_confirmation, parent, false);
        return new ConfirmationViewHolder(view);
    }

    class ConfirmationViewHolder extends RecyclerView.ViewHolder{
        AutoCompleteTextView autotv;
        NumberPicker np;
        public ConfirmationViewHolder(View itemView){
            super(itemView);
            autotv = (AutoCompleteTextView) itemView.findViewById(R.id.confirm_text);
            np = (NumberPicker) itemView.findViewById(R.id.confirm_number);

            np.setMinValue(0);
            np.setMaxValue(3);
            np.setWrapSelectorWheel(false);

        }

    }

    public ArrayList<String> getList(){
        return list;
    }
    public ArrayList<Integer> getNumList(){
        return numList;
    }


}
