package com.example.feelthenote.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.feelthenote.Model.CourseFacultyDetails;
import com.example.feelthenote.R;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.List;

public class FacultyCarouselAdapter extends CardSliderAdapter<FacultyCarouselAdapter.FacultyViewHolder> {

    private Context context = null;

    private String BASE_URL = "http://ftn.locuslogs.com/images/faculty_profile/";

    private List<CourseFacultyDetails> faculties;

    public FacultyCarouselAdapter(List<CourseFacultyDetails> faculties){
        this.faculties = faculties;
    }

    @Override
    public void bindVH(FacultyViewHolder facultyViewHolder, int position) {
        CourseFacultyDetails facultyCarouselItem = faculties.get(position);
        Integer facultyID = facultyCarouselItem.getFacultyID();
        String facultyName = facultyCarouselItem.getName(),
                facultyAbout = facultyCarouselItem.getAbout(),
                facultyProfileImageDate = facultyCarouselItem.getFacultyProfileImageDate();

        String facultyImage = facultyID+facultyProfileImageDate.replace(':','_')+ ".jpg";

        String facultyImageURL = BASE_URL+facultyImage;
        Log.e("faculty", "Image URL"+facultyImageURL);

        facultyViewHolder.bindViewAndData(context, facultyName, facultyAbout, facultyImageURL);
    }

    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_carousel_item_layout, parent, false);
        return new FacultyCarouselAdapter.FacultyViewHolder(view);
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

        void bindViewAndData(Context context, String facultyName, String facultyAbout, String facultyImageURL){
            this.facultyName.setText(facultyName);
            this.facultyAbout.setText(facultyAbout);

            Glide.with(context)
                    .load(facultyImageURL)
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .into(facultyImage);
        }
    }
}
