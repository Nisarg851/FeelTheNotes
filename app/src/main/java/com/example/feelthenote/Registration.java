package com.example.feelthenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Registration extends AppCompatActivity {

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent backToLogin = new Intent(Registration.this, Login.class);
            startActivity(backToLogin);
        });
    }
}