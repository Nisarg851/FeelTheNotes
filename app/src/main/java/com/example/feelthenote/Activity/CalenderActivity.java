package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.feelthenote.R;
import com.example.feelthenote.fragment.CalendarFragment;
import com.google.android.material.button.MaterialButton;

public class CalenderActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialButton btnCalanderPrevWeek, btnCalanderNextWeek;
    FragmentManager fragmentManager;
    int WeekCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        btnCalanderNextWeek = findViewById(R.id.btnCalanderNextWeek);
        btnCalanderPrevWeek = findViewById(R.id.btnCalanderPrevWeek);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CalendarFragment fragment = new CalendarFragment(WeekCount, 31);
        fragmentTransaction.add(R.id.fcFragmentContainer, fragment);
        fragmentTransaction.commit();

        btnCalanderNextWeek.setOnClickListener(this);
        btnCalanderPrevWeek.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCalanderPrevWeek:
                WeekCount = WeekCount<=1 ? 1 : WeekCount-1;
                btnCalanderNextWeek.setVisibility(View.VISIBLE);
                if(WeekCount==1){
                    btnCalanderPrevWeek.setVisibility(View.GONE);
                    break;
                }
                btnCalanderPrevWeek.setVisibility(View.VISIBLE);
                changeWeek(WeekCount);
                break;

            case R.id.btnCalanderNextWeek:
                WeekCount = WeekCount>=4 ? 4 : WeekCount+1;
                btnCalanderPrevWeek.setVisibility(View.VISIBLE);
                if(WeekCount==4){
                    btnCalanderNextWeek.setVisibility(View.GONE);
                    break;
                }
                btnCalanderNextWeek.setVisibility(View.VISIBLE);
                changeWeek(WeekCount);
                break;
        }
    }

    private void changeWeek(int WeekCount){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CalendarFragment fragment = new CalendarFragment(WeekCount, 31);
        fragmentTransaction.replace(R.id.fcFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

}