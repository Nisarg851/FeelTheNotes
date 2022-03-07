package com.example.feelthenote.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.feelthenote.Model.CourseOtherPackages;
import com.example.feelthenote.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PackageSubscriptionDialog extends AppCompatDialogFragment {

    private CourseOtherPackages selectedPackage = null;

    private ArrayList<CourseOtherPackages> courseOtherPackages = new ArrayList<CourseOtherPackages>();

    private List<String> batches = new ArrayList();
    private List<String> packages = new ArrayList<>();
    private String teachMode, batch, discountType;
    int session, duration, fees, discount;

    private ToggleButton rgPackageMode;
    private TextInputLayout spBatchSpinner, spPackages;
    private AutoCompleteTextView sptvBatchItems, sptvPackageItems;
    private LinearLayout llOtherSubmitValue;
    private TextView tvDiscountApplied, tvFeesOriginal, tvFeesToPay;

    public PackageSubscriptionDialog(List<CourseOtherPackages> courseOtherPackages){
        if(courseOtherPackages!=null){
            this.courseOtherPackages.addAll(courseOtherPackages);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        return builder.create();
    }

    private void initiateControls(View view){
        rgPackageMode = view.findViewById(R.id.rgPackageMode);
        sptvBatchItems = view.findViewById(R.id.sptvBatchItems);
        sptvPackageItems = view.findViewById(R.id.sptvPackageItems);

        spBatchSpinner = view.findViewById(R.id.spBatchSpinner);
        spPackages = view.findViewById(R.id.spPackages);

        llOtherSubmitValue = view.findViewById(R.id.llOtherSubmitValue);

        tvDiscountApplied = view.findViewById(R.id.tvDiscountApplied);
        tvFeesOriginal = view.findViewById(R.id.tvFeesOriginal);
        tvFeesToPay = view.findViewById(R.id.tvFeesToPay);
    }

    @SuppressLint("NewApi")
    private void initiateListeners(){
        rgPackageMode.setOnClickListener(viewItem -> {

            spBatchSpinner.setVisibility(View.GONE);
            sptvBatchItems.setText(null);
            spPackages.setVisibility(View.GONE);
            llOtherSubmitValue.setVisibility(View.GONE);

            teachMode = rgPackageMode.getText().toString();

            batches = courseOtherPackages.stream()
                    .filter(courseOtherPackages1 -> courseOtherPackages1.getMode().equals(teachMode))
                    .map(CourseOtherPackages::getBatchStrength).distinct().collect(Collectors.toList());
            ArrayAdapter batchSpinnerAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, batches);
            Log.e("Dbox", "batchSpinnerAdapter: "+batches.toString());
            sptvBatchItems.setAdapter(batchSpinnerAdapter);

            spBatchSpinner.setVisibility(View.VISIBLE);
        });

        sptvBatchItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemView, int i, long l) {
                spPackages.setVisibility(View.GONE);
                sptvPackageItems.setText(null);
                llOtherSubmitValue.setVisibility(View.GONE);
                teachMode = rgPackageMode.getText().toString();
                batch = sptvBatchItems.getText().toString();
                packages = courseOtherPackages.stream()
                        .filter(courseOtherPackages1 -> (courseOtherPackages1.getMode().equals(teachMode)&&courseOtherPackages1.getBatchStrength().equals(batch) ))
                        .map( courseOtherPackagesTemp -> courseOtherPackagesTemp.getSessions()+" Sesssion in "+courseOtherPackagesTemp.getDuration()+" Days")
                        .collect(Collectors.toList());
                ArrayAdapter packageSpinnerAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, packages);
                sptvPackageItems.setAdapter(packageSpinnerAdapter);
                Log.e("Dbox", "onItemClick: sptvPackageItems "+packages.toString());
                spPackages.setVisibility(View.VISIBLE);
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

                float finalFees = 0;
                if(discount==0){
                    tvFeesOriginal.setVisibility(View.GONE);
                    tvDiscountApplied.setVisibility(View.GONE);
                    tvFeesToPay.setText(fees);
                }else{
                    tvDiscountApplied.setVisibility(View.VISIBLE);
                    tvFeesOriginal.setVisibility(View.VISIBLE);
                    String discountApplied = "Discount Applied: "+(discountType.equals("Flat") ? "Flat "+discount+" Rs." : discount+" %");
                    tvDiscountApplied.setText(discountApplied);
                    tvFeesOriginal.setPaintFlags(tvFeesOriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    tvFeesOriginal.setText(fees);
                    if(discountType.equals("Flat")){
                        finalFees = fees - discount;
                    }else{
                        finalFees = fees - (fees/100)*discount;
                    }
                    tvFeesToPay.setText(String.valueOf(finalFees));
                }
            }
        });
    }
}
