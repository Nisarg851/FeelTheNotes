package com.example.feelthenote.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.feelthenote.R;

public class PackageSubscriptionDialog extends AppCompatDialogFragment {

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.package_subscription_dialog_layout, null);

        String[] batchList = getResources().getStringArray(R.array.Batch_list);
        ArrayAdapter batchArrayAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, batchList);

        AutoCompleteTextView sptvBatchItems = view.findViewById(R.id.sptvBatchItems);
        sptvBatchItems.setAdapter(batchArrayAdapter);

        String[] packageList = getResources().getStringArray(R.array.Package_list);
        ArrayAdapter packageArrayAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_layout, packageList);

        AutoCompleteTextView sptvPackageItems = view.findViewById(R.id.sptvPackageItems);
        sptvPackageItems.setAdapter(packageArrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        return builder.create();
    }
}
