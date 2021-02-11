package com.neo.smartsolutions.location_recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.neo.smartsolutions.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private List<Location> locations;
    private Context context;
    private ClickListener<Location> clickListener;


    public LocationAdapter(Context context, List<Location> locations) {
        this.locations = locations;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.h_loc_location_item, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location currentLocation = locations.get(position);
        holder.getTextViewTitle().setText(currentLocation.getLocationName());
        holder.getTextViewLocation().setText(currentLocation.getLocation());
        holder.getImageViewImage().setBackground(ContextCompat.getDrawable(context, R.drawable.smart_logo));
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(currentLocation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setOnItemClickListener(ClickListener<Location> locationClickListener) {
        this.clickListener = locationClickListener;
    }
}
