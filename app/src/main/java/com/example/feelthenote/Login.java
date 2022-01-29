package com.example.feelthenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    TextView registrationLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registrationLink = findViewById(R.id.registrationLink);

        registrationLink.setOnClickListener(v -> {
            Intent redirectToRegistration = new Intent(Login.this, Registration.class);
            startActivity(redirectToRegistration);
        });
    }
}