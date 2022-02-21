package com.example.feelthenote.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Network.UploadImageRequest;
import com.example.feelthenote.Network.UploadImageResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity {

    Button btnUploadImage;

    private static final int CAMERA_INTENT_CODE = 111;
    private String mCurrentPhotoPath = "";
    private byte[] vehicleImage=null;
//    private String imagepath;
    private boolean isConnected;
    private ProgressDialog pg;

    private ConstraintLayout clRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        btnUploadImage = findViewById(R.id.btnImageUpload);
        pg = Common.showProgressDialog(this);
        clRootLayout = findViewById(R.id.clRootLayout);

        btnUploadImage.setOnClickListener(view -> {
            imageUpload();
        });
    }

    private void imageUpload() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
             Log.d("IOEXCEPTION :: ", ex.getMessage());
        }
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Vehicle Photo");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
        startActivityForResult(chooserIntent, CAMERA_INTENT_CODE);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        String currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_INTENT_CODE) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                mCurrentPhotoPath = getRealPathFromURI(uri);
                //Logger.LogDebug("Hello gallery", mCurrentPhotoPath);
//                Log.i("PATH :::::: ", mCurrentPhotoPath);
                if (!mCurrentPhotoPath.isEmpty()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                    vehicleImage = convertImageToByteArray(bitmap);
//                    imagepath = getRealPathFromURI(uri);
                    //ivVehicleImage.setImageURI(uri);
                    changeVehicleImage(vehicleImage);
                    //if (alert.isShowing())
                    //    alert.dismiss();
                    //ivVehicleImage.setBorderColor(getResources().getColor(R.color.colorAccent));
                }
                //Toast.makeText(getApplicationContext(), "URI :::: " + getRealPathFromURI(uri), Toast.LENGTH_SHORT).show();
            } else {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Uri uri = getImageUri(this, bitmap);
                File finalFile = new File(getRealPathFromURI(uri));
                mCurrentPhotoPath = finalFile.getPath();
//                Log.i("PATH :::::: ", mCurrentPhotoPath);
                //Logger.LogDebug("Hello Camera", mCurrentPhotoPath);
                if (!mCurrentPhotoPath.isEmpty()) {
                    Bitmap bitmap2 = BitmapFactory.decodeFile(mCurrentPhotoPath);
//                    imagepath = getRealPathFromURI(uri);
                    vehicleImage = convertImageToByteArray(bitmap2);
                    changeVehicleImage(vehicleImage);
//                    if (alert.isShowing())
//                        alert.dismiss();
//                    ivVehicleImage.(getResources().getColor(R.color.colorAccent));
                }
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void changeVehicleImage(byte[] image) {
        isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(UploadImageActivity.this, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    changeVehicleImage(image);
                }
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            pg.setMessage("Please wait...");
            pg.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Log.i("II", "changeVehicleImage: ");
            Call<UploadImageResponse> call = apiInterface.uploadImage(new UploadImageRequest(image));

            call.enqueue(new Callback<UploadImageResponse>() {
                @Override
                public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                    try{
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == 1) {
                                pg.dismiss();
                                Common.showSnack_Dark(clRootLayout, response.body().getMessage());
                            } else {
                                pg.dismiss();
                                Common.showSnack_Dark(clRootLayout, response.body().getMessage());
                            }
                        } else  {
                            pg.dismiss();
                            String message = response.errorBody().string();
                            Common.showSnack_Dark(clRootLayout, message);
                        }
                    } catch (Exception e) {
                        pg.dismiss();
                        Common.showSnack_Dark(clRootLayout, e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                    pg.dismiss();
                    t.printStackTrace();
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(UploadImageActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            changeVehicleImage(image);
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }

    private byte[] convertImageToByteArray(Bitmap b) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();
    }

}