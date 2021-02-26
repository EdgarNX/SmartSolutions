package com.neo.smartsolutions.devices.device_local_db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.neo.smartsolutions.locations.location_local_db.Location;
import com.neo.smartsolutions.locations.location_local_db.LocationDao;
import com.neo.smartsolutions.locations.location_local_db.LocationRoomDatabase;

import java.text.DecimalFormat;
import java.util.List;

class DeviceRepository {

    private DeviceDao mDeviceDao;
    private LiveData<List<Device>> mAllDevices;


    DeviceRepository(Application application) {
        DeviceRoomDatabase db = DeviceRoomDatabase.getDatabase(application);
        mDeviceDao = db.deviceDao();
        mAllDevices =  mDeviceDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Device>> getAll() {
        return mAllDevices;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Device device) {
        DeviceRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDeviceDao.insert(device);
        });
    }

    private static class deleteAllDevicesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DeviceDao mAsyncTaskDao;

        deleteAllDevicesAsyncTask(DeviceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteAll()  {
         new deleteAllDevicesAsyncTask(mDeviceDao).execute();
    }
}