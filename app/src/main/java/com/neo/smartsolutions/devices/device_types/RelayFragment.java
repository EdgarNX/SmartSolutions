package com.neo.smartsolutions.devices.device_types;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.home.Listener;

public class RelayFragment extends Fragment {

    private Listener listener;

    Button buttonBack;
    ImageView imageOnOff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_dev_fragment_relay, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonBack = view.findViewById(R.id.backImageButton);
        buttonBack.setOnClickListener(backOnClick);

        imageOnOff = view.findViewById(R.id.imageOnOff);
        imageOnOff.setOnClickListener(onOffButtonOnClick);
        setImageOnOff();
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

    private void setImageOnOff() {
        if ("on".equals(HomeActivity.DEVICE_STATUS)) {
            imageOnOff.setImageResource(R.drawable.ic_power_on);
        } else {
            imageOnOff.setImageResource(R.drawable.ic_power_of);
        }
    }

    //listeners

    private final View.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToDeviceFragmentFromDevices();
        }
    };

    private final View.OnClickListener onOffButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if ("on".equals(HomeActivity.DEVICE_STATUS)) {
                listener.onOnOffButtonPressedInRelayFragment("off");
            } else {
                listener.onOnOffButtonPressedInRelayFragment("on");
            }

            setImageOnOff();
        }
    };
}