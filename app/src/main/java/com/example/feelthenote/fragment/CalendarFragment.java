package com.example.feelthenote.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.feelthenote.Activity.CourseDetailsActivity;
import com.example.feelthenote.Activity.MyCoursesActivity;
import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Model.GetExploreCoursesDatum;
import com.example.feelthenote.Model.GetMyCoursesDatum;
import com.example.feelthenote.Model.SessionData;
import com.example.feelthenote.Model.StudentCalenderdData;
import com.example.feelthenote.Network.GetCoursesRequest;
import com.example.feelthenote.Network.GetCoursesResponse;
import com.example.feelthenote.Network.GetStudentCalenderRequest;
import com.example.feelthenote.Network.GetStudentCalenderResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.RecyclerViewAdapter.ExploreCoursesAdapter;
import com.example.feelthenote.RecyclerViewAdapter.MyCoursesAdapter;
import com.example.feelthenote.RecyclerViewAdapter.WeekDayAndDateListAdapter;
import com.example.feelthenote.RecyclerViewAdapter.WeeklySlotsAdapter;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarFragment extends Fragment {

    RecyclerView rvWeekDaysContainer, rvWeeklySlots;

    Date weekStartDate;
    private int daysInWeek = 7;
    private int slotsInADay = 48;
    int timeSlotCol = 1;
    private int totalSlots = (daysInWeek+timeSlotCol)*slotsInADay;
    private SharedPreferences sp;
    private Calendar calendar;

    public CalendarFragment(Date weekStartDate){
        this.weekStartDate = weekStartDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initilizeControls(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initilizeDayRecyclerView();
        }
        getWeekSlotsData();
        return view;
    }

    private void initilizeControls(View view) {
        rvWeekDaysContainer = view.findViewById(R.id.rvWeekDaysContainer);
        rvWeeklySlots = view.findViewById(R.id.rvWeeklySlots);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initilizeDayRecyclerView() {
        // Set Horizontal LinearLayout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvWeekDaysContainer.setLayoutManager(layoutManager);

        // Set Adapter
        WeekDayAndDateListAdapter weekDayAndDateListAdapter = new WeekDayAndDateListAdapter(weekStartDate);
        rvWeekDaysContainer.setAdapter(weekDayAndDateListAdapter);
    }

    private void initilizeWeeklySlotRecyclerView(List<SessionData> weeklySlotData) {
        WeeklySlotsAdapter weeklySlotsAdapter = new WeeklySlotsAdapter(weeklySlotData);
        weeklySlotsAdapter.setWeekDaySlotClickListeners(new WeeklySlotsAdapter.WeekDaySlotClickListeners() {
            @Override
            public void cellClicker(WeeklySlotsAdapter.ViewHolderForWeekDaySlot viewHolder, int position, int[] daySlots) {
                if(position>=(totalSlots-daysInWeek))
                    return;
                if(daySlots[position] == 0){
                    daySlots[position] = 1;
                    daySlots[position+8] = 1;
                }else{
                    daySlots[position] = 0;
                    daySlots[position+8] = 0;
                }
                weeklySlotsAdapter.onBindViewHolder(viewHolder, position);
                WeeklySlotsAdapter.ViewHolderForWeekDaySlot viewHolderBelow = (WeeklySlotsAdapter.ViewHolderForWeekDaySlot) rvWeeklySlots.findViewHolderForAdapterPosition(position+8);
                weeklySlotsAdapter.onBindViewHolder(viewHolderBelow, position+8);
//                Toast.makeText(getContext(), "Clicked Cell no. "+position, Toast.LENGTH_SHORT).show();
            }
        });
        rvWeeklySlots.setAdapter(weeklySlotsAdapter);
    }

    private void getWeekSlotsData(){
        Context context = getActivity();
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(context, R.style.AlertDialogTheme);
            builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
            builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
            builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getWeekSlotsData();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }else {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(weekStartDate);
            calendar.add(Calendar.DATE, 6);

            sp = context.getSharedPreferences(getResources().getString(R.string.LoginSharedPreference), context.MODE_PRIVATE);
            int studentID = sp.getInt("UserId",0);
            int studentMappingId = 0;
            String fromDate =  sd.format(weekStartDate), toDate = sd.format(calendar.getTime());
            String status = "All";

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<GetStudentCalenderResponse> getStudentCalenderResponseCall = apiInterface.getStudentCalender(new GetStudentCalenderRequest(studentID, studentMappingId, fromDate, toDate, status));

            getStudentCalenderResponseCall.enqueue(new Callback<GetStudentCalenderResponse>() {
                @Override
                public void onResponse(Call<GetStudentCalenderResponse> call, Response<GetStudentCalenderResponse> response) {
                    try {
                        if(response.isSuccessful()){
//                            pg.dismiss();
//                            Common.showSnack_Dark(llRootLayout, "Success..!!");
                            if(response.body().getStatusCode()==1){
                                StudentCalenderdData studentCalenderdData = response.body().getStudentCalenderdData();
                                List<SessionData> sessionDataList = studentCalenderdData.getSessionData();
                                initilizeWeeklySlotRecyclerView(sessionDataList);
                            }
                        } else {
//                            pg.dismiss();
//                            Common.showSnack_Dark(llRootLayout, response.errorBody().string());
                        }
                    }catch (Exception ex) {
//                        pg.dismiss();
//                        Common.showSnack_Dark(llRootLayout, ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetStudentCalenderResponse> call, Throwable t) {
//                    pg.dismiss();
                    t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getWeekSlotsData();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }
}