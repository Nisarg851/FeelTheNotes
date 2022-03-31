package com.example.feelthenote.RecyclerViewAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.feelthenote.Model.Advertise;
import com.example.feelthenote.R;

import java.util.List;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.ViewHolder> {

    private ViewGroup parent;

    List<Advertise> advertiseList;

    public AdvertisementAdapter(List<Advertise> advertiseList) {
        Log.e("adImage", "ad Adv Adapter");
        this.advertiseList = advertiseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("adImage", "ad");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertisement_item_layout, parent, false);
        this.parent = parent;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("adImage", "ad onBind");
        String imageURL = "http://ftn.locuslogs.com/images/advertisement/ad" + advertiseList.get(position).getAdvertiseImage().replace(':', '_') + ".jpg";
        Log.e("adImage", "Ad Card Image: " + imageURL);
        Glide.with(parent.getContext())
                .load(imageURL)
                .placeholder(R.drawable.default_user_image)
                .into(holder.vAdvertisementCardImage);
    }

    @Override
    public int getItemCount() {
        return advertiseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView vAdvertisementCardImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.e("adImage", "ad ViewHolder");
            vAdvertisementCardImage = itemView.findViewById(R.id.vAdvertisementCardImage);
        }
    }
}
