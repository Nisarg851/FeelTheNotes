package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Model.CourseData;
import com.example.feelthenote.Model.CourseDetails;
import com.example.feelthenote.Model.CourseFacultyDetails;
import com.example.feelthenote.Model.CourseLatestPackages;
import com.example.feelthenote.Model.CourseOtherPackages;
import com.example.feelthenote.Model.GetMyCoursesDatum;
import com.example.feelthenote.Network.CourseDetailsResponse;
import com.example.feelthenote.Network.CourseDetailsRequest;
import com.example.feelthenote.Network.GetCoursesResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity {

    ProgressDialog pg;

    ConstraintLayout clRootLayout;
    TextView tempView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent recievedIntent = getIntent();
        String course_ID = recievedIntent.getStringExtra("Course_ID");
        clRootLayout = findViewById(R.id.clRootLayout);
        tempView = findViewById(R.id.textView5);
        tempView.setText(course_ID);
        pg = Common.showProgressDialog(CourseDetailsActivity.this);
        getCourseDetails(course_ID);
    }

    private void getCourseDetails(String course_ID){
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(CourseDetailsActivity.this, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getCourseDetails(course_ID);
                }
            });
            builder.setCancelable(false);
            builder.show();
        }else {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<CourseDetailsResponse> courseDetailsResponse = apiInterface.getCourseDetail(new CourseDetailsRequest(23,course_ID));

            courseDetailsResponse.enqueue(new Callback<CourseDetailsResponse>() {
                @Override
                public void onResponse(Call<CourseDetailsResponse> call, Response<CourseDetailsResponse> response) {
                    try {
                        if(response.isSuccessful()){
                            pg.dismiss();
                            Common.showSnack_Dark(clRootLayout, "Success..!!");
                            CourseData courseData = response.body().getCourseData();
                            List<CourseDetails> courseDetails = courseData.getCourseDetails();
                            List<CourseFacultyDetails> courseFacultyDetails = courseData.getCourseFacultyDetails();
                            List<CourseLatestPackages> courseLatestPackages = courseData.getCourseLatestPackages();
                            List<CourseOtherPackages> courseOtherPackages = courseData.getCourseOtherPackages();
                        } else {
                            pg.dismiss();
                            Common.showSnack_Dark(clRootLayout, response.errorBody().string());
                        }
                    }catch (Exception ex) {
                        pg.dismiss();
                        Common.showSnack_Dark(clRootLayout, ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<CourseDetailsResponse> call, Throwable t) {
                    pg.dismiss();
                    t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getCourseDetails(course_ID);
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }
}