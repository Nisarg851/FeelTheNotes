package com.example.feelthenote.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.feelthenote.Activity.MyCoursesActivity;
import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Network.AddFeeDetailRequest;
import com.example.feelthenote.Network.AddFeeDetailResponse;
import com.example.feelthenote.Network.ProfileImageChangeRequest;
import com.example.feelthenote.Network.ProfileImageChangeResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ProfileImageChangeDialog extends AppCompatDialogFragment implements View.OnClickListener{

    Context context;
//    ProgressDialog pg;
    CircleImageView ivProfileImage;
    MaterialButton ivGallary, ivCamera;
    ActivityResultLauncher<Intent> gallaryIntentResultLauncher, cameraIntentResultLauncher;
    ProfileImageChangeDialog profileImageChangeDialog;
    SharedPreferences sp;
    int studentId;
    private Bitmap image;

    public ProfileImageChangeDialog(CircleImageView ivProfileImage, Context context){
        this.ivProfileImage = ivProfileImage;
        profileImageChangeDialog = this;
        this.context = context;
//        pg = Common.showProgressDialog(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.profile_image_change_dialog_layout, null);

        setControls(view);
        registerCameraIntent();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        changeProfileImage(image);
    }

    private void setControls(View view) {
        ivGallary = view.findViewById(R.id.ivGallary);
        ivCamera = view.findViewById(R.id.ivCamera);

        ivGallary.setOnClickListener(this);
        ivCamera.setOnClickListener(this);

        sp = getActivity().getSharedPreferences(getResources().getString(R.string.LoginSharedPreference), MODE_PRIVATE);
        studentId = sp.getInt("UserId",0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivGallary:
                launchGallary();
                break;
            case R.id.ivCamera:
                launchCamera();
                break;
        }
    }

    private void registerCameraIntent(){
        gallaryIntentResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Uri imageUri = (Uri)result.getData().getData();
                            Toast.makeText(getContext(),"Profile Image Changed",Toast.LENGTH_LONG).show();
                            try {
                                 image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                                ivProfileImage.setImageURI(imageUri);
                                profileImageChangeDialog.dismiss();
                                //changeProfileImage(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getContext(),"An Error Occured",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        cameraIntentResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bundle dataBundle = result.getData().getExtras();
                            image = (Bitmap) dataBundle.get("data");
                            ivProfileImage.setImageBitmap(image);
                            profileImageChangeDialog.dismiss();
//                            changeProfileImage(image);
                        }else{
                            Toast.makeText(getContext(),"An Error Occured",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void changeProfileImage(Bitmap image){
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
        }else {
            byte[] encodedImage = getByteArrayImage(image);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            String Image_Date = studentId+"_"+(new SimpleDateFormat("yyyyMMMddHHmmss").format(new Date()));
            String image_name = "student_profile\\"+Image_Date+".jpg";
            String Image_ID = "student";
            Call<ProfileImageChangeResponse> profileImageChangeResponseCall = apiInterface.changeProfileImage(new ProfileImageChangeRequest(image_name, Image_Date, Image_ID, encodedImage, studentId));
            Toast.makeText(context, "Request send", Toast.LENGTH_SHORT).show();
            Log.e("imageDate", "ImageName: "+image_name+" Image_Date: "+Image_Date+" ObjectID: "+studentId+" ImageID: "+Image_ID);
            profileImageChangeResponseCall.enqueue(new Callback<ProfileImageChangeResponse>() {

                @Override
                public void onResponse(Call<ProfileImageChangeResponse> call, Response<ProfileImageChangeResponse> response) {
                    try{

                        if (response.isSuccessful()) {
                            if(response.body().getStatusCode() == 1) {
//                                pg.dismiss();
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                profileImageChangeDialog.dismiss();
                            } else {
//                                pg.dismiss();
                                Toast.makeText(context, "Something Went Wrong!1", Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            pg.dismiss();

                            Log.e("ERRR1", response.errorBody().string());
                            Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
//                        pg.dismiss();
                        Toast.makeText(context, "An Unexpected Error Occured", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileImageChangeResponse> call, Throwable t) {
//                    pg.dismiss();
                    Log.e("ERRR1", "ERRR1");
                    Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntentResultLauncher.launch(cameraIntent);
    }

    private void launchGallary() {
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK);
        gallaryIntent.setType("image/*");
        gallaryIntentResultLauncher.launch(gallaryIntent);
    }

    private byte[] getByteArrayImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
