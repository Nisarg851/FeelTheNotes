package com.example.feelthenote.RecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.R;

import java.util.ArrayList;

public class WeeklySlotsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<String> weeklySlotsData;

    public WeeklySlotsAdapter(ArrayList<String> weeklySlotsData) {
        this.weeklySlotsData = weeklySlotsData;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%8==0)
            return 1;
        return 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_day_time_slot_layout, parent, false);
            return new ViewHolderForDayTimeSlot(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_week_day_slot_layout, parent, false);
        return new ViewHolderForWeekDaySlot(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { }

    @Override
    public int getItemCount() {
        return weeklySlotsData.size();
    }

    class ViewHolderForWeekDaySlot extends RecyclerView.ViewHolder{
        public ViewHolderForWeekDaySlot(View itemView) {
            super(itemView);
        }
    }

    class ViewHolderForDayTimeSlot extends RecyclerView.ViewHolder{
        public ViewHolderForDayTimeSlot(View itemView) { super(itemView); }
    }
}
