package com.neo.smartsolutions.devices;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.home.Listener;


public class UpdateDevices extends Fragment {

    private Listener listener;

    Button buttonBack;
    EditText textDeviceName;

    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_dev_fragment_edit_devices, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textDeviceName = view.findViewById(R.id.inputDeviceName);
        textDeviceName.setHint(HomeActivity.CURRENT_DEVICE_FOR_DATABASE);

        buttonBack = view.findViewById(R.id.backImageButton);
        buttonBack.setOnClickListener(backOnClick);

        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(submitOnClick);
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

    //methods

    private void extractDeviceDates() {
        name = textDeviceName.getText().toString();
    }

    private void checkLocationFields() {
        if (!name.isEmpty() && !HomeActivity.CURRENT_DEVICE_FOR_DATABASE.equals(name)) {
            listener.onSubmitButtonPressedFromUpdateDevice(name);
        } else {
            textDeviceName.setError("Please complete the name field with a new name.");
        }
    }

    //listeners

    private final View.OnClickListener submitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            extractDeviceDates();
            checkLocationFields();
        }
    };

    private final View.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToDeviceFragment();
        }
    };

}
