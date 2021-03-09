package com.neo.smartsolutions.services.storage;


import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.devices.device_local_db.DeviceViewModel;
import com.neo.smartsolutions.locations.location_local_db.Location;
import com.neo.smartsolutions.locations.location_local_db.LocationViewModel;

public class LocalStorage {

    LocationViewModel locationViewModel;
    DeviceViewModel deviceViewModel;

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
        Device device = new Device(name, location, description, type, status, code);
        deviceViewModel.insert(device);
    }

}
