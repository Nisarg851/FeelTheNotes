package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.feelthenote.R;
import com.example.feelthenote.fragment.CalendarFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Date;

public class CalenderActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialButton btnCalanderPrevWeek, btnCalanderNextWeek;
    TextView tvCalanderMonth;
    FragmentManager fragmentManager;
    Date weekStartDate;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        btnCalanderNextWeek = findViewById(R.id.btnCalanderNextWeek);
        btnCalanderPrevWeek = findViewById(R.id.btnCalanderPrevWeek);
        tvCalanderMonth = findViewById(R.id.tvCalanderMonth);

        // start with current week's start date i.e Date on Monday
        calendar = Calendar.getInstance();
        weekStartDate = new Date();
        calendar.setTime(weekStartDate);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE,(calendar.get(Calendar.DAY_OF_WEEK)-2)*-1);
            weekStartDate = calendar.getTime();
            tvCalanderMonth.setText(getMonth(calendar.get(Calendar.MONTH)));
        }

        fragmentManager = getSupportFragmentManager();
        setCalanderFragment(weekStartDate);

        btnCalanderNextWeek.setOnClickListener(this);
        btnCalanderPrevWeek.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCalanderPrevWeek:
                calendar.setTime(weekStartDate);
                calendar.add(Calendar.DATE, -7);
                weekStartDate = calendar.getTime();
                tvCalanderMonth.setText(getMonth(calendar.get(Calendar.MONTH)));
                setCalanderFragment(weekStartDate);
                break;

            case R.id.btnCalanderNextWeek:
                calendar.setTime(weekStartDate);
                calendar.add(Calendar.DATE, 7);
                weekStartDate = calendar.getTime();
                tvCalanderMonth.setText(getMonth(calendar.get(Calendar.MONTH)));
                setCalanderFragment(weekStartDate);
                break;
        }
    }

    private void setCalanderFragment(Date startDateOfWeek) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CalendarFragment fragment = new CalendarFragment(startDateOfWeek);
        fragmentTransaction.add(R.id.fcFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private String getMonth(int monthValue){
        switch (monthValue){
            case Calendar.JANUARY: return "January";
            case Calendar.FEBRUARY: return "February";
            case Calendar.MARCH: return "March";
            case Calendar.APRIL: return "April";
            case Calendar.MAY: return "May";
            case Calendar.JUNE: return "June";
            case Calendar.JULY: return "July";
            case Calendar.AUGUST: return "August";
            case Calendar.SEPTEMBER: return "September";
            case Calendar.OCTOBER: return "October";
            case Calendar.NOVEMBER: return "November";
            case Calendar.DECEMBER: return "December";
        }
        return "";
    }
}