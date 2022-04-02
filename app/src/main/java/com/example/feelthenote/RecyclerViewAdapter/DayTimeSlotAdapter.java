package com.example.feelthenote.RecyclerViewAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.R;


public class DayTimeSlotAdapter extends RecyclerView.Adapter<DayTimeSlotAdapter.ViewHolder> {

    public DayTimeSlotAdapter() {
        Log.e("init", "DayTimeSlotAdapter: ");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_day_time_slot_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayTimeSlotAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 24;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
