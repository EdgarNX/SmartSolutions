package com.neo.smartsolutions.devices;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.devices.devices_recycler_view.ClickListener;
import com.neo.smartsolutions.devices.devices_recycler_view.Device;
import com.neo.smartsolutions.devices.devices_recycler_view.DeviceAdapter;
import com.neo.smartsolutions.home.Listener;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends Fragment {

    private Listener listener;
    private List<Device> devices;
    private RecyclerView recyclerViewDevices;
    private DeviceAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_dev_fragment_devices, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            this.listener = (Listener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerViewDevices = view.findViewById(R.id.recyclerViewDevices);
        displayDevicesList();

        Button backToLocationsButton = view.findViewById(R.id.buttonBack);
        backToLocationsButton.setOnClickListener(backToLocationsOnClick);
    }

    // RecyclerView

    private void fillWithDevices() {
        devices = new ArrayList<>();
        Device devices;
        devices = new Device(0, "Flash", "this is something", "on", "relay", "1dF34&5#h");
        this.devices.add(devices);
        devices = new Device(0, "Flash Intensity", "this is something", "50", "intensity", "1dF34&5#h");
        this.devices.add(devices);
        devices = new Device(0, "Door lock", "this is something", "of", "relay", "1dF34&5#h");
        this.devices.add(devices);
    }

    private void setDevicesLayoutManager() {
        recyclerViewDevices.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setDevicesAdapter() {
        recyclerViewAdapter = new DeviceAdapter(getActivity(), devices);

        recyclerViewAdapter.setOnItemClickListener(new ClickListener<Device>() {
            @Override
            public void onItemClick(Device data) {
                //todo from here we will fly to use the device activity
                //Toast.makeText(getActivity(), data.getDeviceName(), Toast.LENGTH_SHORT).show();
                listener.onDeviceSelected(data.getDeviceName(), data.getType(), data.getStatus());
            }
        });

        recyclerViewDevices.setAdapter(recyclerViewAdapter);
    }

    private void displayDevicesList() {
        fillWithDevices();

        setDevicesLayoutManager();

        setDevicesAdapter();
    }

    //methods

    private void addDevice(String name, String status, String type, String code) {
        //todo add here the new device to the database/firebase
    }

    //listeners

    private View.OnClickListener backToLocationsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToLocationFragment();
        }
    };
}