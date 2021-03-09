package com.neo.smartsolutions.devices.device_local_db;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

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

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteDevice(Device device) {mRepository.deleteDevice(device);}

}
