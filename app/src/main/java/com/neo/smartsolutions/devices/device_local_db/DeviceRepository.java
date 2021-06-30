package com.neo.smartsolutions.devices.device_local_db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

class DeviceRepository {

    private DeviceDao mDeviceDao;
    private LiveData<List<Device>> mAllDevices;

    DeviceRepository(Application application) {
        DeviceRoomDatabase db = DeviceRoomDatabase.getDatabase(application);
        mDeviceDao = db.deviceDao();
        mAllDevices = mDeviceDao.getAll();
    }

    LiveData<List<Device>> getAll() {
        return mAllDevices;
    }

    LiveData<Device> getDevice(String deviceName) {
        return mDeviceDao.getDevice(deviceName);
    }

    void insert(Device device) {
        DeviceRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDeviceDao.insert(device);
            }
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

    public void deleteAll() {
        new deleteAllDevicesAsyncTask(mDeviceDao).execute();
    }

    private static class deleteDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DeviceDao mAsyncTaskDao;

        deleteDeviceAsyncTask(DeviceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Device... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void deleteDevice(Device device) {
        new deleteDeviceAsyncTask(mDeviceDao).execute(device);
    }

    public List<Device> getDeviceByLocationName(String locationName){
        try {
            return new getDeviceByLocationNameAsyncTask(mDeviceDao).execute(locationName).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getDeviceByLocationNameAsyncTask extends android.os.AsyncTask<String, Void, List<Device>> {
        private final DeviceDao mAsyncTaskDao;

        private getDeviceByLocationNameAsyncTask(DeviceDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }


        @Override
        protected List<Device> doInBackground(String... params) {
            return mAsyncTaskDao.getDevices(params[0]);
        }
    }

    void update(Device device) {
        DeviceRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDeviceDao.update(device);
        });
    }

}