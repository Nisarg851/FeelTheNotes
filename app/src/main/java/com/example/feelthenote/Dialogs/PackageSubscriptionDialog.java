package com.example.feelthenote.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.feelthenote.Model.CourseOtherPackages;
import com.example.feelthenote.R;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public class PackageSubscriptionDialog extends AppCompatDialogFragment {

    ArrayList<CourseOtherPackages> courseOtherPackages = new ArrayList<CourseOtherPackages>();

    BitSet batchSet = new BitSet(255);

    HashMap<String,ArrayList<String>> modePackages = new HashMap<>();
    HashMap<String[],ArrayList<String[]>> batchPackages = new HashMap<>();

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

        initiateDataSets();

        initiateListeners(view);

        String[] batchList = getResources().getStringArray(R.array.Batch_list);
//        ArrayAdapter batchArrayAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, batchList);
//
//        AutoCompleteTextView sptvBatchItems = view.findViewById(R.id.sptvBatchItems);
//        sptvBatchItems.setAdapter(batchArrayAdapter);

        String[] packageList = getResources().getStringArray(R.array.Package_list);
        ArrayAdapter packageArrayAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, packageList);

        AutoCompleteTextView sptvPackageItems = view.findViewById(R.id.sptvPackageItems);
        sptvPackageItems.setAdapter(packageArrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        return builder.create();
    }

    private void initiateDataSets(){
        int rowSize = courseOtherPackages.size();
        for(int row=0; row<rowSize; row++){
            CourseOtherPackages courseOtherPackage = courseOtherPackages.get(row);
            String mode = courseOtherPackage.getMode();
            int batch = courseOtherPackage.getBatchStrength();
            String batchStrength = String.valueOf(batch);
            String duration = courseOtherPackage.getDuration().toString();
            String session = courseOtherPackage.getSessions().toString();
            String fee = courseOtherPackage.getFee().toString();
            String packageId = courseOtherPackage.getPackageID();
            String discount = courseOtherPackage.getDiscount().toString();
            String discountType = courseOtherPackage.getDiscountType();

            if(modePackages.get(mode)==null){
                modePackages.put(mode, new ArrayList<String>(){{add(batchStrength);}});
                batchSet.set(batch,1);
            }else if(!batchSet.get(batch)){
                modePackages.get(mode).add(batchStrength);
//                modeBatchList.add(batchStrength);
//                modePackages.put(mode, modeBatchList);
            }

            if(batchPackages.get(batchStrength)==null){
                batchPackages.put(new String[]{mode,batchStrength}, new ArrayList<String[]>(){{add(new String[]{duration, session, fee, packageId, discount, discountType});}});
            }else{
                batchPackages.get(batchStrength).add(new String[]{duration, session, fee, packageId, discount, discountType});
//                batchRow.add(new String[]{duration, session, fee, packageId, discount, discountType});
//                batchPackages.put(new String[]{mode,batchStrength}, batchRow);
            }
        }
    }

    private void initiateListeners(View view){
        ToggleButton rgPackageMode = view.findViewById(R.id.rgPackageMode);

        AutoCompleteTextView sptvBatchItems = view.findViewById(R.id.sptvBatchItems);
//        String[] batchList = getResources().getStringArray(R.array.Batch_list);
//        ArrayAdapter batchArrayAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, batchList);
//        sptvBatchItems.setAdapter(batchArrayAdapter);

        rgPackageMode.setOnClickListener(viewItem -> {
            String teachMode = rgPackageMode.getText().toString();
            Log.e("Mode", "Mode Selected: "+ teachMode);
            ArrayList<String> batches = modePackages.get(teachMode);
            ArrayAdapter batchSpinnerAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, batches);
            sptvBatchItems.setAdapter(batchSpinnerAdapter);
        });

        sptvBatchItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemView, int i, long l) {
                TextView selectedItem = (TextView) itemView;
                String selectedBatch = selectedItem.getText().toString();
                selectedBatch = selectedBatch=="1" ? "One on One" : "Batch of "+selectedBatch;
                Log.e("BatchSpinner", "onItemClick: "+selectedBatch);
            }
        });
    }
}
