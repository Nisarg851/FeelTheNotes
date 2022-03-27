package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Model.GetExploreCoursesDatum;
import com.example.feelthenote.Model.GetMyCoursesDatum;
import com.example.feelthenote.Network.GetCoursesRequest;
import com.example.feelthenote.Network.GetCoursesResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.RecyclerViewAdapter.ExploreCoursesAdapter;
import com.example.feelthenote.RecyclerViewAdapter.MyCoursesAdapter;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCoursesActivity extends AppCompatActivity {

    LinearLayout llRootLayout;
    RecyclerView rvEnrolledCourses, rvExploreCourses;
    ImageView ivBack;
    private ProgressDialog pg;

    private ExploreCoursesAdapter exploreCoursesAdapter;
    private MyCoursesAdapter myCoursesAdapter;
    private SharedPreferences sp;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        initializeControls();
    }

    private void initializeControls(){
        llRootLayout = findViewById(R.id.llRootLayout);
        pg = Common.showProgressDialog(MyCoursesActivity.this);
        rvEnrolledCourses = findViewById(R.id.rvEnrolledCourses);
        rvExploreCourses = findViewById(R.id.rvExploreCourses);
        sp = getSharedPreferences(getResources().getString(R.string.LoginSharedPreference), MODE_PRIVATE);

        userID = sp.getInt("UserId",0);

        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(view -> {
            Intent redirectToHome = new Intent(this, HomeActivity.class);
            startActivity(redirectToHome);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllCourses();
    }

    private void getAllCourses(){
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MyCoursesActivity.this, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getAllCourses();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }else {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<GetCoursesResponse> getCoursesResponseCall = apiInterface.getCourses(new GetCoursesRequest(userID));

            getCoursesResponseCall.enqueue(new Callback<GetCoursesResponse>() {
                @Override
                public void onResponse(Call<GetCoursesResponse> call, Response<GetCoursesResponse> response) {
                    try {
                        if(response.isSuccessful()){
                            pg.dismiss();
//                            Common.showSnack_Dark(llRootLayout, "Success..!!");
                            // My Courses recycler
                            List<GetMyCoursesDatum> getMyCoursesDatumList = response.body().getCourses().getMyCoursesDatumList();
                            myCoursesAdapter = new MyCoursesAdapter(getMyCoursesDatumList);
                            myCoursesAdapter.SetOnItemClickListener(new MyCoursesAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent redirectToCourseDetail = new Intent(MyCoursesActivity.this, CourseDetailsActivity.class);
                                    redirectToCourseDetail.putExtra("Course_ID", getMyCoursesDatumList.get(position).getCourseID());
                                    startActivity(redirectToCourseDetail);
                                }
                            });
                            rvEnrolledCourses.setAdapter(myCoursesAdapter);

                            //Explore Courses recycler
                            List<GetExploreCoursesDatum> getExploreCoursesDatumList = response.body().getCourses().getExploreCoursesDatumList();
                            exploreCoursesAdapter = new ExploreCoursesAdapter(MyCoursesActivity.this, getExploreCoursesDatumList);
                            exploreCoursesAdapter.SetOnItemClickListener(new ExploreCoursesAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent redirectToCourseDetail = new Intent(MyCoursesActivity.this, CourseDetailsActivity.class);
                                    redirectToCourseDetail.putExtra("Course_ID", getExploreCoursesDatumList.get(position).getCourseID());
                                    startActivity(redirectToCourseDetail);
                                }
                            });
                            rvExploreCourses.setAdapter(exploreCoursesAdapter);
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
                public void onFailure(Call<GetCoursesResponse> call, Throwable t) {
                    pg.dismiss();
                    t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyCoursesActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getAllCourses();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }
}