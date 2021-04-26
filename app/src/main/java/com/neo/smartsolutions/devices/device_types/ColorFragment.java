package com.neo.smartsolutions.devices.device_types;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.ColorPickerView;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.listener.DismissListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.util.ColorUtil;
import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.home.Listener;
import com.xw.repo.BubbleSeekBar;

import org.jetbrains.annotations.NotNull;

public class ColorFragment extends Fragment {

    private Listener listener;

    Button buttonBack;
    Button buttonColorPicker;
    ColorPickerView colorPickerView;

    String color;
    private int mColor = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_dev_fragment_color, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonBack = view.findViewById(R.id.backImageButton);
        buttonBack.setOnClickListener(backOnClick);

        colorPickerView = view.findViewById(R.id.colorPickerView);

        buttonColorPicker = view.findViewById(R.id.colorPickerButton);
        buttonColorPicker.setOnClickListener(openColorPickerOnClick);
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

    private final View.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onBackPressedToDeviceFragmentFromDevices();
        }
    };

    private final View.OnClickListener openColorPickerOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new ColorPickerDialog.Builder(requireActivity())
                    .setColorShape(ColorShape.SQAURE)
                    .setDefaultColor(mColor)
                    .setColorListener(new ColorListener() {
                        @Override
                        public void onColorSelected(int color, @NotNull String colorHex) {
                            mColor = color;
                            colorPickerView.setColor(color);
                            listener.onColorSubmitButtonPressed(ColorUtil.formatColor(mColor));
                        }
                    })
                    .setDismissListener(new DismissListener() {
                        @Override
                        public void onDismiss() {
                            Log.d("ColorPickerDialog", "Handle dismiss event");
                        }
                    })
                    .show();
        }
    };
}