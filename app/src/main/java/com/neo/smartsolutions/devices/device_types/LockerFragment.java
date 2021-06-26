package com.neo.smartsolutions.devices.device_types;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.home.Listener;

public class LockerFragment extends Fragment {

    private Listener listener;

    Button buttonBack;
    ImageView imageOpenClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_dev_fragment_locker, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonBack = view.findViewById(R.id.backImageButton);
        buttonBack.setOnClickListener(backOnClick);

        imageOpenClose = view.findViewById(R.id.imageOpenClose);
        imageOpenClose.setOnClickListener(openCloseButtonOnClick);
        setImageOpenClose();
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

    private void setImageOpenClose() {
        if ("open".equals(HomeActivity.DEVICE_STATUS)) {
            imageOpenClose.setImageResource(R.drawable.ic_opened_padlock);
        } else {
            imageOpenClose.setImageResource(R.drawable.ic_closed_padlock);
        }
    }

    //listeners

    private final View.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToDeviceFragmentFromDevices();
        }
    };

    private final View.OnClickListener openCloseButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if ("open".equals(HomeActivity.DEVICE_STATUS)) {
                listener.openCloseButtonPressedInLockerFragment("close");
            } else {
                listener.openCloseButtonPressedInLockerFragment("open");
            }

            setImageOpenClose();
        }
    };
}