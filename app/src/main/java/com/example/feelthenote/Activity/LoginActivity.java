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
    boolean alreadyLoggedIn = false;
    private SharedPreferences loginSharedPreference;
    private SharedPreferences.Editor sharedPreferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeControls();
        verifyLoginStatus();
    }

    void verifyLoginStatus(){
        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.LoginSharedPreference), MODE_PRIVATE);
        String contact = sp.getString("Contact",null);
        String password = sp.getString("Password",null);

        alreadyLoggedIn = ((contact!=null) && (password!=null));

        if (alreadyLoggedIn) {
            loginUser(alreadyLoggedIn, contact, password);
        }
    }

    void initializeControls(){
        loginSharedPreference = getSharedPreferences(getResources().getString(R.string.LoginSharedPreference), MODE_PRIVATE);
        sharedPreferenceEditor = loginSharedPreference.edit();
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

                String mobileNumber = etMobileNumber.getText().toString();
                String password = etPassword.getText().toString();
                boolean rememberMe = cbRememberMe.isChecked();

                loginUser(alreadyLoggedIn, mobileNumber,password);

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

    void loginUser(boolean alreadyLoggedIn, String spUserContact, String spUserPassword){
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    loginUser(alreadyLoggedIn, spUserContact, spUserPassword);

                }
            });
            builder.setCancelable(false);
            builder.show();
        } else {

            pg.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<LoginResponse> loginResponseCall = apiInterface.loginUser(new LoginRequest(spUserContact, spUserPassword));

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
                                String contact = loginUserDatum.getContact1();
                                int userType = loginUserDatum.getUserType();


                                sharedPreferenceEditor.putInt("UserId", userID);
                                sharedPreferenceEditor.putString("Contact", contact);
                                sharedPreferenceEditor.putString("Password", password);
                                sharedPreferenceEditor.putInt("UserType", userType);
                                sharedPreferenceEditor.commit();

                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                pg.dismiss();
                                switch (response.body().getMessage()) {
                                    case "1":
                                        Common.showSnack_Dark(llRootLayout, "Invalid Credentials");
                                        if(alreadyLoggedIn) {
                                           sharedPreferenceEditor.clear().commit();
                                        }
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
                            loginUser(alreadyLoggedIn, spUserContact, spUserPassword);
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }
}