package com.example.feelthenote.RecyclerViewAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.Model.SessionData;
import com.example.feelthenote.R;
import com.example.feelthenote.fragment.CalendarFragment;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeeklySlotsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<SessionData> weeklySlotsData;
    WeeklySlotsAdapter.WeekDaySlotClickListeners weekDaySlotClickListeners;

    String startTime = "00:00 am";
//    String[] Time = new String[385];
    int[] DaySlots;

    public WeeklySlotsAdapter(List<SessionData> weeklySlotsData) {
        this.weeklySlotsData = weeklySlotsData;
//        Arrays.fill(Time, "");
        DaySlots = new int[weeklySlotsData.size()];
        Arrays.fill(DaySlots, 0);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderForDayTimeSlot){
//            if(Time[position].isEmpty())
//                Time[position] = startTime;
//            ((ViewHolderForDayTimeSlot)holder).tvTimeSlot.setText(Time[position]);
            ((ViewHolderForDayTimeSlot)holder).tvTimeSlot.setText(startTime);
            try {
                startTime = addMinutes(startTime,30);
            } catch (ParseException e) {
                Log.e("misstag", "Stack position missed "+position);
            }
            finally { }
        }else{
            if(DaySlots[position]==1){
                ((ViewHolderForWeekDaySlot)holder).itemView.setBackgroundResource(R.color.colorAccent);
            }else{
                ((ViewHolderForWeekDaySlot)holder).itemView.setBackgroundResource(R.color.white);
            }
        }
    }

    @Override
    public int getItemCount() {
        return weeklySlotsData.size();
    }

    class ViewHolderForDayTimeSlot extends RecyclerView.ViewHolder{
        TextView tvTimeSlot;
        public ViewHolderForDayTimeSlot(View itemView) {
            super(itemView);
            tvTimeSlot = itemView.findViewById(R.id.tvTimeSlot);
        }
    }

    public class ViewHolderForWeekDaySlot extends RecyclerView.ViewHolder implements View.OnClickListener{
        ViewHolderForWeekDaySlot viewHolderForWeekDaySlot;
        public ViewHolderForWeekDaySlot(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            viewHolderForWeekDaySlot = this;
        }

        @Override
        public void onClick(View view) {
            weekDaySlotClickListeners.cellClicker(viewHolderForWeekDaySlot, getAdapterPosition(), DaySlots);
        }
    }

    public interface WeekDaySlotClickListeners{
        void cellClicker(ViewHolderForWeekDaySlot viewHolder, int position, int[] daySlots);
    }

    public void setWeekDaySlotClickListeners(final WeeklySlotsAdapter.WeekDaySlotClickListeners weekDaySlotClickListeners){
        this.weekDaySlotClickListeners = weekDaySlotClickListeners;
    }

    private String addMinutes(String orgTime, int minute) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm aa");
        Date d = df.parse(orgTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, minute);
        return df.format(cal.getTime());
    }
}
