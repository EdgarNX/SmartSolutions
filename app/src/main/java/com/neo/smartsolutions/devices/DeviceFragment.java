package com.neo.smartsolutions.devices;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.devices.device_local_db.ClickListener;
import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.devices.device_local_db.DeviceListAdapter;
import com.neo.smartsolutions.devices.device_local_db.DeviceViewModel;
import com.neo.smartsolutions.home.Listener;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends Fragment {

    private Listener listener;

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
        Button backToLocationsButton = view.findViewById(R.id.buttonBack);
        backToLocationsButton.setOnClickListener(backToLocationsOnClick);

        RecyclerView recyclerViewDevices = view.findViewById(R.id.recyclerViewDevices);
        final DeviceListAdapter adapter = new DeviceListAdapter(getActivity());
        recyclerViewDevices.setAdapter(adapter);
        recyclerViewDevices.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get a new or existing ViewModel from the ViewModelProvider.
        DeviceViewModel mDeviceViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mDeviceViewModel.getAllDevices().observe(getActivity(), new Observer<List<Device>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable final List<Device> devices) {
                // Update the cached copy of the words in the adapter.

                List<Device> correctLocationList = new ArrayList<>();

                for (Device temp : devices) {
                    if (temp.getLocation().equals(HomeActivity.CURRENTLOCATIONFORDATABASE)) {
                        correctLocationList.add(temp);
                    }
                }

                adapter.setDevices(correctLocationList);
            }
        });

        adapter.setOnItemClickListener(new ClickListener<Device>() {
            @Override
            public void onItemClick(Device data) {
                listener.onDeviceSelected(data.getName(), data.getType(), data.getStatus());
            }
        });
    }

    //listeners

    private View.OnClickListener backToLocationsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToLocationFragment();
        }
    };
}