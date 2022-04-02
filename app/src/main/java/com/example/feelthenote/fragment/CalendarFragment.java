package com.example.feelthenote.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.feelthenote.R;
import com.example.feelthenote.RecyclerViewAdapter.WeekDayAndDateListAdapter;
import com.example.feelthenote.RecyclerViewAdapter.WeeklySlotsAdapter;

import java.util.ArrayList;
import java.util.Date;

public class CalendarFragment extends Fragment {

    RecyclerView rvWeekDaysContainer, rvWeeklySlots;

    Date weekStartDate;
    private int daysInWeek = 7;
    private int slotsInADay = 48;
    int timeSlotCol = 1;
    private int totalSlots = (daysInWeek+timeSlotCol)*slotsInADay;

    public CalendarFragment(Date weekStartDate){
        this.weekStartDate = weekStartDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initilizeControls(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initilizeDayRecyclerView();
        }

        ArrayList<String> weeklySlotData = getWeekSlotsData();
        initilizeWeeklySlotRecyclerView(weeklySlotData);

        return view;
    }

    private void initilizeControls(View view) {
        rvWeekDaysContainer = view.findViewById(R.id.rvWeekDaysContainer);
        rvWeeklySlots = view.findViewById(R.id.rvWeeklySlots);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initilizeDayRecyclerView() {
        // Set Horizontal LinearLayout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvWeekDaysContainer.setLayoutManager(layoutManager);

        // Set Adapter
        WeekDayAndDateListAdapter weekDayAndDateListAdapter = new WeekDayAndDateListAdapter(weekStartDate);
        rvWeekDaysContainer.setAdapter(weekDayAndDateListAdapter);
    }

    private void initilizeWeeklySlotRecyclerView(ArrayList<String> weeklySlotData) {
        WeeklySlotsAdapter weeklySlotsAdapter = new WeeklySlotsAdapter(weeklySlotData);
        weeklySlotsAdapter.setWeekDaySlotClickListeners(new WeeklySlotsAdapter.WeekDaySlotClickListeners() {
            @Override
            public void cellClicker(WeeklySlotsAdapter.ViewHolderForWeekDaySlot viewHolder, int position, int[] daySlots) {
                if(position>=(totalSlots-daysInWeek))
                    return;
                if(daySlots[position] == 0){
                    daySlots[position] = 1;
                    daySlots[position+8] = 1;
                }else{
                    daySlots[position] = 0;
                    daySlots[position+8] = 0;
                }
                weeklySlotsAdapter.onBindViewHolder(viewHolder, position);
                WeeklySlotsAdapter.ViewHolderForWeekDaySlot viewHolderBelow = (WeeklySlotsAdapter.ViewHolderForWeekDaySlot) rvWeeklySlots.findViewHolderForAdapterPosition(position+8);
                weeklySlotsAdapter.onBindViewHolder(viewHolderBelow, position+8);
//                Toast.makeText(getContext(), "Clicked Cell no. "+position, Toast.LENGTH_SHORT).show();
            }
        });
        rvWeeklySlots.setAdapter(weeklySlotsAdapter);
    }

    private ArrayList<String> getWeekSlotsData(){
        ArrayList<String> data = new ArrayList<String>();

        for(int slot=1; slot<=totalSlots; slot++){
            data.add("");
        }

        return data;
    }
}