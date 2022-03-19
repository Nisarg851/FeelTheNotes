package com.example.feelthenote.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.feelthenote.Helper.Common;
import com.example.feelthenote.Model.CourseOtherPackages;
import com.example.feelthenote.Network.AddFeeDetailRequest;
import com.example.feelthenote.Network.AddFeeDetailResponse;
import com.example.feelthenote.Network.PromoCodeRequest;
import com.example.feelthenote.Network.PromoCodeResponse;
import com.example.feelthenote.R;
import com.example.feelthenote.Receiver.ConnectivityReceiver;
import com.example.feelthenote.Retrofit.ApiClient;
import com.example.feelthenote.Retrofit.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageSubscriptionDialog extends AppCompatDialogFragment {

    Context context = null;

    ProgressDialog pg;

    private CourseOtherPackages selectedPackage = null;

    private String courseID, promoCodeValidated = "";
    private BigInteger studentMappingID;
    private Integer studentID = null;
    private ArrayList<CourseOtherPackages> courseOtherPackages = new ArrayList<CourseOtherPackages>();

    private List<String> batches = new ArrayList();
    private List<String> packages = new ArrayList<>();
    private String teachMode, batch, discountType;
    int session, duration, fees, discount;

    private LinearLayout llStartDateContainer;

//    private ToggleButton rgPackageMode;
    private RadioGroup rgPackageMode;
    private RadioButton radioButton;
    private TextInputLayout spBatchSpinner, spPackages;
    private AutoCompleteTextView sptvBatchItems, sptvPackageItems;
    private EditText etPromoCode;
    private LinearLayout llOtherSubmitValue;
    private TextView tvDiscountApplied, tvFeesOriginal, tvFeesToPay, tvStartDate;

    private Button btnCheckPromocode, btnAddFeeDetails;
    Calendar newCalendar;

    // Date Objects
    DatePickerDialog StartTime;

    public PackageSubscriptionDialog(Integer Student_ID,String courseID, BigInteger studentMappingID,List<CourseOtherPackages> courseOtherPackages){
        if(courseOtherPackages!=null){
            context = getContext();
            this.courseID = courseID;
            this.studentMappingID = studentMappingID;
            this.courseOtherPackages.addAll(courseOtherPackages);
            this.studentID = Student_ID;
        }
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.package_subscription_dialog_layout, null);

        initiateControls(view);
        initiateListeners();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        return builder.create();
    }

    private void initiateControls(View view){

        pg = Common.showProgressDialog(this.getActivity());

        llStartDateContainer = view.findViewById(R.id.llStartDateContainer);

        rgPackageMode = view.findViewById(R.id.rgPackageMode);
        radioButton = rgPackageMode.findViewById(R.id.rbOnlineMode);

        sptvBatchItems = view.findViewById(R.id.sptvBatchItems);
        sptvPackageItems = view.findViewById(R.id.sptvPackageItems);

        spBatchSpinner = view.findViewById(R.id.spBatchSpinner);
        spPackages = view.findViewById(R.id.spPackages);

        llOtherSubmitValue = view.findViewById(R.id.llOtherSubmitValue);

        etPromoCode = view.findViewById(R.id.etPromoCode);

        btnCheckPromocode = view.findViewById(R.id.btnCheckPromocode);

        tvDiscountApplied = view.findViewById(R.id.tvDiscountApplied);
        tvFeesOriginal = view.findViewById(R.id.tvFeesOriginal);
        tvFeesToPay = view.findViewById(R.id.tvFeesToPay);
        tvStartDate = view.findViewById(R.id.tvStartDate);

        tvStartDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        btnAddFeeDetails = view.findViewById(R.id.btnAddFeeDetails);

        //////////////////////////////////////  Calander Dialog and Date Configurations /////////////////////////////////////////////
        Log.e("context", "before context is null: "+(context==null));
        if(context==null)
            context = getActivity();
        Log.e("context", "after context is null: "+(context==null));
        newCalendar = Calendar.getInstance();
        StartTime = new DatePickerDialog(context, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            tvStartDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        Date date = newCalendar.getTime();
        StartTime.getDatePicker().setMinDate(date.getTime());
        newCalendar.add(Calendar.MONTH,3);
        date = newCalendar.getTime();
        StartTime.getDatePicker().setMaxDate(date.getTime());
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    @SuppressLint("NewApi")
    private void initiateListeners(){

        rgPackageMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                llStartDateContainer.setVisibility(View.GONE);
                spBatchSpinner.setVisibility(View.GONE);
                sptvBatchItems.setText(null);
                spPackages.setVisibility(View.GONE);
                llOtherSubmitValue.setVisibility(View.GONE);

                radioButton.setTypeface(radioButton.getTypeface(), Typeface.ITALIC);
                radioButton.setTextSize(14);
                radioButton = radioGroup.findViewById(id);
                teachMode = radioButton.getText().toString();
                radioButton.setTypeface(radioButton.getTypeface(), Typeface.BOLD);
                radioButton.setTextSize(18);

                batches = courseOtherPackages.stream()
                        .filter(courseOtherPackages1 -> courseOtherPackages1.getMode().equals(teachMode))
                        .map(CourseOtherPackages::getBatchStrength).distinct().collect(Collectors.toList());
                ArrayAdapter batchSpinnerAdapter = new ArrayAdapter(context, R.layout.spinner_item_layout, batches);
                Log.e("Dbox", "batchSpinnerAdapter: "+batches.toString());
                sptvBatchItems.setAdapter(batchSpinnerAdapter);

                spBatchSpinner.setVisibility(View.VISIBLE);
                llStartDateContainer.setVisibility(View.VISIBLE);
            }
        });

        sptvBatchItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemView, int i, long l) {
                spPackages.setVisibility(View.GONE);
                sptvPackageItems.setText(null);
                llOtherSubmitValue.setVisibility(View.GONE);
                teachMode = radioButton.getText().toString();
                batch = sptvBatchItems.getText().toString();
                packages = courseOtherPackages.stream()
                        .filter(courseOtherPackages1 -> (courseOtherPackages1.getMode().equals(teachMode)&&courseOtherPackages1.getBatchStrength().equals(batch) ))
                        .map( courseOtherPackagesTemp -> courseOtherPackagesTemp.getSessions()+" Sesssion in "+courseOtherPackagesTemp.getDuration()+" Days")
                        .collect(Collectors.toList());
                ArrayAdapter packageSpinnerAdapter = new ArrayAdapter(context, R.layout.spinner_item_layout, packages);
                sptvPackageItems.setAdapter(packageSpinnerAdapter);
                Log.e("Dbox", "onItemClick: sptvPackageItems "+packages.toString());
                spPackages.setVisibility(View.VISIBLE);
            }
        });

        tvStartDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                StartTime.show();
                return true;
            }
        });

        sptvPackageItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                session = Integer.parseInt(sptvPackageItems.getText().toString().split(" Sesssion in ")[0]);
                duration = Integer.parseInt(sptvPackageItems.getText().toString().split(" Sesssion in ")[1].split(" Days")[0]);

                selectedPackage = courseOtherPackages.stream().filter(pack -> (
                        pack.getMode().equals(teachMode)
                                &&pack.getBatchStrength().equals(batch)
                                &&(pack.getSessions()==session)
                                &&(pack.getDuration()==duration)
                        )
                ).collect(Collectors.toList()).get(0);

                llOtherSubmitValue.setVisibility(View.VISIBLE);

                discountType = selectedPackage.getDiscountType();
                discount = selectedPackage.getDiscount();
                fees = selectedPackage.getFee();

                calculateDiscount(fees, discount, discountType);
            }
        });

        etPromoCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etPromoCode.setTextColor(getResources().getColor(R.color.colorBlack));
                calculateDiscount(fees, 0, "Flat");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                etPromoCode.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });

        btnCheckPromocode.setOnClickListener(view -> {
            String promoCode = etPromoCode.getText().toString();
            String packageId = selectedPackage.getPackageID();
            checkPromoCode(promoCode, packageId);
        });


        btnAddFeeDetails.setOnClickListener(view -> {
            addFeeDetail();
        });
    }

    private void addFeeDetail(){
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();

        }else {
//            int Student_ID = studentID;
            int Student_ID = 23;
            String Course_ID = this.courseID;
            String Package_ID = selectedPackage.getPackageID();
            String Promo_Code = promoCodeValidated;
            if(!etPromoCode.getText().toString().equals(promoCodeValidated)){
                etPromoCode.setTextColor(getResources().getColor(R.color.invalidPromoCode));
                Toast.makeText(context, "Promocode has been changed, Please Validate again!", Toast.LENGTH_LONG);
                return ;
            }
            String Start_Date = tvStartDate.getText().toString();
            BigInteger Student_Mapping_ID = this.studentMappingID;

            Log.e("dialogValues", "Student_ID: "+Student_ID+" Course_ID: "+Course_ID+" Package_ID: "+Package_ID+" Promo_Code: "+Promo_Code+" StartDate: "+Start_Date+" Student_Mapping_ID: "+Student_Mapping_ID);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<AddFeeDetailResponse> addFeeDetailResponseCall = apiInterface.addFeeDetail(new AddFeeDetailRequest(Student_ID, Course_ID, Package_ID, Promo_Code, Start_Date, Student_Mapping_ID));

            addFeeDetailResponseCall.enqueue(new Callback<AddFeeDetailResponse>() {

                @Override
                public void onResponse(Call<AddFeeDetailResponse> call, Response<AddFeeDetailResponse> response) {
                    try{
                        if (response.isSuccessful()) {
                            if(response.body().getStatusCode() == 1) {
                                pg.dismiss();
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                pg.dismiss();
                                Toast.makeText(context, "Something Went Wrong!1", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            pg.dismiss();
                            Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        pg.dismiss();
                        Toast.makeText(context, "An Unexpected Error Occured", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddFeeDetailResponse> call, Throwable t) {
                    pg.dismiss();
                    Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void calculateDiscount(int fees, int discount, String discountType){
        int finalFees = 0;
        if(discount==0){
            tvFeesOriginal.setVisibility(View.GONE);
            tvDiscountApplied.setVisibility(View.GONE);
            tvFeesToPay.setText(""+fees);
        }else{
            tvDiscountApplied.setVisibility(View.VISIBLE);
            tvFeesOriginal.setVisibility(View.VISIBLE);
            String discountApplied = "Discount Applied: "+(discountType.equals("Flat") ? "Flat "+discount+" Rs." : discount+" %");
            tvDiscountApplied.setText(discountApplied);
            tvFeesOriginal.setPaintFlags(tvFeesOriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvFeesOriginal.setText(""+fees);
            if(discountType.equals("Flat")){
                finalFees = fees - discount;
            }else{
                finalFees = Math.round(fees - (fees/100)*discount);
            }
            tvFeesToPay.setText(String.valueOf(finalFees));
        }
    }

    private void checkPromoCode(String promoCode, String packageID){
        pg.show();
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<PromoCodeResponse> promoCodeCall = apiInterface.getPromoDiscount(new PromoCodeRequest(promoCode,packageID));

            promoCodeCall.enqueue(new Callback<PromoCodeResponse>() {
                @Override
                public void onResponse(Call<PromoCodeResponse> call, Response<PromoCodeResponse> response) {
                    pg.dismiss();
                    try{
                        if (response.isSuccessful()) {
                            if(response.body().getStatusCode()==1){
                                if(response.body().getStatus()==null){
                                    pg.dismiss();
                                    promoCodeValidated = "";
                                    calculateDiscount(fees, 0, "Flat");
                                    etPromoCode.setTextColor(getResources().getColor(R.color.invalidPromoCode));
                                    tvDiscountApplied.setText("Promocode INCORRECT");
                                    tvDiscountApplied.setVisibility(View.VISIBLE);
                                    Toast.makeText(context, "Promocode INCORRECT", Toast.LENGTH_LONG).show();
                                } else if(response.body().getStatus().equals("ACTIVE")) {
                                    pg.dismiss();
                                    promoCodeValidated = etPromoCode.getText().toString();

                                    etPromoCode.setTextColor(getResources().getColor(R.color.validPromoCode));

                                    int discount = response.body().getDiscount();
                                    String discountType = response.body().getDiscountType();

                                    calculateDiscount(fees, discount, discountType);
                                    Toast.makeText(context, "Promocode Applied", Toast.LENGTH_LONG).show();
                                } else if(response.body().getStatus().equals("INACTIVE")){
                                    promoCodeValidated = "";
                                    calculateDiscount(fees, 0, "Flat");
                                    etPromoCode.setTextColor(getResources().getColor(R.color.invalidPromoCode));
                                    tvDiscountApplied.setText("Promocode INACTIVE");
                                    tvDiscountApplied.setVisibility(View.VISIBLE);
                                    Toast.makeText(context, "Promocode INACTIVE", Toast.LENGTH_LONG).show();
                                } else if(response.body().getStatus().equals("EXPIRED")){
                                    pg.dismiss();
                                    promoCodeValidated = "";
                                    calculateDiscount(fees, 0, "Flat");
                                    etPromoCode.setTextColor(getResources().getColor(R.color.invalidPromoCode));
                                    tvDiscountApplied.setText("Promocode EXPIRED");
                                    tvDiscountApplied.setVisibility(View.VISIBLE);
                                    Toast.makeText(context, "Promocode EXPIRED", Toast.LENGTH_LONG).show();
                                }else {
                                    pg.dismiss();
                                    promoCodeValidated = "";
                                    calculateDiscount(fees, 0, "Flat");
                                    Toast.makeText(context, "Unexpected Error", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                pg.dismiss();
                                Toast.makeText(context,"Network Error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            pg.dismiss();
                            Toast.makeText(context,response.errorBody().string(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        pg.dismiss();
                        Toast.makeText(context,ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PromoCodeResponse> call, Throwable t) {
                    pg.dismiss();
                    t.printStackTrace();
                    Toast.makeText(context,R.string.NetworkErrorMsg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
