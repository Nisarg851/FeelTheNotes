package com.example.feelthenote.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.Model.GetMyCoursesDatum;
import com.example.feelthenote.R;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyCoursesViewHolder>{
    Context context;
    private List<GetMyCoursesDatum> Courses;
    OnItemClickListener onItemClickListener;

    public MyCoursesAdapter(List<GetMyCoursesDatum> courses){
        Courses = courses;
    }

    @NonNull
    @Override
    public MyCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card_layout, parent, false);
        return new MyCoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCoursesViewHolder holder, int position) {
        GetMyCoursesDatum course = Courses.get(position);
        holder.courseName.setText(course.getCourseName());
    }

    @Override
    public int getItemCount() {
        return Courses.size();
    }

    class MyCoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView courseName;
        CardView cvCourse;
        public MyCoursesViewHolder(@NonNull View itemView) {
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

    public void SetOnItemClickListener(final MyCoursesAdapter.OnItemClickListener itemClickListener)
    {
        this.onItemClickListener = itemClickListener;
    }
}
