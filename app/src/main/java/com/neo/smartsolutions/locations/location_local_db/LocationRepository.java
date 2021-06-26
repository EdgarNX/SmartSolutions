package com.neo.smartsolutions.locations.location_local_db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class LocationRepository {

    private LocationDao mLocationDao;
    private LiveData<List<Location>> mAllLocations;


    LocationRepository(Application application) {
        LocationRoomDatabase db = LocationRoomDatabase.getDatabase(application);
        mLocationDao = db.locationDao();
        mAllLocations =  mLocationDao.getAll();
    }

    LiveData<List<Location>> getAll() {
        return mAllLocations;
    }

    void insert(Location location) {
        LocationRoomDatabase.databaseWriteExecutor.execute(() -> {
            mLocationDao.insert(location);
        });
    }

    private static class deleteAllLocationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private LocationDao mAsyncTaskDao;

        deleteAllLocationsAsyncTask(LocationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteAll()  {
         new deleteAllLocationsAsyncTask(mLocationDao).execute();
    }

    private static class deleteLocationAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao mAsyncTaskDao;

        deleteLocationAsyncTask(LocationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Location... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void deleteLocation(Location location)  {
        new LocationRepository.deleteLocationAsyncTask(mLocationDao).execute(location);
    }
}