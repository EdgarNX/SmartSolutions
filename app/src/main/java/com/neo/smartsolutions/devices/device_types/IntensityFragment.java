package com.neo.smartsolutions.devices.device_types;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.home.Listener;
import com.xw.repo.BubbleSeekBar;

import java.util.Locale;

public class IntensityFragment extends Fragment {

    private Listener listener;

    Button buttonBack;
    TextView intensityTextView;
    BubbleSeekBar intensitySeekBar;
    Button buttonSubmitIntensity;

    String intensity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_dev_fragment_intensity, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonBack = view.findViewById(R.id.backImageButton);
        buttonBack.setOnClickListener(backOnClick);

        intensityTextView = view.findViewById(R.id.intensityTextView);
        setAndStoreProgress(Integer.parseInt(HomeActivity.DEVICE_STATUS));

        buttonSubmitIntensity = view.findViewById(R.id.buttonSubmitIntensity);
        buttonSubmitIntensity.setOnClickListener(submitOnClick);

        intensitySeekBar = view.findViewById(R.id.intensitySeekBar);
        intensitySeekBar.setProgress(Integer.parseInt(HomeActivity.DEVICE_STATUS));
        intensitySeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                setAndStoreProgress(progress);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                setAndStoreProgress(progress);
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                setAndStoreProgress(progress);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    void setAndStoreProgress(int progress) {
        intensityTextView.setText(Integer.toString(progress));
        intensity = Integer.toString(progress);
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

    //methods

    //listeners

    private final View.OnClickListener submitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onIntensitySubmitButtonPressed(intensity);
        }
    };

    private final View.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToDeviceFragmentFromDevices();
        }
    };
}