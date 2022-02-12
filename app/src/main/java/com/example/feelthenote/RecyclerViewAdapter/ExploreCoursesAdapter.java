package com.example.feelthenote.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.Model.GetExploreCoursesDatum;
import com.example.feelthenote.R;

import java.util.List;

public class ExploreCoursesAdapter extends RecyclerView.Adapter<ExploreCoursesAdapter.ViewHolder>{
    Context context;
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
    }

    @Override
    public int getItemCount() {
        return Courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cvCourse;
        TextView courseName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCourse = itemView.findViewById(R.id.cvCourse);
            courseName = itemView.findViewById(R.id.tvCourseName);
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
