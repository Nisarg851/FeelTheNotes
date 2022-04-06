package com.example.feelthenote.RecyclerViewAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.feelthenote.Model.GetExploreCoursesDatum;
import com.example.feelthenote.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ExploreCoursesAdapter extends RecyclerView.Adapter<ExploreCoursesAdapter.ViewHolder>{
    Context context;

    private String BASE_URL = "http://ftn.locuslogs.com/images/card/";

    private List<GetExploreCoursesDatum> Courses;
    OnItemClickListener onItemClickListener;

    public ExploreCoursesAdapter(Context context, List<GetExploreCoursesDatum> courses){
        this.context = context;
        Courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetExploreCoursesDatum course = Courses.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.courseID.setText(course.getCourseID());
        holder.cvSubscription.setVisibility(View.GONE);
        String imageURL = BASE_URL + course.getCardImage() + ".jpg";

//        holder.courseContainer.setBackground(bitmap2Drawable(StringToBitMap(course.getCardImage())));
        Glide.with(context)
                .load(imageURL)
                .transition(DrawableTransitionOptions.withCrossFade(700))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable backgroundImage, @Nullable Transition<? super Drawable> transition) {
                        holder.courseContainer.setBackground(backgroundImage);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cvCourse;
        TextView courseName, courseID, courseSubscriptionStatus;
        LinearLayout courseContainer;
        MaterialCardView cvSubscription;

        public ViewHolder(@NonNull View itemView) {
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
    public void SetOnItemClickListener(final OnItemClickListener itemClickListener)
    {
        this.onItemClickListener = itemClickListener;
    }
}
