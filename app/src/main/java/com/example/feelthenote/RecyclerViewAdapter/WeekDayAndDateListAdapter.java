package com.example.feelthenote.RecyclerViewAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class WeekDayAndDateListAdapter extends RecyclerView.Adapter<WeekDayAndDateListAdapter.ViewHolder> {

    ViewGroup parent = null;
    String[] Days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

    Date weekStartDate;
    String dateToday;
    SimpleDateFormat sdf;
    Calendar calendar;

    public WeekDayAndDateListAdapter(Date weekStartDate) {
        this.weekStartDate = weekStartDate;
        sdf = new SimpleDateFormat("dd/MM");
        calendar = Calendar.getInstance();
        dateToday = sdf.format(calendar.getTime());
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
            holder.itemView.findViewById(R.id.vBottomLine).setVisibility(View.GONE);
        }else{
            ((TextView)holder.itemView.findViewById(R.id.tvDay)).setText(Days[position-1]);
            calendar.setTime(weekStartDate);
            calendar.add(Calendar.DATE, position-1);
            String date = sdf.format(calendar.getTime());
            holder.tvDate.setText(date.substring(0,2));
            if(date.equals(dateToday))
                holder.bottomLine.setBackgroundResource(R.color.colorAccent);
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        View bottomLine;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            bottomLine = itemView.findViewById(R.id.vBottomLine);
        }
    }
}
