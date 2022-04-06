package com.example.feelthenote.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.feelthenote.Model.StudentDashboardCourseCarousel;
import com.example.feelthenote.R;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.List;

public class CourseCarouselAdapter extends CardSliderAdapter<CourseCarouselAdapter.CourseViewHolder> {
    private Context context = null;
    private final List<StudentDashboardCourseCarousel> course;

    public CourseCarouselAdapter(List<StudentDashboardCourseCarousel> course){
        this.course = course;
    }

    @Override
    public int getItemCount(){
        return course.size();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_carousel_item_layout, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void bindVH(@NonNull CourseViewHolder courseViewHolder, int position) {
        StudentDashboardCourseCarousel courseCarouselItem = course.get(position);
        int totalAllotedSessions = courseCarouselItem.getTotal(),
                attendedSessions = courseCarouselItem.getAttended(),
                availableSessions = courseCarouselItem.getRegular() + courseCarouselItem.getCarried() + courseCarouselItem.getExtra(),
                bookedSessions = courseCarouselItem.getBooked(),
                missedSessions = courseCarouselItem.getCancelled() + courseCarouselItem.getMissed() + courseCarouselItem.getExpired();
        String imageName = courseCarouselItem.getCardImage();

        String courseName = courseCarouselItem.getCourseName(),
                courseId = courseCarouselItem.getCourseID();
        String instructor = courseCarouselItem.getCourseInstructor();

//        Drawable courseImage = courseCarouselItem.getCourseImage();
        String courseImageURL = "http://ftn.locuslogs.com/images/card/"+imageName+ ".jpg";
        Log.e("image", "bindVH: "+courseImageURL);
        courseViewHolder.bindViewAndData(context, totalAllotedSessions, attendedSessions, availableSessions, bookedSessions, missedSessions, courseName, courseId, courseImageURL, instructor);
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView tvTotalAllotedSessions, tvAttendedSessions, tvAvailableSessions, tvBookedSessions, tvMissedSessions;
        TextView courseText, instructorText;
        LinearLayout courseCardBackgroundImage;

        public CourseViewHolder(View view){
            super(view);
            tvTotalAllotedSessions = view.findViewById(R.id.tvTotalAllotedSessions);
            tvAttendedSessions = view.findViewById(R.id.tvAttendedSessions);
            tvAvailableSessions = view.findViewById(R.id.tvAvailableSessions);
            tvBookedSessions = view.findViewById(R.id.tvBookedSessions);
            tvMissedSessions = view.findViewById(R.id.tvMissedSessions);

            courseText = view.findViewById(R.id.courseNameAndCode);
            instructorText = view.findViewById(R.id.instructor);
            courseCardBackgroundImage = view.findViewById(R.id.courseCard);
        }

        void bindViewAndData(Context context, int totalAllotedSessions, int attendedSessions, int availableSessions, int bookedSessions, int missedSessions, String courseName, String courseCode, String courseImageURL, String instructor){
            tvTotalAllotedSessions.setText(String.valueOf(totalAllotedSessions));
            tvAttendedSessions.setText(String.valueOf(attendedSessions));
            tvAvailableSessions.setText(String.valueOf(availableSessions));
            tvBookedSessions.setText(String.valueOf(bookedSessions));
            Log.e("booked", "bindViewAndData: "+bookedSessions);
            tvMissedSessions.setText(String.valueOf(missedSessions));

            courseText.setText(courseName +" - "+ courseCode);
            instructorText.setText(instructor);

            Glide.with(context)
                    .load(courseImageURL)
                    .transition(DrawableTransitionOptions.withCrossFade(700))
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            courseCardBackgroundImage.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) { }
                    });
        }
    }
}
