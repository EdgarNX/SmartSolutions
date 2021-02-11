package com.neo.smartsolutions.location_recycler_view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.R;

public class LocationViewHolder extends RecyclerView.ViewHolder{

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
