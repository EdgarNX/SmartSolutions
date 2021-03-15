package com.neo.smartsolutions.devices;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.devices.device_local_db.ClickListener;
import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.devices.device_local_db.DeviceListAdapter;
import com.neo.smartsolutions.devices.device_local_db.DeviceViewModel;
import com.neo.smartsolutions.home.Listener;
import com.neo.smartsolutions.services.Weather;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView degreesTextView = view.findViewById(R.id.degreesTextView);
        TextView windTextView = view.findViewById(R.id.windTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);

        degreesTextView.setText(Weather.getWeatherCelsius() + "°C");
        windTextView.setText("Wind speed: " + "\n" + Weather.getWeatherWindSpeed() + "km/h");
        descriptionTextView.setText("Weather: " + "\n" + Weather.getWeatherDescription());

        Button backToLocationsButton = view.findViewById(R.id.buttonBack);
        backToLocationsButton.setOnClickListener(backToLocationsOnClick);

        RecyclerView recyclerViewDevices = view.findViewById(R.id.recyclerViewDevices);
        final DeviceListAdapter adapter = new DeviceListAdapter(getActivity());
        recyclerViewDevices.setAdapter(adapter);
        recyclerViewDevices.setLayoutManager(new LinearLayoutManager(getActivity()));

        DeviceViewModel mDeviceViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);

        mDeviceViewModel.getAllDevices().observe(getActivity(), new Observer<List<Device>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable final List<Device> devices) {

                List<Device> correctLocationList = new ArrayList<>();

                for (Device temp : devices) {
                    if (temp.getLocation().equals(HomeActivity.CURRENT_LOCATION_FOR_DATABASE)) {
                        correctLocationList.add(temp);
                    }
                }

                adapter.setDevices(correctLocationList);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage(getString(R.string.device_delete_question));
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                int position = viewHolder.getAdapterPosition();
                                Device myDevice = adapter.getDeviceAtPosition(position);
                               // Toast.makeText(getActivity(), getString(R.string.delete_word_preamble) + " " + myDevice.getName(), Toast.LENGTH_LONG).show();

                                mDeviceViewModel.deleteDevice(myDevice);

                                listener.onDeleteDeviceButtonPressed(myDevice);

                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                dialog.cancel();
                            }
                        });
                        builder.create()
                                .show();
                    }

                });

        helper.attachToRecyclerView(recyclerViewDevices);

        adapter.setOnItemClickListener(new ClickListener<Device>() {
            @Override
            public void onItemClick(Device data) {
                listener.onDeviceSelected(data.getName(), data.getType(), data.getStatus());
            }

            @Override
            public void onItemLongClick(Device data) {
                listener.onDeviceSelectedToEdit(data);
            }
        });
    }

    //listeners

    private final View.OnClickListener backToLocationsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToLocationFragment();
        }
    };
}