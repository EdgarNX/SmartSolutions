package com.neo.smartsolutions.home;

public interface Listener {
    void onTabModeSelected(int mode);
    void onBackPressedToLocationFragment();
    void onSubmitButtonPressedFromLocation(String name, String city, String street, int number);
    void onLocationSelected(String locationName);
    void onDeviceSelected(String deviceName, String deviceType);
    void onSubmitButtonPressedFromDevice(String name, String type, String code);
    void onBackPressedFromAddDeviceFragment();
}