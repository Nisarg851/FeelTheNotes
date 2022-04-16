package com.example.feelthenote.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feelthenote.Dialogs.ProfileImageChangeDialog;
import com.example.feelthenote.Network.ProfileImageChangeRequest;
import com.example.feelthenote.Network.ProfileImageChangeResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    CircleImageView ivProfileImage;
    TextView tvUserName, tvUserEmail, tvUserNumber, tvUserDOB, tvUserJoiningDate, tvUserAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeControls();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivProfileImage:
                ProfileImageChangeDialog profileImageChangeDialog = new ProfileImageChangeDialog(ivProfileImage, ProfileActivity.this);
                profileImageChangeDialog.show(getSupportFragmentManager(), "Profile Image Change Dialog");
                break;
        }
    }

    private void initializeControls(){
        ivProfileImage = findViewById(R.id.ivProfileImage);

        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserNumber = findViewById(R.id.tvUserNumber);
        tvUserDOB = findViewById(R.id.tvUserDOB);
        tvUserJoiningDate = findViewById(R.id.tvUserJoiningDate);
        tvUserAddress = findViewById(R.id.tvUserAddress);

        ivProfileImage.setOnClickListener(this);
    }

    private void saveChanges(){
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDate();
    }

    private void loadDate(){}

}