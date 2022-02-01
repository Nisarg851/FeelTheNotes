package com.example.feelthenote.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.Model.Course;
import com.example.feelthenote.R;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.ArrayList;

public class CourseCarousel extends CardSliderAdapter<CourseCarousel.CourseViewHolder> {
    private final ArrayList<Course> courses;

    public CourseCarousel(ArrayList<Course> courses){
        this.courses = courses;
    }

    @Override
    public int getItemCount(){
        return courses.size();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_layout, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void bindVH(@NonNull CourseViewHolder courseViewHolder, int position) {
        Course course = courses.get(position);
        int totalAllotedSessions = course.getTotalAllotedSessions(),
                attended = course.getAttended(),
                pending = course.getPending(),
                available = course.getAvailable(),
                expired = course.getExpired(),
                daysLeft = course.getDaysLeft(),
                extraHrs = course.getExtraHrs();
        String courseName = course.getCourseName(),
                courseCode = course.getCourseCode();
        String instructor = course.getInstructor();
        Drawable courseImage = course.getCourseImage();
        courseViewHolder.bindViewAndData(totalAllotedSessions, attended, pending, available, expired, daysLeft, extraHrs, courseName+" - "+courseCode, instructor, courseImage);
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView totalAllotedSessionsText, attendedText, pendingText, availableText, expiredText, daysLeftText, extraHrsText;
        TextView courseText, instructorText;
        LinearLayout courseCardBackgroundImage;

        public CourseViewHolder(View view){
            super(view);
            totalAllotedSessionsText = view.findViewById(R.id.totalAllotedSessions);
            attendedText = view.findViewById(R.id.attended);
            pendingText =  view.findViewById(R.id.pending);
            availableText = view.findViewById(R.id.available);
            expiredText = view.findViewById(R.id.expired);
            daysLeftText = view.findViewById(R.id.daysLeft);
            extraHrsText = view.findViewById(R.id.extraHrs);
            courseText = view.findViewById(R.id.courseNameAndCode);
            instructorText = view.findViewById(R.id.instructor);
            courseCardBackgroundImage = view.findViewById(R.id.courseCard);
        }

        void bindViewAndData(int totalAllotedSessions, int attended, int pending, int available, int expired, int daysLeft, int extraHrs, String courseName, String instructor, Drawable courseImage){
            totalAllotedSessionsText.setText(String.valueOf(totalAllotedSessions));
            attendedText.setText(String.valueOf(attended));
            pendingText.setText(String.valueOf(pending));
            availableText.setText(String.valueOf(available));
            expiredText.setText(String.valueOf(expired));
            daysLeftText.setText(String.valueOf(daysLeft));
            extraHrsText.setText(String.valueOf(extraHrs));
            courseText.setText(String.valueOf(courseName));
            instructorText.setText(String.valueOf(instructor));
            courseCardBackgroundImage.setBackground(courseImage);
        }
    }
}
