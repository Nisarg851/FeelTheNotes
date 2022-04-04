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
import android.view.View;
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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivProfileImage.setOnClickListener(this);
        context = getApplicationContext();
    }

    @Override
    public void onClick(View view) {
        ProfileImageChangeDialog profileImageChangeDialog = new ProfileImageChangeDialog(ivProfileImage, ProfileActivity.this);


//        profileImageChangeDialog.onDismiss(new DialogInterface() {
//            @Override
//            public void cancel() {
//
//            }
//
//            @Override
//            public void dismiss() {
//                boolean isConnected = ConnectivityReceiver.isConnected();
//                if (!isConnected) {
//                    Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
//                }else {
//                    Bitmap image = ((BitmapDrawable)ivProfileImage.getDrawable()).getBitmap();
//                    byte[] encodedImage = getByteArrayImage(image);
//                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//                    Call<ProfileImageChangeResponse> profileImageChangeResponseCall = apiInterface.changeProfileImage(new ProfileImageChangeRequest("student_profile", encodedImage));
//                    Toast.makeText(context, "Request send", Toast.LENGTH_SHORT).show();
//                    profileImageChangeResponseCall.enqueue(new Callback<ProfileImageChangeResponse>() {
//                        @Override
//                        public void onResponse(Call<ProfileImageChangeResponse> call, Response<ProfileImageChangeResponse> response) {
//                            try{
//                                if (response.isSuccessful()) {
//                                    if(response.body().getStatusCode() == 1) {
////                                pg.dismiss();
//                                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                                        profileImageChangeDialog.dismiss();
//                                    } else {
////                                pg.dismiss();
//                                        Toast.makeText(context, "Something Went Wrong!1", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
////                            pg.dismiss();
//                                    Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (Exception ex) {
////                        pg.dismiss();
//                                Toast.makeText(context, "An Unexpected Error Occured", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ProfileImageChangeResponse> call, Throwable t) {
////                    pg.dismiss();
//                            Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
        profileImageChangeDialog.show(getSupportFragmentManager(), "Profile Image Change Dialog");
    }

    private byte[] getByteArrayImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

}