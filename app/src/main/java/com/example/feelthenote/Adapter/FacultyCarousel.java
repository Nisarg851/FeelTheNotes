package com.example.feelthenote.Adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.Model.CourseFacultyDetails;
import com.example.feelthenote.Model.FacultyCarouselItem;
import com.example.feelthenote.R;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.List;

public class FacultyCarousel extends CardSliderAdapter<FacultyCarousel.FacultyViewHolder> {

    List<CourseFacultyDetails> faculties;
    public FacultyCarousel(List<CourseFacultyDetails> faculties){ this.faculties = faculties;}

    @Override
    public void bindVH(FacultyViewHolder facultyViewHolder, int position) {
        CourseFacultyDetails facultyCarouselItem = faculties.get(position);
        String facultyName = facultyCarouselItem.getName(),
                facultyAbout = facultyCarouselItem.getAbout();
        Drawable facultyImage = bitmap2Drawable(StringToBitMap(facultyCarouselItem.getImage()));
        facultyViewHolder.bindViewAndData(facultyName, facultyAbout, facultyImage);
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        @SuppressWarnings("deprecation")
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }

    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_carousel_item_layout, parent, false);
        return new FacultyCarousel.FacultyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    static class FacultyViewHolder extends RecyclerView.ViewHolder {

        TextView facultyName, facultyAbout;
        ImageView facultyImage;

        public FacultyViewHolder(View view){
            super(view);
            facultyName = view.findViewById(R.id.tvFacultyName);
            facultyAbout = view.findViewById(R.id.tvFacultyAbout);
            facultyImage = view.findViewById(R.id.ivFacultyImage);
        }

        void bindViewAndData(String facultyName, String facultyAbout, Drawable facultyImage){
            this.facultyName.setText(facultyName);
            this.facultyAbout.setText(facultyAbout);
            this.facultyImage.setImageDrawable(facultyImage);
        }
    }
}
