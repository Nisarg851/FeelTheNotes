package com.example.feelthenote.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.feelthenote.Model.GetMyCoursesDatum;
import com.example.feelthenote.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyCoursesViewHolder>{
    private Context context = null;

    private String BASE_URL = "http://ftn.locuslogs.com/images/card/";

    private List<GetMyCoursesDatum> Courses;
    OnItemClickListener onItemClickListener;

    public MyCoursesAdapter(List<GetMyCoursesDatum> courses){
        Courses = courses;
    }

    @NonNull
    @Override
    public MyCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card_layout, parent, false);
        return new MyCoursesViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyCoursesViewHolder holder, int position) {
        GetMyCoursesDatum course = Courses.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.courseID.setText(course.getCourseID());

        int color = course.getSubscription().equals("Active") ? R.color.Active : R.color.InActive;

        holder.courseSubscriptionStatus.setText(course.getSubscription());
        holder.cvSubscription.setStrokeColor(ContextCompat.getColor(context, color));

        String imageURL = BASE_URL + course.getCourseID() + course.getCardImage() .replace(':','_')+ ".jpg";

        Glide.with(context)
                .load(imageURL)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable courseContainerBackgroundImage, @Nullable Transition<? super Drawable> transition) {
                        holder.courseContainer.setBackground(courseContainerBackgroundImage);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return Courses.size();
    }

    class MyCoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView courseName, courseID, courseSubscriptionStatus;
        LinearLayout courseContainer;
        CardView cvCourse;
        MaterialCardView cvSubscription;
        public MyCoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCourse = itemView.findViewById(R.id.cvCourse);
            courseName = itemView.findViewById(R.id.tvCourseName);
            courseID = itemView.findViewById(R.id.tvCourseId);
            courseContainer = itemView.findViewById(R.id.llCourseContainer);
            courseSubscriptionStatus = itemView.findViewById(R.id.tvSubscription);
            cvSubscription = itemView.findViewById(R.id.cvSubscription);

            cvCourse.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(view,getLayoutPosition());
        }
    }

    public interface OnItemClickListener
    {
        public void onItemClick(View view,int position);
    }

    public void SetOnItemClickListener(final MyCoursesAdapter.OnItemClickListener itemClickListener)
    {
        this.onItemClickListener = itemClickListener;
    }
}
