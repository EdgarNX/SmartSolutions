package com.neo.smartsolutions.welcome;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.R;

import java.util.regex.Pattern;

public class LogInFragment extends WelcomeResources {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);

        imageVisible = view.findViewById(R.id.imageVisible);
        imageVisible.setOnClickListener(openEyeOnClick);

        imageNotVisible = view.findViewById(R.id.imageNotVisible);
        imageNotVisible.setOnClickListener(closedEyeOnClick);

        Button logInButton = view.findViewById(R.id.buttonLogIn);
        logInButton.setOnClickListener(logInOnClick);

        buttonBack = (ImageButton) view.findViewById(R.id.backImageButton);
        buttonBack.setOnClickListener(backOnClick);
    }

    //methods

    private void checkLogInFields() {
        if (isValidEmail(email)) {
            if (isValidPassword(password)) {
                pressedListener.onLogInButtonPressed(email, password);
            } else {
                inputPassword.setError("The password format is wrong.");
            }
        } else {
            inputEmail.setError("The email format is wrong.");
        }
    }

    //listeners

    private View.OnClickListener logInOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();

            checkLogInFields();
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