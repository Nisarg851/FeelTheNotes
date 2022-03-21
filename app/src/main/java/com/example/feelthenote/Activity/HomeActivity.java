package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.feelthenote.Adapter.CourseCarouselAdapter;
import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Model.StudentDashboardCourseCarousel;
import com.example.feelthenote.Model.StudentDashboardData;
import com.example.feelthenote.Model.StudentDashboardInfo;
import com.example.feelthenote.Model.StudentDashboardUpcomingSession;
import com.example.feelthenote.Network.GetCoursesRequest;
import com.example.feelthenote.Network.GetStudentDashboardResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;
import com.github.islamkhsh.CardSliderViewPager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout llRootLayout;

    ProgressDialog pg;

    Context context = this;

    // Student Info Components
    CircleImageView ivStudentProfileImage;
    TextView tvStudentName, tvStudentEmailID;

    // Student Upcoming Session Components
    TextView tvUpcomingSessionTitle, tvCourseName, tvTutorName, tvUpcomingSessionDateAndTime;

    // Student's Course Carousel
    CardSliderViewPager vpStudentCourseCarousel;

    ImageView notificationMenuToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        ArrayList<CourseCarouselItem> course = new ArrayList<CourseCarouselItem>();
//        // add items to arraylist
//        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", "http://ftn.locuslogs.com/images/card/agtr.jpg"));
//        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", "http://ftn.locuslogs.com/images/card/agtr.jpg"));
//        course.add(new CourseCarouselItem(10,10,10,10,10,10,10,"Acoustic Guitar","AGTR", "Shubham Acharya", "http://ftn.locuslogs.com/images/card/agtr.jpg"));
//        vpStudentCourseCarousel.setAdapter(new CourseCarousel(course));

        // Temp Image new activity redirect
        notificationMenuToggleButton = findViewById(R.id.notificationMenuToggleButton);
        notificationMenuToggleButton.setOnClickListener(view -> {
            Intent readirectToMyCourses = new Intent(this, MyCoursesActivity.class);
            startActivity(readirectToMyCourses);
        });

        initializeControls();

        setDashboardData();
    }

    private void initializeControls(){
        llRootLayout = findViewById(R.id.llRootLayout);
        pg = Common.showProgressDialog(HomeActivity.this);

        // Student Info Controls
        ivStudentProfileImage = findViewById(R.id.ivStudentProfileImage);
        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentEmailID = findViewById(R.id.tvStudentEmailID);

        // Student's Upcoming Session
        tvUpcomingSessionTitle = findViewById(R.id.tvUpcomingSessionTitle);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvTutorName = findViewById(R.id.tvTutorName);
        tvUpcomingSessionDateAndTime = findViewById(R.id.tvUpcomingSessionDateAndTime);

        // Student's Course Carousel
        vpStudentCourseCarousel = findViewById(R.id.vpStudentCourseCarousel);
    }

    private void setDashboardData(){
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setDashboardData();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }else {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<GetStudentDashboardResponse> getStudentDashboardResponseCall = apiInterface.getStudentDashboard(new GetCoursesRequest(23));

            getStudentDashboardResponseCall.enqueue(new Callback<GetStudentDashboardResponse>() {
                @Override
                public void onResponse(Call<GetStudentDashboardResponse> call, Response<GetStudentDashboardResponse> response) {
                    try{
                        if (response.isSuccessful()) {
                            if(response.body().getStatusCode() == 1) {
                                pg.dismiss();
                                Common.showSnack_Light(llRootLayout,"Success..!!");
                                StudentDashboardData studentDashboardData = response.body().getStudentDashboardData();

                                // Student Info
                                StudentDashboardInfo studentDashboardInfo = studentDashboardData.getStudentDashboardInfo().get(0);
                                String studentName = studentDashboardInfo.getName(),
                                        studentEmailID = studentDashboardInfo.getEmail(),
                                        studentProfileImage = studentDashboardInfo.getProfileImageDate();

//                                Glide.with(context)
//                                        .load("Set Base URL HERE"+studentProfileImage)
//                                        .into(ivStudentProfileImage);

                                tvStudentName.setText(studentName);
                                tvStudentEmailID.setText(studentEmailID);

                                // Student Upcoming Session
                                StudentDashboardUpcomingSession studentDashboardUpcomingSession = studentDashboardData.getStudentDashboardUpcomingSession().get(0);
                                String upcomingSessionTitle = "Upcoming Session - "+studentDashboardUpcomingSession.getCourseID(),
                                        courseName = studentDashboardUpcomingSession.getCourseName(),
                                        tutorName = "Tutor Name",
                                        upcomingSessionDateAndTime = studentDashboardUpcomingSession.getDate();

                                tvUpcomingSessionTitle.setText(upcomingSessionTitle);
                                tvCourseName.setText(courseName);
                                tvTutorName.setText(tutorName);
                                tvUpcomingSessionDateAndTime.setText(upcomingSessionDateAndTime);

                                // Student Course Carousel
                                List<StudentDashboardCourseCarousel> studentDashboardCourseCarouselList = studentDashboardData.getStudentDashboardCourseCarousel();
                                CourseCarouselAdapter courseCarouselAdapter = new CourseCarouselAdapter(studentDashboardCourseCarouselList);
                                vpStudentCourseCarousel.setAdapter(courseCarouselAdapter);
                            } else {
                                pg.dismiss();
                                Common.showSnack_Light(llRootLayout,"Something went wrong!");
                            }
                        } else {
                            pg.dismiss();
                            Common.showSnack_Light(llRootLayout, response.errorBody().string());
                            Log.e("error", "1 "+response.code());
                        }
                    } catch (Exception ex) {
                        pg.dismiss();
                        Common.showSnack_Light(llRootLayout, ex.getMessage());
                        Log.e("error", "2");
                    }
                }

                @Override
                public void onFailure(Call<GetStudentDashboardResponse> call, Throwable t) {
                    pg.dismiss();
                    t.printStackTrace();
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setDashboardData();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }
}