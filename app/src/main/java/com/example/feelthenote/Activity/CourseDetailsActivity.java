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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.feelthenote.Adapter.FacultyCarouselAdapter;
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

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context = null;

    private SharedPreferences sp;

    private String BASE_URL = "http://ftn.locuslogs.com/images/cover/";

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private LinearLayout llCourseContainer;
    private CoordinatorLayout llRootLayout;
    private TextView tvShowMoreAbout, tvShowMoreDetails, rvLatestPackageTitle, tvAboutDescription, tvDetailDescription;

    private RelativeLayout rlAboutCourse, rlCourseDetail;
    private boolean aboutCourseExpanded = false, courseDetailExpanded = false;

    private ImageView ivCourseCoverImage;
    private RecyclerView rvLatestPackage;

    private Button btnSubscribe;

    private ProgressDialog pg;

    private  String courseName="", course_ID;

    private List<CourseOtherPackages> diaglogList;

    Integer Student_ID = null;
    private String Course_ID;
    private BigInteger Student_Mapping_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent recievedIntent = getIntent();
         this.course_ID = recievedIntent.getStringExtra("Course_ID");

        initializeControls();
        initializeAppBarAndToolBarConfigs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sp = getSharedPreferences(getResources().getString(R.string.LoginSharedPreference), MODE_PRIVATE);
        Student_ID = sp.getInt("UserId",0);
        getCourseDetails(this.course_ID);
    }

    private void initializeControls(){
        context = getApplicationContext();
        llRootLayout = findViewById(R.id.llRootLayout);
        appBarLayout = findViewById(R.id.appbarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        llCourseContainer = findViewById(R.id.llCourseContainer);
        pg = Common.showProgressDialog(CourseDetailsActivity.this);

        tvShowMoreAbout = findViewById(R.id.tvShowMoreAbout);
        tvAboutDescription = findViewById(R.id.tvAboutDescription);
        tvShowMoreDetails = findViewById(R.id.tvShowMoreDetails);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);

        rlAboutCourse = findViewById(R.id.rlAboutCourse);
        rlCourseDetail = findViewById(R.id.rlCourseDetail);

        ivCourseCoverImage = findViewById(R.id.ivCourseCoverImage);

        rvLatestPackageTitle = findViewById(R.id.rvLatestPackageTitle);

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

        collapsingToolbarLayout.setTitle(courseName);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(courseName);
                    isShow = true;
                } else if (isShow) {
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
                PackageSubscriptionDialog packageSubscriptionDialog = new PackageSubscriptionDialog(Student_ID,Course_ID, Student_Mapping_ID, diaglogList);
                packageSubscriptionDialog.show(getSupportFragmentManager(), "Subscription Dialog Poped");
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

            Call<CourseDetailsResponse> courseDetailsResponse = apiInterface.getCourseDetail(new CourseDetailsRequest(Student_ID,course_ID));

            courseDetailsResponse.enqueue(new Callback<CourseDetailsResponse>() {
                @Override
                public void onResponse(Call<CourseDetailsResponse> call, Response<CourseDetailsResponse> response) {
                    try {
                        if(response.isSuccessful()){
                            pg.dismiss();
                            // Course Date
                            CourseData courseData = response.body().getCourseData();

                            // Course Details
                            CourseDetails courseDetails = courseData.getCourseDetails().get(0);
                            tvAboutDescription.setText(courseDetails.getAbout());
                            tvDetailDescription.setText(courseDetails.getDetail());

                            // Course Faculty Details
                            List<CourseFacultyDetails> courseFacultyDetails = courseData.getCourseFacultyDetails();

                            //Faculty Carousel
                            CardSliderViewPager cardSliderViewPager = findViewById(R.id.vpStudentCourseCarousel);
                            cardSliderViewPager.setAdapter(new FacultyCarouselAdapter(courseFacultyDetails));

                            // Course Latest Packages
                            List<CourseLatestPackages> courseLatestPackages = courseData.getCourseLatestPackages();
                            Log.e("CourseLatestPackages", "CourseLatestPackages size: "+courseLatestPackages+" Student_Id: "+Student_ID);
                            CourseLatestPackages courseLatestPackage = (courseLatestPackages.size()==0) ? null : courseLatestPackages.get(courseLatestPackages.size()-1);

                            // Latest Package Configurations
                            btnSubscribe.setClickable(true);
                            if(courseLatestPackage==null){
                                    btnSubscribe.setText("Subscribe");
                            }else if(courseLatestPackage.getTransactionNo()==0){
                                btnSubscribe.setText("Under Approval");
                                btnSubscribe.setClickable(false);
                            } else {
                                String expiryDate = courseLatestPackage.getExpiryDate();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date courseExpiryDate = sdf.parse(expiryDate);
                                if(new Date().after(courseExpiryDate)){
                                    btnSubscribe.setText("Renew");
                                }else{
                                    btnSubscribe.setText("Early Renew");
                                }
                            }

                            // Latest Package Recycler
                                LatestPackageAdapter latestPackageAdapter = new LatestPackageAdapter(CourseDetailsActivity.this,courseLatestPackages);
                                latestPackageAdapter.SetOnItemClickListener(new ExploreCoursesAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        // View Details Dialog
                                    }
                                });
                                rvLatestPackage.setAdapter(latestPackageAdapter);

                                if(courseLatestPackages!=null && courseLatestPackages.size()>0)
                                    courseLatestPackage = courseLatestPackages.get(0);
                                else
                                    rvLatestPackageTitle.setVisibility(View.GONE);

                            // Course Other Packages
                            List<CourseOtherPackages> courseOtherPackages = courseData.getCourseOtherPackages();

                            // Setting Course Cover Image
                            String CourseID = courseDetails.getCourseID();
                            String courseCoverImageDateTime = courseDetails.getCoverImageDateTime();
                            Log.i("CourseCoverImage", "Signature: "+CourseID+courseCoverImageDateTime);
                            ObjectKey coverImageObjectKey = new ObjectKey(CourseID+courseCoverImageDateTime);

                            String imageURL = BASE_URL + CourseID + courseCoverImageDateTime.replace(':','_')+ ".jpg";

                            Log.e("imageurl", "url: "+imageURL);
                            Glide.with(context)
                                    .load(imageURL)
                                    .signature(coverImageObjectKey)
                                    .transition(DrawableTransitionOptions.withCrossFade(700))
                                    .into(ivCourseCoverImage);

                            Log.e("CourseCoverImage", "onResponse: "+coverImageObjectKey.toString());

                            // Setting Global Variables
                            Course_ID = courseDetails.getCourseID();
                            Student_Mapping_ID = new BigInteger(courseDetails.getStudentMappingID().toString());

                            String CourseName = courseDetails.getCourseName();
                            courseName = CourseName;
                            collapsingToolbarLayout.setTitle(CourseName);

                            diaglogList = courseOtherPackages;

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

}