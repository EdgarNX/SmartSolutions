package com.neo.smartsolutions.home;

import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.locations.location_local_db.Location;

import java.util.concurrent.ExecutionException;

public interface Listener {
    void onTabModeSelected(int mode);

    void onLocationSelected(String locationName, String city);
    void onBackPressedToLocationFragment();
    void onDeleteLocationButtonPressed(Location myLocation) throws ExecutionException, InterruptedException;
    void onSubmitButtonPressedFromAddLocation(String name, String city, String street, int number);

    void onDeviceSelected(Device device);
    void onDeviceSelectedToEdit(Device device);
    void onBackPressedToDeviceFragment();
    void onBackPressedToDeviceFragmentFromDevices();
    void onSubmitButtonPressedFromAddDevice(String name, String description, String type, String status, String code);
    void onSubmitButtonPressedFromUpdateDevice(String name);
    void onDeleteDeviceButtonPressed(Device device);

    void onOnOffButtonPressedInRelayFragment(String status);
    void openCloseButtonPressedInLockerFragment(String status);
    void onIntensitySubmitButtonPressed(String status);
    void onColorSubmitButtonPressed(String status);
}