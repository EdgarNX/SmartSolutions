package com.neo.smartsolutions.devices.device_local_db;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.colorpicker.util.ColorUtil;
import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.devices.device_types.IntensityFragment;
import com.neo.smartsolutions.devices.device_types.RelayFragment;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder> {

    class DeviceViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName, textViewInformation;
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

        public TextView getTextViewInformation() {
            return textViewInformation;
        }

        public ImageView getImageViewImage() {
            return image;
        }

        public CardView getCardView() {
            return cardView;
        }
    }

    private List<Device> mDevices;
    private Context context;
    private ClickListener<Device> clickListener;

    private final LayoutInflater mInflater;

    public DeviceListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.h_dev_device_item, parent, false);
        return new DeviceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device currentDevice = mDevices.get(position);
        holder.getTextViewName().setText(currentDevice.getName());

        if ("Relay".equals(currentDevice.getType())) {
            if ("on".equals(currentDevice.getStatus())) {
                holder.getImageViewImage().setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.ic_power_on));
            } else {
                holder.getImageViewImage().setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.ic_power_of));
            }
        }  else if ("Locker".equals(currentDevice.getType())){
            if ("open".equals(currentDevice.getStatus())) {
                holder.getImageViewImage().setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.ic_opened_padlock));
            } else {
                holder.getImageViewImage().setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.ic_closed_padlock));
            }
        } else if ("Progress".equals(currentDevice.getType())){
            holder.getImageViewImage().setVisibility(View.GONE);
            holder.getTextViewInformation().setText(currentDevice.getStatus() + "%");
        } else {
            holder.getImageViewImage().setVisibility(View.GONE);
            holder.getTextViewInformation().setBackgroundColor(Color.parseColor(currentDevice.getStatus()));
        }


        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(currentDevice);
            }
        });

        holder.getCardView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onItemLongClick(currentDevice);
                return false;
            }
        });
    }

    public Device getDeviceAtPosition (int position) {
        return mDevices.get(position);
    }

    public void setDevices(List<Device> devices) {
        mDevices = devices;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDevices != null)
            return mDevices.size();
        else return 0;
    }

    public void setOnItemClickListener(ClickListener<Device> deviceClickListener) {
        this.clickListener = deviceClickListener;
    }

}