package com.neo.smartsolutions.devices.device_local_db;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neo.smartsolutions.locations.location_local_db.Location;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DeviceViewModel extends AndroidViewModel {

    private DeviceRepository mRepository;
    private LiveData<List<Device>> mAllDevice;

    public DeviceViewModel(Application application) {
        super(application);
        mRepository = new DeviceRepository(application);
        mAllDevice = mRepository.getAll();
    }

    public LiveData<List<Device>> getAllDevices() { return mAllDevice; }

    public void insert(Device device) { mRepository.insert(device); }

    public void update(Device device) { mRepository.update(device); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteDevice(Device device) {mRepository.deleteDevice(device);}

    public List<Device> getAllWithLocationName(String locationName)
    { return mRepository.getDeviceByLocationName(locationName); }

    public LiveData<Device> getWithDeviceName(String deviceName)
    { return mRepository.getDevice(deviceName); }
}
