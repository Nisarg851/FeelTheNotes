package com.example.feelthenote.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.HashMap;
import java.util.List;

public class PackageSubscriptionDialog extends AppCompatDialogFragment {

    private ArrayList<CourseOtherPackages> courseOtherPackages = new ArrayList<CourseOtherPackages>();
    private int listLength = 0;

    private ArrayList<String> batches = new ArrayList();
    private ArrayList<String> packages = new ArrayList<>();

    private HashMap<String, Boolean> batchValueExists = new HashMap<>();
    private HashMap<String, Boolean> packValueExists = new HashMap<>();

    public PackageSubscriptionDialog(List<CourseOtherPackages> courseOtherPackages){
        if(courseOtherPackages!=null){
            this.courseOtherPackages.addAll(courseOtherPackages);
            listLength = courseOtherPackages.size();
        }
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.package_subscription_dialog_layout, null);

        initiateListeners(view);

//        String[] batchList = getResources().getStringArray(R.array.Batch_list);
//        ArrayAdapter batchArrayAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, batchList);
//
//        AutoCompleteTextView sptvBatchItems = view.findViewById(R.id.sptvBatchItems);
//        sptvBatchItems.setAdapter(batchArrayAdapter);

//        String[] packageList = getResources().getStringArray(R.array.Package_list);
//        ArrayAdapter packageArrayAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, packageList);
//
//        AutoCompleteTextView sptvPackageItems = view.findViewById(R.id.sptvPackageItems);
//        sptvPackageItems.setAdapter(packageArrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        return builder.create();
    }

    private void initiateListeners(View view){
        ToggleButton rgPackageMode = view.findViewById(R.id.rgPackageMode);

        AutoCompleteTextView sptvBatchItems = view.findViewById(R.id.sptvBatchItems);
        AutoCompleteTextView sptvPackageItems = view.findViewById(R.id.sptvPackageItems);

//        String[] batchList = getResources().getStringArray(R.array.Batch_list);
//        ArrayAdapter batchArrayAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, batchList);
//        sptvBatchItems.setAdapter(batchArrayAdapter);

        rgPackageMode.setOnClickListener(viewItem -> {
            String teachMode = rgPackageMode.getText().toString();
            Log.e("Dbox", "Mode Selected: "+teachMode);
            batches.clear();
            batchValueExists.clear();
            Log.e("Dbox", "listlength: "+listLength);
            for (int row=0; row<listLength; row++){
                String mode = courseOtherPackages.get(row).getMode();
                Log.e("Dbox", "mode==teachMode "+mode+" "+teachMode+" "+(mode.equals(teachMode)));
                String packBatch = courseOtherPackages.get(row).getBatchStrength().toString();
                if(mode.equals(teachMode) && batchValueExists.get(packBatch)==null){
                    batches.add(packBatch);
                    batchValueExists.put(packBatch, true);
                }
            }
            ArrayAdapter batchSpinnerAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, batches);
            Log.e("Dbox", "batchSpinnerAdapter: "+batches.toString());
            sptvBatchItems.setAdapter(batchSpinnerAdapter);
            // Make batch spinner visible
        });

        sptvBatchItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemView, int i, long l) {
                packages.clear();
                packValueExists.clear();
                String teachMode = rgPackageMode.getText().toString();
                TextView selectedItem = (TextView) itemView;
                String selectedBatch = selectedItem.getText().toString();
                Log.e("Dbox", "listlength: "+listLength);
                for(int row=0; row<listLength; row++){
                    CourseOtherPackages pack = courseOtherPackages.get(row);
                    Log.e("Dbox", "For loop: "+pack.getBatchStrength().toString()+" "+selectedBatch);
                    String packBatch = pack.getBatchStrength().toString();
                    String packID = pack.getPackageID();
                    String mode = pack.getMode();
                    if(packBatch.equals(selectedBatch) && mode.equals(teachMode)  && packValueExists.get(packID)==null){
                        String duration = pack.getDuration().toString();
                        String session = pack.getSessions().toString();
                        String fees = pack.getFee().toString();
                        packages.add(session+" Sesssion in "+duration+" Days");
                        packValueExists.put(packID, true);
                    }
                }
                ArrayAdapter packageSpinnerAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, packages);
                sptvPackageItems.setAdapter(packageSpinnerAdapter);
                Log.e("Dbox", "onItemClick: sptvPackageItems "+packages.toString());
            }
        });
    }
}
