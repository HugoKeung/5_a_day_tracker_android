package com.example.hugo.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hugo.myapplication.data.DayRecord;
import com.example.hugo.myapplication.data.RecordContract;

import java.util.ArrayList;


/**
 * Created by hugo on 03/06/17.
 */

public class DayViewAdapter extends RecyclerView.Adapter<DayViewAdapter.DayViewHolder> {



    private Context mContext;
    private ArrayList<DayRecord> list;

    public DayViewAdapter(Context context, ArrayList<DayRecord> list){
        this.list = list;
        mContext = context;
    }
    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.rv_each_day, parent, false);
        return new DayViewHolder(view);

    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {

        DayRecord day = list.get(position);
        String simpleDate = day.getDate().substring(day.getDate().indexOf('-')+1);

        holder.dateTextView.setText(simpleDate);
        ProgressBarUtil.setProgressBar(holder.progressBar, day.getPortion());
        if (day.getPortion() >= 5) {
            holder.completeImageView.setVisibility(View.VISIBLE);
        }
        else holder.completeImageView.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        //return mCursor.getCount();
        return 7;
    }

    class DayViewHolder extends RecyclerView.ViewHolder{
        TextView dateTextView;
        ProgressBar progressBar;
        ImageView completeImageView;

        public DayViewHolder(View itemView){
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.rv_date);
            progressBar = (ProgressBar) itemView.findViewById(R.id.rv_progress);
            completeImageView = (ImageView) itemView.findViewById(R.id.rv_check);
        }
    }
}
