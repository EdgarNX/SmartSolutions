package com.neo.smartsolutions.home;

public interface Listener {
    void onTabModeSelected(int mode);

    void onLocationSelected(String locationName);
    void onBackPressedToLocationFragment();
    void onSubmitButtonPressedFromLocation(String name, String city, String street, int number);

    void onDeviceSelected(String deviceName, String deviceType, String Status);
    void onBackPressedToDeviceFragment();
    void onSubmitButtonPressedFromDevice(String name, String type, String code);

    void  onOnOffButtonPressedInRelayFragment(String status);
    void onIntensitySubmitButtonPressed(String status);
}