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

import com.example.feelthenote.R;
import com.example.feelthenote.RecyclerViewAdapter.WeekDayAndDateListAdapter;
import com.example.feelthenote.RecyclerViewAdapter.WeeklySlotsAdapter;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    RecyclerView rvWeekDaysContainer, rvWeeklySlots;

    int weekNumber, daysInMonth;

    public CalendarFragment(int weekNumber, int daysInMonth){
        this.weekNumber = weekNumber;
        this.daysInMonth = daysInMonth;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initilizeControls(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initilizeDayRecyclerView(container);
        }

        ArrayList<String> weeklySlotData = getWeekSlotsData();
        initilizeWeeklySlotRecyclerView(weeklySlotData);

        return view;
    }


    private void initilizeWeeklySlotRecyclerView(ArrayList<String> weeklySlotData) {
        WeeklySlotsAdapter weeklySlotsAdapter = new WeeklySlotsAdapter(weeklySlotData);
        rvWeeklySlots.setAdapter(weeklySlotsAdapter);
    }

    private void initilizeControls(View view) {
        rvWeekDaysContainer = view.findViewById(R.id.rvWeekDaysContainer);
        rvWeeklySlots = view.findViewById(R.id.rvWeeklySlots);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initilizeDayRecyclerView(ViewGroup container) {
        // Set Horizontal LinearLayout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvWeekDaysContainer.setLayoutManager(layoutManager);

        // Set Divider in Recycler View
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(container.getContext(), DividerItemDecoration.HORIZONTAL);
//        rvWeekDaysContainer.addItemDecoration(dividerItemDecoration);

        // Set Adapter
        WeekDayAndDateListAdapter weekDayAndDateListAdapter = new WeekDayAndDateListAdapter(this.weekNumber, this.daysInMonth);
        rvWeekDaysContainer.setAdapter(weekDayAndDateListAdapter);
    }

    private ArrayList<String> getWeekSlotsData(){
        ArrayList<String> data = new ArrayList<String>();

        int daysInWeek = 7;
        int timeSlotCol = 1;
        int slotsInADay = 24;

        for(int slot=1; slot<=((daysInWeek+timeSlotCol)*slotsInADay); slot++){
            data.add("");
        }

        return data;
    }

}