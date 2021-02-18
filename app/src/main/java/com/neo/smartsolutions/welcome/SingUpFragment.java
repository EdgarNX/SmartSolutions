package com.neo.smartsolutions.welcome;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.neo.smartsolutions.R;

import java.util.ArrayList;
import java.util.List;

public class SingUpFragment extends WelcomeResources implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCountry;
    private CheckBox checkBoxIAccept;

    private String country = "nothing";
    private Boolean countrySpinnerWasTouched = false;
    private Boolean termsAndConditionsChecked = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.w_fragment_sing_up, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        spinnerCountry = view.findViewById(R.id.country_spinner);
        spinnerCountry.setAdapter(getSpinnerAdapter());
        spinnerCountry.setOnItemSelectedListener(this);
        spinnerCountry.setOnTouchListener(spinnerOnTouch);

        inputEmail = view.findViewById(R.id.inputDeviceName);
        inputPassword = view.findViewById(R.id.inputPassword);

        imageVisible = view.findViewById(R.id.imageVisible);
        imageVisible.setOnClickListener(openEyeOnClick);

        imageNotVisible = view.findViewById(R.id.imageNotVisible);
        imageNotVisible.setOnClickListener(closedEyeOnClick);

        checkBoxIAccept = view.findViewById(R.id.checkBoxIAccept);
        checkBoxIAccept.setOnTouchListener(checkBoxOnTouch);

        Button signUpButton = view.findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(signUpOnClick);

        buttonBack = view.findViewById(R.id.backImageButton);
        buttonBack.setOnClickListener(backOnClick);
    }

    //spinner

    private List<String> getSpinnerCountryDataSource() {
        List<String> countries = new ArrayList<>();
        countries.add("Romania");
        countries.add("Hungary");
        countries.add("Serbia");
        countries.add("Austria");
        countries.add("Germany");
        return countries;
    }

    private ArrayAdapter<String> getSpinnerAdapter() {
        return new ArrayAdapter<>(getActivity(), R.layout.w_spinner_text, getSpinnerCountryDataSource());
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        country = arg0.getItemAtPosition(position).toString();

        TextView countryText = (TextView) spinnerCountry.getSelectedView();
        countryText.setText(country);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //methods

    private void checkSignUpFields() {
        if (super.isValidEmail(email)) {
            if (isValidPassword(password)) {
                if (countrySpinnerWasTouched) {
                    if (termsAndConditionsChecked) {
                        //Toast.makeText(getActivity(), "It's ok!", Toast.LENGTH_LONG).show();
                        pressedListener.onSignUpButtonPressed(email, password, country);
                    } else {
                        checkBoxIAccept.setError("Please check the Terms and Conditions.");
                        //Toast.makeText(getActivity(), "Please check the Terms and Conditions.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    setSpinnerText("error");
                    //Toast.makeText(getActivity(), "Please choose a country.", Toast.LENGTH_LONG).show();
                }
            } else {
                inputPassword.setError("The password must be minimum 8 characters." + "\n" + "It can contain: Numbers, Lower Case Letters, Upper Case Letters, !, @, #, $");
                //Toast.makeText(getActivity(), "Incorrect password format!", Toast.LENGTH_LONG).show();
            }
        } else {
            inputEmail.setError("The email address is incorrect.");
            //Toast.makeText(getActivity(), "The email address is incorrect.", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public void setSpinnerText(String text) {
        TextView errorText = (TextView) spinnerCountry.getSelectedView();
        if ("error".equals(text)) {
            errorText.setError("this is just for the icon");
            errorText.setTextColor(Color.RED);
            errorText.setText("Please choose a country.");
        } else {
            errorText.setError(null);
            errorText.setTextColor(Color.BLACK);
            errorText.setText(text);
        }
    }

    //listeners

    private View.OnClickListener signUpOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            termsAndConditionsChecked = checkBoxIAccept.isChecked();

            checkSignUpFields();
        }
    };

    private final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                countrySpinnerWasTouched = true;
                setSpinnerText(country);
            }
            return false;
        }
    };

    private final View.OnTouchListener checkBoxOnTouch = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                checkBoxIAccept.setError(null);
            }
            return false;
        }
    };

    private final View.OnClickListener closedEyeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            imageVisible.setVisibility(View.VISIBLE);
            imageNotVisible.setVisibility(View.GONE);
        }
    };

    private final View.OnClickListener openEyeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageVisible.setVisibility(View.GONE);
            imageNotVisible.setVisibility(View.VISIBLE);
        }
    };

    private final View.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pressedListener.onBackPressed();
        }
    };
}