package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.feelthenote.Adapter.CourseCarousel;
import com.example.feelthenote.Model.CourseCarouselItem;
import com.example.feelthenote.R;
import com.github.islamkhsh.CardSliderViewPager;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    CardSliderViewPager cardSliderViewPager;

    ImageView notificationMenuToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayList<CourseCarouselItem> course = new ArrayList<CourseCarouselItem>();
        // add items to arraylist
        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", "http://ftn.locuslogs.com/images/card/agtr.jpg"));
        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", "http://ftn.locuslogs.com/images/card/agtr.jpg"));
        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", "http://ftn.locuslogs.com/images/card/agtr.jpg"));

        cardSliderViewPager = findViewById(R.id.viewPager);
        cardSliderViewPager.setAdapter(new CourseCarousel(course));

        // Temp Image new activity redirect
        notificationMenuToggleButton = findViewById(R.id.notificationMenuToggleButton);
        notificationMenuToggleButton.setOnClickListener(view -> {
            Intent readirectToMyCourses = new Intent(this, MyCoursesActivity.class);
            startActivity(readirectToMyCourses);
        });
    }
}