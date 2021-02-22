package com.neo.smartsolutions.locations.location_local_db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.R;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationViewHolder> {

    class LocationViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle, textViewLocation;
        private ImageView image;
        private CardView cardView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewLocation() {
            return textViewLocation;
        }

        public ImageView getImageViewImage() {
            return image;
        }

        public CardView getCardView() {
            return cardView;
        }
    }

    private List<Location> mLocations;
    private Context context;
    private ClickListener<Location> clickListener;

    private final LayoutInflater mInflater;

    public LocationListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.h_loc_location_item, parent, false);
        return new LocationViewHolder(itemView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location currentLocation = mLocations.get(position);
        holder.getTextViewTitle().setText(currentLocation.getName());
        holder.getTextViewLocation().setText(currentLocation.getLocation());
        holder.getImageViewImage().setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.smart_logo));
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(currentLocation);
            }
        });
    }

    public void setLocations(List<Location> locations) {
        mLocations = locations;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mLocations != null)
            return mLocations.size();
        else return 0;
    }

    public void setOnItemClickListener(ClickListener<Location> locationClickListener) {
        this.clickListener = locationClickListener;
    }
}