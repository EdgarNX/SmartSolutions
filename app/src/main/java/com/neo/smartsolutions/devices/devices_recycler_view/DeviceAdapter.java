package com.neo.smartsolutions.devices.devices_recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private List<Device> devices;
    private Context context;
    private ClickListener<Device> clickListener;

    public DeviceAdapter(Context context, List<Device> devices) {
        this.devices = devices;
        this.context = context;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.h_dev_device_item, parent, false);
        return new DeviceViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device currentDevice = devices.get(position);
        holder.getTextViewName().setText(currentDevice.getDeviceName());

        if ("relay".equals(currentDevice.getType())) {
            if ("on".equals(currentDevice.getStatus())) {
                holder.getImageViewImage().setBackground(ContextCompat.getDrawable(context, R.drawable.ic_power_on));
            } else {
                holder.getImageViewImage().setBackground(ContextCompat.getDrawable(context, R.drawable.ic_power_of));
            }
        } else {
            //intensity
            holder.getImageViewImage().setVisibility(View.GONE);
            holder.getTextViewInformation().setText(currentDevice.getStatus() + "%");
        }

        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(currentDevice);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void setOnItemClickListener(ClickListener<Device> deviceClickListener) {
        this.clickListener = deviceClickListener;
    }
}
