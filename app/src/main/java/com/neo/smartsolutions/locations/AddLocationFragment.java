package com.neo.smartsolutions.locations;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.home.Listener;

public class AddLocationFragment extends Fragment {

    private Listener listener;

    Button buttonBack;
    EditText inputLocationName;
    EditText inputCity;
    EditText inputStreet;
    EditText inputNumber;

    String name;
    String city;
    String street;
    int number = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_loc_fragment_add_location, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        inputLocationName = view.findViewById(R.id.inputDeviceName);
        inputCity = view.findViewById(R.id.inputCity);
        inputStreet = view.findViewById(R.id.inputStreet);
        inputNumber = view.findViewById(R.id.inputNumber);

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
                    + " must implement OnPressListener");
        }
    }

    //methods

    private void extractLocationDates() {
        name = inputLocationName.getText().toString();
        city = inputCity.getText().toString();
        street = inputStreet.getText().toString();
        if (!inputNumber.getText().toString().isEmpty()) {
            number = Integer.parseInt(inputNumber.getText().toString());
        } else {
            number = -1;
        }
    }

    private void checkLocationFields() {
        if (!name.isEmpty() && name.length() > 0) {
            if (!city.isEmpty() && city.length() > 0) {
                if (!street.isEmpty() && street.length() > 0) {
                    if (number != -1) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage(getString(R.string.add_location_double_check));
                        builder.setPositiveButton("It is checked", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                listener.onSubmitButtonPressedFromAddLocation(name, city, street, number);
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("Let me check", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                dialog.cancel();
                            }
                        });
                        builder.create()
                                .show();

                    } else {
                        inputNumber.setError("Please complete the number field.");
                    }
                } else {
                    inputStreet.setError("Please complete the street field.");
                }
            } else {
                inputCity.setError("Please complete the city field.");
            }
        } else {
            inputLocationName.setError("Please complete the name field.");
        }
    }

    //listeners

    private final View.OnClickListener submitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            extractLocationDates();
            checkLocationFields();
        }
    };

    private final View.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToLocationFragment();
        }
    };
}