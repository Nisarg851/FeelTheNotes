package com.example.feelthenote.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feelthenote.Model.CourseLatestPackages;
import com.example.feelthenote.Model.LatestCoursePackageItem;
import com.example.feelthenote.R;

import java.util.List;

public class LatestPackageAdapter extends RecyclerView.Adapter<LatestPackageAdapter.ViewHolder> {

    Context context;
    private  List<CourseLatestPackages> packages;
    ExploreCoursesAdapter.OnItemClickListener onItemClickListener;

    public LatestPackageAdapter(Context context,  List<CourseLatestPackages> packages){
        this.context = context;
        this.packages = packages;
    }

    @NonNull
    @Override
    public LatestPackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_package_item_layout, parent, false);
        return new LatestPackageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestPackageAdapter.ViewHolder holder, int position) {
        CourseLatestPackages currentPackage = packages.get(position);
        holder.tvPackageID.setText(currentPackage.getPackageID());
        holder.tvPackageExpiryDate.setText(currentPackage.getExpiryDate().split("T")[0]);
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvPackageID, tvPackageExpiryDate, tvViewPackageDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPackageID = itemView.findViewById(R.id.tvPackageID);
            tvPackageExpiryDate = itemView.findViewById(R.id.tvPackageExpiryDate);
            tvViewPackageDetails = itemView.findViewById(R.id.tvViewPackageDetails);

            tvViewPackageDetails.setOnClickListener(this);
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
    public void SetOnItemClickListener(final ExploreCoursesAdapter.OnItemClickListener itemClickListener)
    {
        this.onItemClickListener = itemClickListener;
    }

}
