package com.example.feelthenote.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.feelthenote.Dialogs.ProfileImageChangeDialog;
import com.example.feelthenote.R;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    CircleImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivProfileImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ProfileImageChangeDialog profileImageChangeDialog = new ProfileImageChangeDialog(ivProfileImage);
        profileImageChangeDialog.show(getSupportFragmentManager(), "Profile Image Change Dialog");
    }
}