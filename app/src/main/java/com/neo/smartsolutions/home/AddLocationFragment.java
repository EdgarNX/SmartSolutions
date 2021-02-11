package com.neo.smartsolutions.home;

import android.content.Context;
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

public class AddLocationFragment extends Fragment {

    private Listener listener;

    ImageButton buttonBack;
    EditText inputLocationName;
    EditText inputCity;
    EditText inputStreet;
    EditText inputNumber;

    String name;
    String city;
    String street;
    int number=-1;

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

    private void checkLocationFields() {
        if (!name.isEmpty() && name.length()>0) {
            if (!city.isEmpty() && city.length()>0) {
                if (!street.isEmpty() && street.length()>0) {
                    if (number != -1) {
                        listener.onSubmitButtonPressed(name, city, street, number);
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

    private View.OnClickListener submitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            name = inputLocationName.getText().toString();
            city = inputCity.getText().toString();
            street = inputStreet.getText().toString();
            if(!inputNumber.getText().toString().isEmpty()) {
                number = Integer.parseInt(inputNumber.getText().toString());
            } else {
                number = -1;
            }

            checkLocationFields();
        }
    };

    private final View.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedFromAddLocationFragment();
        }
    };
}