package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.feelthenote.R;
import com.example.feelthenote.fragment.CalendarFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Date;

public class CalenderActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialButton btnCalanderPrevWeek, btnCalanderNextWeek;
    FragmentManager fragmentManager;
    Date weekStartDate;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        btnCalanderNextWeek = findViewById(R.id.btnCalanderNextWeek);
        btnCalanderPrevWeek = findViewById(R.id.btnCalanderPrevWeek);

        // start with current week's start date i.e Date on Monday
        weekStartDate = new Date();
        calendar = Calendar.getInstance();

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
                setCalanderFragment(weekStartDate);
                break;

            case R.id.btnCalanderNextWeek:
                calendar.setTime(weekStartDate);
                calendar.add(Calendar.DATE, 7);
                weekStartDate = calendar.getTime();
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
}