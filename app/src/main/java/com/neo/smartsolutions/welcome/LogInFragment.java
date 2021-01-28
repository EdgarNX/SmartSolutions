package com.neo.smartsolutions.welcome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    private ImageButton buttonBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    private OnBackPressedListener backPressedListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonBack = (ImageButton) view.findViewById(R.id.backImageButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPressedListener.onBackPressed();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBackPressedListener) {      // context instanceof YourActivity
            this.backPressedListener = (OnBackPressedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement WelcomeFragment.OnModeItemSelectedListener");
        }
    }

}