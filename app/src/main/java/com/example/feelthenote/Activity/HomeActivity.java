package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.feelthenote.Adapter.CourseCarouselAdapter;
import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Model.Advertise;
import com.example.feelthenote.Model.StudentDashboardCourseCarousel;
import com.example.feelthenote.Model.StudentDashboardData;
import com.example.feelthenote.Model.StudentDashboardInfo;
import com.example.feelthenote.Model.StudentDashboardUpcomingSession;
import com.example.feelthenote.Network.GetCoursesRequest;
import com.example.feelthenote.Network.GetStudentDashboardResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.RecyclerViewAdapter.AdvertisementAdapter;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;
import com.github.islamkhsh.CardSliderViewPager;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout llRootLayout;

    ProgressDialog pg;

    Context context = this;

    // Student Info Components
    CircleImageView ivStudentProfileImage;
    TextView tvStudentName, tvStudentEmailID, tvUpcomingSessionDuration;

    // Student Upcoming Session Components
    TextView tvUpcomingSessionTitle, tvCourseName, tvTutorName, tvUpcomingSessionDateAndTime;

    // Student's Course Carousel
    CardSliderViewPager vpStudentCourseCarousel;

    // Advertisement RecyclerView
    RecyclerView rvAdvertisement;

    CardView cvNoSubscribedCourseContainer;

    ImageView notificationMenuToggleButton;
    private SharedPreferences sp;
    private int userID;

    MaterialButton navBtnHome, navBtnExplore, navBtnSessions , navBtnSettings, navBtnCalander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDashboardData();
    }

    private void initializeControls(){
        llRootLayout = findViewById(R.id.llRootLayout);
        pg = Common.showProgressDialog(HomeActivity.this);
        sp = getSharedPreferences(getResources().getString(R.string.LoginSharedPreference), MODE_PRIVATE);

        notificationMenuToggleButton = findViewById(R.id.notificationMenuToggleButton);

        // Student Info Controls
        ivStudentProfileImage = findViewById(R.id.ivStudentProfileImage);
        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentEmailID = findViewById(R.id.tvStudentEmailID);

        // Student's Upcoming Session
        tvUpcomingSessionTitle = findViewById(R.id.tvUpcomingSessionTitle);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvTutorName = findViewById(R.id.tvTutorName);
        tvUpcomingSessionDateAndTime = findViewById(R.id.tvUpcomingSessionDateAndTime);
        tvUpcomingSessionDuration = findViewById(R.id.tvUpcomingSessionDuration);

        cvNoSubscribedCourseContainer = findViewById(R.id.cvNoSubscribedCourseContainer);

        rvAdvertisement = findViewById(R.id.rvAdvertisement);

        navBtnHome = findViewById(R.id.navBtnHome);
        navBtnCalander = findViewById(R.id.navBtnCalander);
        navBtnSessions = findViewById(R.id.navBtnSessions);
        navBtnExplore = findViewById(R.id.navBtnExplore);
        navBtnSettings = findViewById(R.id.navBtnSettings);

        userID = sp.getInt("UserId",0);
        // Student's Course Carousel
        vpStudentCourseCarousel = findViewById(R.id.vpStudentCourseCarousel);

        cvNoSubscribedCourseContainer.setOnClickListener(this);
        navBtnHome.setOnClickListener(this);
        navBtnCalander.setOnClickListener(this);
        navBtnSessions.setOnClickListener(this);
        navBtnExplore.setOnClickListener(this);
        navBtnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent redirectTo = null;
        switch (view.getId()){
            case R.id.navBtnCalander:
                redirectTo = new Intent(HomeActivity.this, CalenderActivity.class);
                break;
            case R.id.navBtnSessions:
                redirectTo = new Intent(HomeActivity.this, SessionDataActivity.class);
                break;
            case R.id.navBtnExplore:
            case R.id.cvNoSubscribedCourseContainer:
                redirectTo = new Intent(HomeActivity.this, MyCoursesActivity.class);
                break;
            case R.id.navBtnSettings:
                redirectTo = new Intent(HomeActivity.this, SettingsActivity.class);
                break;
        }
        if(redirectTo!=null) {
            startActivity(redirectTo);
        }
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

            Call<GetStudentDashboardResponse> getStudentDashboardResponseCall = apiInterface.getStudentDashboard(new GetCoursesRequest(userID));

            getStudentDashboardResponseCall.enqueue(new Callback<GetStudentDashboardResponse>() {
                @Override
                public void onResponse(Call<GetStudentDashboardResponse> call, Response<GetStudentDashboardResponse> response) {
                    try{
                        if (response.isSuccessful()) {
                            if(response.body().getStatusCode() == 1) {
                                pg.dismiss();
//                                Common.showSnack_Light(llRootLayout,"Success..!!");
                                StudentDashboardData studentDashboardData = response.body().getStudentDashboardData();

                                // Student Info
                                List<StudentDashboardInfo> studentDashboardInfoList = studentDashboardData.getStudentDashboardInfo();
                                if(studentDashboardInfoList!=null && studentDashboardInfoList.size()>0) {
                                    StudentDashboardInfo studentDashboardInfo = studentDashboardInfoList.get(0);
                                    String studentName = studentDashboardInfo.getName(),
                                            studentEmailID = studentDashboardInfo.getEmail(),
                                            studentProfileImage = studentDashboardInfo.getProfileImageDate();

                                    String imageURL = "http://ftn.locuslogs.com/images/student_profile/" + userID + studentProfileImage.replace(':', '_') + ".jpg";

                                    Log.e("url", "onResponse: " + imageURL);

                                    Glide.with(context)
                                            .load(imageURL)
                                            .placeholder(R.drawable.default_user_image)
                                            .into(ivStudentProfileImage);

                                    tvStudentName.setText(studentName);
                                    tvStudentEmailID.setText(studentEmailID);
                                }

                                // Student Upcoming Session
                                List<StudentDashboardUpcomingSession> studentDashboardUpcomingSessionList = studentDashboardData.getStudentDashboardUpcomingSession();
                                if(studentDashboardUpcomingSessionList!=null && studentDashboardUpcomingSessionList.size()>0) {
                                    StudentDashboardUpcomingSession studentDashboardUpcomingSession = studentDashboardUpcomingSessionList.get(0);
                                    String upcomingSessionTitle = "Upcoming Session - " + studentDashboardUpcomingSession.getCourseID(),
                                            courseName = studentDashboardUpcomingSession.getCourseName(),
                                            tutorName = "Instructor: " + studentDashboardUpcomingSession.getInstructorName(),
                                            date = studentDashboardUpcomingSession.getDate().split("T")[0],
                                            time = studentDashboardUpcomingSession.getTime();

                                    int duration = studentDashboardUpcomingSession.getDuration();

                                    String newDateTimeString = setProperDateTimeFormat(date, time);

                                    tvUpcomingSessionTitle.setText(upcomingSessionTitle);
                                    tvCourseName.setText(courseName);
                                    tvTutorName.setText(tutorName);
                                    tvUpcomingSessionDateAndTime.setText(newDateTimeString);
                                    tvUpcomingSessionDuration.setText(duration + " min");
                                }

                                // Student Course Carousel
                                List<StudentDashboardCourseCarousel> studentDashboardCourseCarouselList = studentDashboardData.getStudentDashboardCourseCarousel();
                                Log.e("ccsize", "onResponse: Carousel datesize: "+studentDashboardCourseCarouselList.size());
                                if(studentDashboardCourseCarouselList.size()==0)
                                    cvNoSubscribedCourseContainer.setVisibility(View.VISIBLE);
                                else {
                                    cvNoSubscribedCourseContainer.setVisibility(View.GONE);
                                    CourseCarouselAdapter courseCarouselAdapter = new CourseCarouselAdapter(studentDashboardCourseCarouselList);
                                    vpStudentCourseCarousel.setAdapter(courseCarouselAdapter);
                                }

                                // Advertisement Recycler
                                List<Advertise> advertiseList = studentDashboardData.getAdvertise();
                                Log.e("adlist", "onResponse: "+advertiseList.size()+" "+advertiseList.get(0).getAdvertiseImage());
                                AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(advertiseList);
                                rvAdvertisement.setAdapter(advertisementAdapter);

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

    private String setProperDateTimeFormat(String date, String time) {
        Log.e("DT", "setProperDateTimeFormat: date: "+date+" time: "+time);
        String inputString = date + " " + time;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, dd MMMM, HH:mm aa");
        String fullDateTime = inputString;
        try {
            fullDateTime = outputFormat.format(inputFormat.parse(inputString));
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            return fullDateTime;
        }
    }
}