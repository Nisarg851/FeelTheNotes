package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Model.LoginUserDatum;
import com.example.feelthenote.MyApplication;
import com.example.feelthenote.Network.LoginRequest;
import com.example.feelthenote.Network.LoginResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, View.OnClickListener{

    LinearLayout llRootLayout;
    EditText etMobileNumber, etPassword;
    CheckBox cbRememberMe;
    Button btnSignIn;
    TextView registrationLink;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeControls();
    }

    void initializeControls(){
        llRootLayout = findViewById(R.id.llRootLayout);

        pg = Common.showProgressDialog(LoginActivity.this);

        etMobileNumber = findViewById(R.id.etMobileNumber);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);

        btnSignIn = findViewById(R.id.btnSignIn);

        registrationLink = findViewById(R.id.registrationLink);

        btnSignIn.setOnClickListener(this);
        registrationLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignIn:
                loginUser();
                pg.show();
                break;
            case R.id.registrationLink:
                Intent redirectToRegistration = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(redirectToRegistration);
                finish();
            default:
                break;
        }
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

    void loginUser(){
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    loginUser();

                }
            });
            builder.setCancelable(false);
            builder.show();
        }else {
            String mobileNumber = etMobileNumber.getText().toString();
            String password = etPassword.getText().toString();
            boolean rememberMe = cbRememberMe.isChecked();

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<LoginResponse> loginResponseCall = apiInterface.loginUser(new LoginRequest(mobileNumber, password));

            loginResponseCall.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    try{
                        if (response.isSuccessful()) {
                            if(response.body().getStatusCode() == 1) {
                                pg.dismiss();
                                Common.showSnack_Dark(llRootLayout,"Success..!!");

                                LoginUserDatum loginUserDatum = response.body().getData().get(0);

                                int userID = loginUserDatum.getUserID();
                                String password = loginUserDatum.getPassword();
//                                String userType = loginUserDatum.getUserType();

                                SharedPreferences loginSharedPreference = getSharedPreferences(getResources().getString(R.string.LoginSharedPreference), MODE_PRIVATE);
                                SharedPreferences.Editor sharedPreferenceEditor = loginSharedPreference.edit();
                                sharedPreferenceEditor.putInt("UserId", userID);
                                sharedPreferenceEditor.putString("Password", password);
                                sharedPreferenceEditor.commit();

                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                pg.dismiss();
                                switch (response.body().getMessage()) {
                                    case "1":
                                        Common.showSnack_Dark(llRootLayout, "Invalid Credentials");
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
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    pg.dismiss();
                    t.printStackTrace();
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loginUser();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }
}