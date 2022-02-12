package com.example.feelthenote.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.feelthenote.Model.GetMyCoursesDatum;
import com.example.feelthenote.Network.CourseDetailsResponse;
import com.example.feelthenote.Network.CourseDetailsRequest;
import com.example.feelthenote.Network.GetCoursesResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.RecyclerViewAdapter.MyCoursesAdapter;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity {
    String Course_ID;
    TextView tempView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent recievedIntent = getIntent();
        Course_ID = recievedIntent.getStringExtra("Course_ID");
        tempView = findViewById(R.id.textView5);
        tempView.setText(Course_ID);
    }

    private void getCourseDetails(){
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(CourseDetailsActivity.this, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getCourseDetails();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }else {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<CourseDetailsResponse> courseDetailsResponse = apiInterface.getCourseDetail(new CourseDetailsRequest(23,Course_ID));

            courseDetailsResponse.enqueue(new Callback<GetCoursesResponse>() {
                @Override
                public void onResponse(Call<GetCoursesResponse> call, Response<GetCoursesResponse> response) {
                    try {
                        if(response.isSuccessful()){
                            pg.dismiss();
//                            Common.showSnack_Dark(llRootLayout, "Success..!!");
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
                        } else {
                            pg.dismiss();
//                            Common.showSnack_Dark(llRootLayout, response.errorBody().string());
                        }
                    }catch (Exception ex) {
                        pg.dismiss();
//                        Common.showSnack_Dark(llRootLayout, ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetCoursesResponse> call, Throwable t) {
                    pg.dismiss();
                    t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getCourseDetails();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }
}