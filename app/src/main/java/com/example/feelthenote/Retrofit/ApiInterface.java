package com.example.feelthenote.Retrofit;

import com.example.feelthenote.Network.AddStudentRequest;
import com.example.feelthenote.Network.AddStudentResponse;
import com.example.feelthenote.Network.CourseDetailsResponse;
import com.example.feelthenote.Network.CourseDetailsRequest;
import com.example.feelthenote.Network.GetCoursesRequest;
import com.example.feelthenote.Network.GetCoursesResponse;
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

    @POST("api/FeelTheNote/GetCourses")
    Call<GetCoursesResponse> getCourses(@Body GetCoursesRequest getCoursesRequest);

    @POST("api/FeelTheNote/GetCourseDetails")
    Call<CourseDetailsResponse> getCourseDetail(@Body CourseDetailsRequest courseDetailsRequest);
}
