package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.MyApplication;
import com.example.feelthenote.Network.AddStudentRequest;
import com.example.feelthenote.Network.AddStudentResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, View.OnClickListener {

    private ImageView ivBack;
    private LinearLayout llRootLayout;
    private EditText etFullName, etAddress, etProf, etDOB, etContact1, etContact2, etEmail, etReference, etPassword;
    private RadioGroup rgGender;
    private CheckBox cbRememberMe;
    private Button btnSignUp;

    private ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeControls();

    }

    private void initializeControls() {
        llRootLayout = findViewById(R.id.llRootLayout);

        pg = Common.showProgressDialog(RegistrationActivity.this);

        ivBack = findViewById(R.id.ivBack);

        etFullName = findViewById(R.id.etFullName);
        etAddress = findViewById(R.id.etAddress);
        etDOB = findViewById(R.id.etDOB);
        etProf = findViewById(R.id.etProf);
        etContact1 = findViewById(R.id.etContact1);
        etContact2 = findViewById(R.id.etContact2);
        etEmail = findViewById(R.id.etEmail);
        etReference = findViewById(R.id.etReference);
        etPassword = findViewById(R.id.etPassword);

        rgGender = findViewById(R.id.rgGender);

        cbRememberMe = findViewById(R.id.cbRememberMe);

        btnSignUp = findViewById(R.id.btnSignUp);

        ivBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showView(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    private void showView(boolean isConnected) {
        if (!isConnected) {
            Common.showSnack_Dark(llRootLayout, getResources().getString(R.string.NetworkErrorMsg));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack :
                onBackPressed();
                break;
            case R.id.btnSignUp :
                registration();
                pg.show();
                break;
            default:
                break;
        }
    }

    private void registration() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    registration();
                }
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            String FullName = etFullName.getText().toString();
            String Address = etAddress.getText().toString();
            String DOB = etDOB.getText().toString();
            String Prof = etProf.getText().toString();
            String Contact1 = etContact1.getText().toString();
            String Contact2 = etContact2.getText().toString();
            String Email = etEmail.getText().toString();
            String Reference = etReference.getText().toString();
            String Password = etPassword.getText().toString();

            int genderId = rgGender.getCheckedRadioButtonId();
            String Gender = null;
            switch (genderId) {
                case R.id.rbMale:
                    Gender = "M";
                    break;
                case R.id.rbFemale:
                    Gender = "F";
                    break;
                case R.id.rbOther:
                    Gender = "O";
                    break;
            }

            boolean rememberMe = cbRememberMe.isChecked();

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<AddStudentResponse> call = apiInterface.addStudent(new AddStudentRequest(FullName, Address, DOB, Gender, Prof, Contact1, Contact2, Email, Reference, Password));

            call.enqueue(new Callback<AddStudentResponse>() {
                @Override
                public void onResponse(Call<AddStudentResponse> call, Response<AddStudentResponse> response) {
                    try{
                        if (response.isSuccessful()) {
                            if(response.body().getStatusCode() == 1) {

                                pg.dismiss();

                                Common.showSnack_Dark(llRootLayout,"Success..!!");
                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                pg.dismiss();
                                switch (response.body().getMessage()) {
                                    case "1":
                                        Common.showSnack_Dark(llRootLayout, "Contact No. Already Exist..!!");
                                        break;
                                    case "Z":
                                        Common.showSnack_Dark(llRootLayout, "Something went wrong..!!");
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else {
                            pg.dismiss();
                            Common.showSnack_Dark(llRootLayout, response.errorBody().string());
                        }
                    } catch (Exception ex) {
                        pg.dismiss();
                        Common.showSnack_Dark(llRootLayout, ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AddStudentResponse> call, Throwable t) {
                    pg.dismiss();
                    t.printStackTrace();
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            registration();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }


    }
}