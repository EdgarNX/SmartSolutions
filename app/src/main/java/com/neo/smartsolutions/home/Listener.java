package com.neo.smartsolutions.home;

import com.neo.smartsolutions.devices.device_local_db.Device;

public interface Listener {
    void onTabModeSelected(int mode);

    void onLocationSelected(String locationName, String city);
    void onBackPressedToLocationFragment();
    void onDeleteLocationButtonPressed();
    void onSubmitButtonPressedFromAddLocation(String name, String city, String street, int number);

    void onDeviceSelected(String deviceName, String deviceType, String Status);
    void onDeviceSelectedToEdit(Device device);
    void onBackPressedToDeviceFragment();
    void onSubmitButtonPressedFromAddDevice(String name, String description, String type, String status, String code);
    void onSubmitButtonPressedFromUpdateDevice(String name);


    void  onOnOffButtonPressedInRelayFragment(String status);
    void onIntensitySubmitButtonPressed(String status);
    void onDeleteDeviceButtonPressed(Device device);
}