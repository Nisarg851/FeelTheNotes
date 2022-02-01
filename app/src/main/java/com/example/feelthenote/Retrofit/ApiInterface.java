package com.example.feelthenote.Retrofit;

import com.example.feelthenote.Network.AddStudentRequest;
import com.example.feelthenote.Network.AddStudentResponse;
import com.example.feelthenote.Network.LoginRequest;
import com.example.feelthenote.Network.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("api/FeelTheNote/AddStudent")
    Call<AddStudentResponse> addStudent(@Body AddStudentRequest addStudentRequest);

    @POST("api/FeelTheNote/UserLogin")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
