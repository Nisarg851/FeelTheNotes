package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.example.feelthenote.Adapter.CourseCarousel;
import com.example.feelthenote.Model.CourseCarouselItem;
import com.example.feelthenote.R;
import com.github.islamkhsh.CardSliderViewPager;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    CardSliderViewPager cardSliderViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayList<CourseCarouselItem> course = new ArrayList<CourseCarouselItem>();
        // add items to arraylist
        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", new BitmapDrawable(getResources(),new BitmapFactory().decodeResource(getResources(), R.drawable.agtr))));
        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", new BitmapDrawable(getResources(),new BitmapFactory().decodeResource(getResources(), R.drawable.agtr))));
        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", new BitmapDrawable(getResources(),new BitmapFactory().decodeResource(getResources(), R.drawable.agtr))));

        cardSliderViewPager = findViewById(R.id.viewPager);
        cardSliderViewPager.setAdapter(new CourseCarousel(course));
    }
}