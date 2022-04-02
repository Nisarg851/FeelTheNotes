package com.example.feelthenote.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.example.feelthenote.R;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileImageChangeDialog extends AppCompatDialogFragment implements View.OnClickListener{

    CircleImageView ivProfileImage;
    MaterialButton ivGallary, ivCamera;
    ActivityResultLauncher<Intent> gallaryIntentResultLauncher, cameraIntentResultLauncher;
    ProfileImageChangeDialog profileImageChangeDialog;

    public ProfileImageChangeDialog(CircleImageView ivProfileImage){
        this.ivProfileImage = ivProfileImage;
        profileImageChangeDialog = this;
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

    private void setControls(View view) {
        ivGallary = view.findViewById(R.id.ivGallary);
        ivCamera = view.findViewById(R.id.ivCamera);

        ivGallary.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
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
                            ivProfileImage.setImageURI(imageUri);
                            profileImageChangeDialog.dismiss();
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
                            Bitmap image = (Bitmap) dataBundle.get("data");
                            Toast.makeText(getContext(),"Intent success "+result.getData().getType(),Toast.LENGTH_LONG).show();
                            ivProfileImage.setImageBitmap(image);
                            profileImageChangeDialog.dismiss();
                        }else{
                            Toast.makeText(getContext(),"An Error Occured",Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
}
