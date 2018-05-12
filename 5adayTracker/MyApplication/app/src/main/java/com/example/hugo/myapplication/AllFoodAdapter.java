package com.example.hugo.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hugo.myapplication.data.RecordContract;

/**
 * Created by hugo on 16/06/17.
 */

public class AllFoodAdapter extends RecyclerView.Adapter<AllFoodAdapter.AllFoodViewHolder> {

    Context ctx;
    Cursor cursor;


    public AllFoodAdapter(Context context, Cursor cursor){
        this.ctx = context;
        this.cursor = cursor;

    }

    @Override
    public AllFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.rv_food_list, parent, false);
        return new AllFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllFoodViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) return;

        String foodName = cursor.getString(cursor.getColumnIndex(RecordContract.PortionToQuantity.COLUMN_FOOD_NAME));

        double portion = cursor.getDouble(cursor.getColumnIndex(RecordContract.PortionToQuantity.COLUMN_PORTION));

        holder.food_name.setText(foodName);
        holder.real_portion.setText(String.format("%.1f",portion));
    }

    @Override
    public int getItemCount() {

        return cursor.getCount();
    }

    class AllFoodViewHolder extends RecyclerView.ViewHolder{
        TextView food_name;
        TextView real_portion;

        public AllFoodViewHolder(View itemView){
            super(itemView);
            food_name = (TextView) itemView.findViewById(R.id.rv_all_food_name);
            real_portion = (TextView) itemView.findViewById(R.id.rv_portion_to_quantity);

        }

    }
}
