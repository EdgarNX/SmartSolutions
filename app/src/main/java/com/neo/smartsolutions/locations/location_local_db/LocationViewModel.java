package com.neo.smartsolutions.locations.location_local_db;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neo.smartsolutions.devices.device_local_db.Device;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {

    private LocationRepository mRepository;

    private LiveData<List<Location>> mAllLocations;

    public LocationViewModel (Application application) {
        super(application);
        mRepository = new LocationRepository(application);
        mAllLocations = mRepository.getAll();
    }

    public LiveData<List<Location>> getAllLocations() { return mAllLocations; }

    public void insert(Location location) { mRepository.insert(location); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteLocation(Location location) {mRepository.deleteLocation(location);}

}
