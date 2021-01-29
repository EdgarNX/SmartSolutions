package com.neo.smartsolutions.welcome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCountry;
    private ImageButton buttonBack;
    private Button signUpButton;
    private EditText inputEmail;
    private EditText inputPassword;
    private CheckBox checkBoxIAccept;
    private ImageButton imageVisible;
    private ImageButton imageNotVisible;

    private TextView errorText;

    private String email;
    private String password;
    private String country = "nothing";
    private Boolean countrySpinnerWasTouched = false;
    private Boolean termsAndConditionsChecked = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sing_up, container, false);

    }

    private OnBackPressedListener backPressedListener;
    private OnSignUpButtonPressedListener onSignUpButtonPressedListener;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        spinnerCountry = view.findViewById(R.id.country_spinner);
        spinnerCountry.setAdapter(getSpinnerAdapter());
        spinnerCountry.setOnItemSelectedListener(this);
        spinnerCountry.setOnTouchListener(spinnerOnTouch);

        imageVisible = view.findViewById(R.id.imageVisible);
        imageVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "down", Toast.LENGTH_LONG).show();
                inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imageVisible.setVisibility(View.GONE);
                imageNotVisible.setVisibility(View.VISIBLE);
            }
        });

        imageNotVisible = view.findViewById(R.id.imageNotVisible);
        imageNotVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "down", Toast.LENGTH_LONG).show();
                inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                imageVisible.setVisibility(View.VISIBLE);
                imageNotVisible.setVisibility(View.GONE);
            }
        });

        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        checkBoxIAccept = view.findViewById(R.id.checkBoxIAccept);
        checkBoxIAccept.setOnTouchListener(checkBoxOnTouch);

        buttonBack = view.findViewById(R.id.backImageButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPressedListener.onBackPressed();
            }
        });

        signUpButton = view.findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                termsAndConditionsChecked = checkBoxIAccept.isChecked();

                checkSignUpFields();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnBackPressedListener) {      // context instanceof YourActivity
            this.backPressedListener = (OnBackPressedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement WelcomeFragment.OnModeItemSelectedListener");
        }
        //todo here the sign up button listener
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
        return new ArrayAdapter<>(getActivity(), R.layout.spinner_text, getSpinnerCountryDataSource());
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
        if (isValidEmail()) {
            if (isValidPassword()) {
                if (countrySpinnerWasTouched) {
                    if (termsAndConditionsChecked) {
                        Toast.makeText(getActivity(), "It's ok!", Toast.LENGTH_LONG).show();
                        //onSignUpButtonPressedListener.onSignUpButtonPressed(email, password, country);
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

    public void setSpinnerText(String text) {
        errorText = (TextView) spinnerCountry.getSelectedView();
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

    //validation

    public boolean isValidEmail() {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isValidPassword() {
        Pattern PASSWORD_PATTERN
                = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches();
    }

    //listeners

    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                countrySpinnerWasTouched = true;
                setSpinnerText(country);
            }
            return false;
        }
    };

    private View.OnTouchListener checkBoxOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                checkBoxIAccept.setError(null);

            }
            return false;
        }
    };

    //interfaces

    public interface OnSignUpButtonPressedListener {
        void onSignUpButtonPressed(String email, String password, String country);
    }

}