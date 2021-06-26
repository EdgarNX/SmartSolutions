package com.neo.smartsolutions.devices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.WelcomeActivity;
import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.home.Listener;

import java.util.ArrayList;
import java.util.List;

public class AddDeviceFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Listener listener;

    Button buttonBack;
    EditText textDeviceName;
    EditText inputCode;
    Spinner spinnerDevice;

    String name;
    String type;
    String code;

    private Boolean deviceSpinnerWasTouched = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_dev_fragment_add_device, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textDeviceName = view.findViewById(R.id.inputDeviceName);
        inputCode = view.findViewById(R.id.inputCode);

        spinnerDevice = view.findViewById(R.id.device_spinner);
        spinnerDevice.setAdapter(getSpinnerAdapter());
        spinnerDevice.setOnItemSelectedListener(this);
        spinnerDevice.setOnTouchListener(spinnerOnTouch);

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

    //spinner

    private List<String> getSpinnerDeviceType() {
        List<String> devices = new ArrayList<>();
        devices.add("Relay");
        devices.add("Locker");
        devices.add("Progress");
        devices.add("Color");
        return devices;
    }

    private ArrayAdapter<String> getSpinnerAdapter() {
        return new ArrayAdapter<>(getActivity(), R.layout.w_spinner_text, getSpinnerDeviceType());
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        type = arg0.getItemAtPosition(position).toString();

        TextView deviceText = (TextView) spinnerDevice.getSelectedView();
        deviceText.setText(type);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //methods

    private void extractDeviceDates() {
        name = textDeviceName.getText().toString();
        code = inputCode.getText().toString();
    }

    private void checkLocationFields() {
        if (!name.isEmpty()) {
            if (deviceSpinnerWasTouched) {
                if (!code.isEmpty()) {
                    if ("Color".equals(type)) {
                        listener.onSubmitButtonPressedFromAddDevice(name,"description", "#FFFFFF", type, code);
                    } else {
                        listener.onSubmitButtonPressedFromAddDevice(name,"description", "0", type, code);
                    }

                } else {
                    inputCode.setError("Please complete the code field.");
                }
            } else {
                setSpinnerText("error");
            }
        } else {
            textDeviceName.setError("Please complete the name field.");
        }
    }

    @SuppressLint("SetTextI18n")
    public void setSpinnerText(String text) {
        TextView errorText = (TextView) spinnerDevice.getSelectedView();
        if ("error".equals(text)) {
            errorText.setError("this is just for the icon");
            errorText.setTextColor(Color.RED);
            errorText.setText("Please choose a device.");
        } else {
            errorText.setError(null);
            errorText.setTextColor(Color.BLACK);
            errorText.setText(text);
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

    private final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                deviceSpinnerWasTouched = true;
                setSpinnerText(type);
            }
            return false;
        }
    };
}