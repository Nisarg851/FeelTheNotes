package com.example.feelthenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    ImageView splashScreen;

    //Dashboard Funtionalities


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

    }
}