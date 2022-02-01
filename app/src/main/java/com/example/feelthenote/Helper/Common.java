package com.example.feelthenote.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.feelthenote.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context, R.style.DialogBox);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        return dialog;
    }

    public static void showSnack_Light(View view, String msg) {
        Snackbar snackbar;
        int seconds_30 = 100 * 30;
        snackbar = Snackbar.make(view, msg, seconds_30);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(view.getResources().getColor(R.color.colortext));
        TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(view.getResources().getColor(R.color.colorPrimary));
        textView.setTextSize(17);
        snackbar.show();
    }

    public static void showSnack_Dark(View view, String msg) {
        Snackbar snackbar;
        int seconds_30 = 100 * 30;
        snackbar = Snackbar.make(view, msg, seconds_30);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(view.getResources().getColor(R.color.colortext));
        textView.setTextSize(17);
        snackbar.show();
    }

    public static String seconds_To_Words(Integer second) {
        String since="";
        if (second < 60) {
            since += String.valueOf(second % 60) + "Secs";
        } else if (second < 3600){
            since += String.valueOf((second / 60) % 60) + "Min ";
            since += String.valueOf(second % 60) + "Secs";
        } else if (second < 86400){
            since += String.valueOf(((second / 60) / 60) % 24) + "Hrs ";
            since += String.valueOf((second / 60) % 60) + "Mins";
        } else {
            since += String.valueOf(((second / 60) / 60) / 24) + "Days ";
            since += String.valueOf(((second / 60) /60) % 24) + "Hrs";
        }
        return since;
    }

    public static String convertDateFormat(String date_string, String dateformat, String returnDateformat,View view) {
        Date initDate = null;
        String Parsedate=null;
        try {
            initDate = new SimpleDateFormat(dateformat).parse(date_string);
            SimpleDateFormat formatter = new SimpleDateFormat(returnDateformat);
            Parsedate = formatter.format(initDate);
        } catch (ParseException e) {
            Common.showSnack_Dark(view, e.getMessage());
        }
        return Parsedate;
    }

    public static String convertDateFormat(String date_string, String dateformat, String returnDateformat) {
        Date initDate = null;
        String Parsedate=null;
        try {
            initDate = new SimpleDateFormat(dateformat).parse(date_string);
            SimpleDateFormat formatter = new SimpleDateFormat(returnDateformat);
            Parsedate = formatter.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Parsedate;
    }
}
