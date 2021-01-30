package com.neo.smartsolutions.welcome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.R;

import java.util.regex.Pattern;

public abstract class WelcomeResources extends Fragment {

    OnPressedListener pressedListener;

    EditText inputEmail;
    EditText inputPassword;
    ImageButton imageVisible;
    ImageButton imageNotVisible;
    ImageButton buttonBack;

    String email;
    String password;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnPressedListener) {
            this.pressedListener = (OnPressedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnPressListener");
        }
    }

    //validation

    public boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isValidPassword(String password) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile("[a-zA-Z0-9!@#$]{8,24}");

        return !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches();
    }
}
