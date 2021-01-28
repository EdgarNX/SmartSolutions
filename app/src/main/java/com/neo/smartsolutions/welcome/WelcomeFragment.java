package com.neo.smartsolutions.welcome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.WelcomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    private Button buttonSignUp;
    private TextView buttonLogIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    private OnModeItemSelectedListener listener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        buttonSignUp = (Button) view.findViewById(R.id.buttonSignUp);
        buttonLogIn = (TextView) view.findViewById(R.id.buttonLogIn);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onModeItemSelected(WelcomeActivity.SIGN_UP_MODE_CODE);
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onModeItemSelected(WelcomeActivity.LOG_IN_MODE_CODE);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnModeItemSelectedListener) {      // context instanceof YourActivity
            this.listener = (OnModeItemSelectedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement WelcomeFragment.OnModeItemSelectedListener");
        }
    }

    //interfaces

    public interface OnModeItemSelectedListener {
        void onModeItemSelected(int mode);
    }

}