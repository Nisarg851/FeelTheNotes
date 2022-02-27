package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.feelthenote.Adapter.FacultyCarousel;
import com.example.feelthenote.Dialogs.PackageSubscriptionDialog;
import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Model.CourseData;
import com.example.feelthenote.Model.CourseDetails;
import com.example.feelthenote.Model.CourseFacultyDetails;
import com.example.feelthenote.Model.CourseLatestPackages;
import com.example.feelthenote.Model.CourseOtherPackages;
import com.example.feelthenote.Network.CourseDetailsResponse;
import com.example.feelthenote.Network.CourseDetailsRequest;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.RecyclerViewAdapter.ExploreCoursesAdapter;
import com.example.feelthenote.RecyclerViewAdapter.LatestPackageAdapter;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;
import com.github.islamkhsh.CardSliderViewPager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private LinearLayout llCourseContainer;
    private CoordinatorLayout llRootLayout;
    private TextView tvShowMoreAbout, tvShowMoreDetails;

    private RelativeLayout rlAboutCourse, rlCourseDetail;
    private boolean aboutCourseExpanded = false, courseDetailExpanded = false;

    private ImageView ivCourseCoverImage;
    private RecyclerView rvLatestPackage;

    private Button btnSubscribe;

    private ProgressDialog pg;

    private  String courseName="Course Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent recievedIntent = getIntent();
        String course_ID = recievedIntent.getStringExtra("Course_ID");

        initializeControls();
        initializeAppBarAndToolBarConfigs();
        getCourseDetails(course_ID);
    }

    private void initializeControls(){
        llRootLayout = findViewById(R.id.llRootLayout);
        appBarLayout = findViewById(R.id.appbarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        llCourseContainer = findViewById(R.id.llCourseContainer);
        pg = Common.showProgressDialog(CourseDetailsActivity.this);

        tvShowMoreAbout = findViewById(R.id.tvShowMoreAbout);
        tvShowMoreDetails = findViewById(R.id.tvShowMoreDetails);

        rlAboutCourse = findViewById(R.id.rlAboutCourse);
        rlCourseDetail = findViewById(R.id.rlCourseDetail);

        ivCourseCoverImage = findViewById(R.id.ivCourseCoverImage);

        rvLatestPackage = findViewById(R.id.rvLatestPackage);

        btnSubscribe = findViewById(R.id.btnSubscribe);

        tvShowMoreAbout.setOnClickListener(this);
        tvShowMoreDetails.setOnClickListener(this);
        btnSubscribe.setOnClickListener(this);
    }

    private void initializeAppBarAndToolBarConfigs(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //userDetailsSummaryContainer.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle(courseName);
                    //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.MyCollapsingTitleTextAppearance);
                    isShow = true;
                } else if (isShow) {
                    //userDetailsSummaryContainer.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        Log.i("Expand", "onClick: "+aboutCourseExpanded+" "+courseDetailExpanded);
        switch (view.getId()){
            case R.id.tvShowMoreAbout:
                aboutCourseExpanded = !aboutCourseExpanded;
                if(aboutCourseExpanded)
                    rlAboutCourse.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                else
                    rlAboutCourse.getLayoutParams().height = (int) convertPixelsToDp(100, this.getApplicationContext());
                rlAboutCourse.requestLayout();
                break;
            case R.id.tvShowMoreDetails:
                courseDetailExpanded = !courseDetailExpanded;
                if(courseDetailExpanded)
                    rlCourseDetail.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                else
                    rlCourseDetail.getLayoutParams().height = (int) convertPixelsToDp(100, this.getApplicationContext());
                rlCourseDetail.requestLayout();
                break;
            case R.id.btnSubscribe:
//                AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this, R.layout.package_subscription_dialog_layout);
//                builder.setTitle("Subscribe");
//                builder.setPositiveButton(R.id.tvSubscriptionButton, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) { }});
//                builder.setNegativeButton(R.id.tvCancleButton, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) { }});
                PackageSubscriptionDialog packageSubscriptionDialog = new PackageSubscriptionDialog();

                packageSubscriptionDialog.show(getSupportFragmentManager(), "Subscription Dialog Poped");
//                builder.show();
                break;
        }
    }

    public float convertPixelsToDp(float px, Context context){
        return px * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
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
                            Common.showSnack_Dark(llRootLayout, "Success..!!");
                            CourseData courseData = response.body().getCourseData();
                            CourseDetails courseDetails = courseData.getCourseDetails().get(0);
                            List<CourseFacultyDetails> courseFacultyDetails = courseData.getCourseFacultyDetails();
                            List<CourseLatestPackages> courseLatestPackages = courseData.getCourseLatestPackages();
                            List<CourseOtherPackages> courseOtherPackages = courseData.getCourseOtherPackages();

                            String CourseID = courseDetails.getCourseID();
                            String CourseName = courseDetails.getCourseName();
                            courseName = CourseName;
                            ivCourseCoverImage.setImageDrawable(bitmap2Drawable(StringToBitMap(courseDetails.getCoverImage())));

                            //Faculty Carousel
                            CardSliderViewPager cardSliderViewPager = findViewById(R.id.viewPager);
                            cardSliderViewPager.setAdapter(new FacultyCarousel(courseFacultyDetails));

                            // Latest Package Recycler
                            LatestPackageAdapter latestPackageAdapter = new LatestPackageAdapter(CourseDetailsActivity.this,courseLatestPackages);
                            latestPackageAdapter.SetOnItemClickListener(new ExploreCoursesAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    // View Details Dialog
                                }
                            });
                            rvLatestPackage.setAdapter(latestPackageAdapter);
                        } else {
                            pg.dismiss();
                            Common.showSnack_Dark(llRootLayout, response.errorBody().string());
                        }
                    }catch (Exception ex) {
                        pg.dismiss();
                        Common.showSnack_Dark(llRootLayout, ex.getMessage());
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

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        @SuppressWarnings("deprecation")
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }

}