package com.neo.smartsolutions.devices.devices_recycler_view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.R;

public class DeviceViewHolder extends RecyclerView.ViewHolder{

    private final TextView textViewName,textViewInformation;
    private ImageView image;
    private CardView cardView;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.textViewTitle);
        textViewInformation = itemView.findViewById(R.id.textViewInformation);
        image = itemView.findViewById(R.id.image);
        cardView = itemView.findViewById(R.id.cardView);
    }

    public TextView getTextViewName() {
        return textViewName;
    }

    public TextView getTextViewInformation() { return textViewInformation; }

    public ImageView getImageViewImage() {
        return image;
    }

    public CardView getCardView() {
        return cardView;
    }
}
