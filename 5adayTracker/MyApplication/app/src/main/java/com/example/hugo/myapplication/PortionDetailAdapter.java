package com.example.hugo.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hugo.myapplication.data.RecordContract;

import org.w3c.dom.Text;

/**
 * Created by hugo on 07/06/17.
 */

public class PortionDetailAdapter extends RecyclerView.Adapter<PortionDetailAdapter.PortionDetailViewHolder> {

    Context mContext;
    Cursor mCursor;

    public PortionDetailAdapter(Context ctx,Cursor cursor){
        mContext = ctx;
        mCursor = cursor;
    }

    @Override
    public PortionDetailAdapter.PortionDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.rv_detail_day, parent, false);
        return new PortionDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PortionDetailAdapter.PortionDetailViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) return;

        String foodName = mCursor.getString(mCursor.getColumnIndex(RecordContract.PortionToQuantity.COLUMN_FOOD_NAME));

        double portion = mCursor.getDouble(mCursor.getColumnIndex(RecordContract.PORTIONxQUANTITY));

        holder.nameOfFood.setText(foodName);
        holder.portion.setText(String.format("%.1f",portion));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor!=null) mCursor.close();
        mCursor = newCursor;

        if (newCursor != null){
            this.notifyDataSetChanged();
        }
    }


    class PortionDetailViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfFood;
        TextView portion;
        public PortionDetailViewHolder(View itemView){
            super(itemView);
            nameOfFood = (TextView) itemView.findViewById(R.id.rv_food_name);
            portion = (TextView) itemView.findViewById(R.id.rv_portion_size);
        }
    }
}
