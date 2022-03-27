package com.example.feelthenote.RecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.R;


public class WeekDayAndDateListAdapter extends RecyclerView.Adapter<WeekDayAndDateListAdapter.ViewHolder> {

    ViewGroup parent = null;
    String[] Days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

    int weekNumber, daysInMonth;

    public WeekDayAndDateListAdapter(int weekNumber, int daysInMonth) {
        this.weekNumber = weekNumber;
        this.daysInMonth = daysInMonth;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_header_week_day_and_date, parent, false);
        this.parent = parent;
        return new WeekDayAndDateListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeekDayAndDateListAdapter.ViewHolder holder, int position) {
        holder.itemView.getLayoutParams().width = (int) ((parent.getWidth())/8);
        holder.itemView.getLayoutParams().height = parent.getHeight();
        if(position==0){
            ((TextView)holder.itemView.findViewById(R.id.tvDay)).setText("");
            ((TextView)holder.itemView.findViewById(R.id.tvDate)).setText("");
            holder.itemView.findViewById(R.id.vBottomLine).setVisibility(View.GONE);
        }else{
            ((TextView)holder.itemView.findViewById(R.id.tvDay)).setText(Days[position-1]);
            ((TextView)holder.itemView.findViewById(R.id.tvDate)).setText(String.valueOf(position+(7*(weekNumber-1))));
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
