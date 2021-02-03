package com.neo.smartsolutions.welcome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.WelcomeActivity;

public class WelcomeFragment extends Fragment {

    private OnPressedListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.w_fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button buttonSignUp = (Button) view.findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(signUpOnClick);

        TextView buttonLogIn = (TextView) view.findViewById(R.id.buttonLogIn);
        buttonLogIn.setOnClickListener(logInOnClick);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnPressedListener) {
            this.listener = (OnPressedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnPressListener");
        }
    }

    //listeners

    private final View.OnClickListener signUpOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onModeItemSelected(WelcomeActivity.SIGN_UP_MODE_CODE);
        }
    };

    private final View.OnClickListener logInOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onModeItemSelected(WelcomeActivity.LOG_IN_MODE_CODE);
        }
    };
}