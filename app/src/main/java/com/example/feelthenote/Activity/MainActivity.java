package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.feelthenote.Model.Course;
import com.example.feelthenote.Adapter.CourseCarousel;
import com.example.feelthenote.R;
import com.github.islamkhsh.CardSliderViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView splashScreen;

    //Dashboard Funtionalities
    CardSliderViewPager cardSliderViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        splashScreen = findViewById(R.id.splashScreen);
//
//        splashScreen.setOnClickListener(v->{
//            Intent lauchActivity = new Intent(MainActivity.this, Login.class);
//            startActivity(lauchActivity);
//        });

//        ArrayList<Course> courses = new ArrayList<Course>();
//        // add items to arraylist
//        courses.add(new Course(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", new BitmapDrawable(getResources(),new BitmapFactory().decodeResource(getResources(), R.drawable.agtr))));
//        courses.add(new Course(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", new BitmapDrawable(getResources(),new BitmapFactory().decodeResource(getResources(), R.drawable.agtr))));
//        courses.add(new Course(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", new BitmapDrawable(getResources(),new BitmapFactory().decodeResource(getResources(), R.drawable.agtr))));
//
//        cardSliderViewPager = findViewById(R.id.viewPager);
//        cardSliderViewPager.setAdapter(new CourseCarousel(courses));
    }

    void carouselAnimationTimeChange(){
        Log.i("carousel", "tapped");
        cardSliderViewPager.setAutoSlideTime(30);
    }
}