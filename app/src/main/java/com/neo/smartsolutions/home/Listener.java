package com.neo.smartsolutions.home;

public interface Listener {
    void onTabModeSelected(int mode);
    void onBackPressedFromAddLocationFragment();
    void onSubmitButtonPressed(String name, String city, String street, int number);
}