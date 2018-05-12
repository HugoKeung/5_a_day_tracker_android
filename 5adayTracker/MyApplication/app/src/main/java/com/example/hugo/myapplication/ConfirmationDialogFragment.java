package com.example.hugo.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hugo.myapplication.data.RecordDbHelper;

import java.util.ArrayList;

/**
 * Created by hugo on 04/08/17.
 */

public class ConfirmationDialogFragment extends DialogFragment {

    RecyclerView rv;
    ConfirmationAdapter mAdapter;

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Confirm the input");
//        builder.setMessage("Set ythe quantity of food");
//        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//              dialog.dismiss();
//            }
//        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        return builder.show();
//    }
//

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ArrayList<String> list = getArguments().getStringArrayList("result_list");


        View view =  inflater.inflate(R.layout.dialog_confirmation, container);

        //set confirm and cancel button

        Button confirm = (Button) view.findViewById(R.id.confirm);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArrayList<String> foodList = mAdapter.getList();
                ArrayList<Integer> numList = mAdapter.getNumList();
                RecordDbHelper dbHelper = new RecordDbHelper(getContext());
                for (int i = 0; i < foodList.size(); i++){
                    dbHelper.newEntry(foodList.get(i), numList.get(i));

                }

                getActivity().finish();

                startActivity(new Intent(getActivity(), MainActivity.class));


                getDialog().dismiss();
           //     getActivity().recreate();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        rv = (RecyclerView) view.findViewById(R.id.rv_confirmation_list);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new ConfirmationAdapter(getContext(), list);
        rv.setAdapter(mAdapter);
        return view;

    }
}
