package com.neo.smartsolutions.services.storage;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.devices.device_local_db.DeviceDao;
import com.neo.smartsolutions.devices.device_local_db.DeviceViewModel;
import com.neo.smartsolutions.locations.location_local_db.Location;
import com.neo.smartsolutions.locations.location_local_db.LocationDao;
import com.neo.smartsolutions.locations.location_local_db.LocationViewModel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class LocalStorage {

    public LocationViewModel locationViewModel;
    public DeviceViewModel deviceViewModel;

    public LocalStorage(LocationViewModel mLocationViewModel, DeviceViewModel mDeviceViewModel) {
        this.locationViewModel = mLocationViewModel;
        this.deviceViewModel = mDeviceViewModel;
    }

    public void deleteAllLocation() {
        locationViewModel.deleteAll();
    }

    public void deleteAllDevices() {
        deviceViewModel.deleteAll();
    }

    public void addLocationInLocalDb(String name, String city, String street, String number) {
        Location location = new Location(name, city, street, number);
        locationViewModel.insert(location);
    }

    public void addDeviceInLocalDb(String name, String location, String description, String type, String status, String code) {
        Device device = new Device(name, location, description, status, type, code);
        deviceViewModel.insert(device);
    }

    public void updateDeviceName(Device device, String name) {
        addDeviceInLocalDb(name, device.getLocation(), device.getDescription(), device.getStatus(), device.getType(), device.getCode());
        deviceViewModel.deleteDevice(device);
    }

    public void deleteLocationAndDevices(Location location) {
        List<Device> devices = deviceViewModel.getAllWithLocationName(location.getName());
        for (Device device : devices) {
            deviceViewModel.deleteDevice(device);
        }
        locationViewModel.deleteLocation(location);
    }

    public void updateDevice(Device device) {
        deviceViewModel.update(device);
    }

    public boolean getDeviceIfExists(String deviceName) {
        boolean result = false;
        Device device = deviceViewModel.getWithDeviceName(deviceName).getValue();
        result = device != null;
        return result;
    }


}
