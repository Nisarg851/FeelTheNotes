package com.example.feelthenote.RecyclerViewAdapter;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.Model.GetMyCoursesDatum;
import com.example.feelthenote.R;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyCoursesViewHolder>{
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
        holder.courseID.setText(course.getCourseID());
        holder.courseContainer.setBackground(bitmap2Drawable(StringToBitMap(course.getCardImage())));
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
//    public static Drawable bytes2Drawable(byte[] b) {
//        Bitmap bitmap = bytes2Bitmap(b);
//        return bitmap2Drawable(bitmap);
//    }
//
//    public static Bitmap bytes2Bitmap(byte[] b) {
//        if (b.length != 0) {
//            return BitmapFactory.decodeByteArray(b, 0, b.length);
//        }
//
//        return null;
//    }
//
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        @SuppressWarnings("deprecation")
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }

    @Override
    public int getItemCount() {
        return Courses.size();
    }

    class MyCoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView courseName, courseID;
        LinearLayout courseContainer;
        CardView cvCourse;
        public MyCoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCourse = itemView.findViewById(R.id.cvCourse);
            courseName = itemView.findViewById(R.id.tvCourseName);
            courseID = itemView.findViewById(R.id.tvCourseId);
            courseContainer = itemView.findViewById(R.id.llCourseContainer);
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
