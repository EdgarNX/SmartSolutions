package com.neo.smartsolutions.devices.device_local_db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.neo.smartsolutions.locations.location_local_db.Location;

import java.util.List;

@Dao
public interface DeviceDao {

    @Query("SELECT * FROM device_table")
    LiveData<List<Device>> getAll();

    @Insert
    void insert(Device device);

//    @RawQuery(observedEntities = Device.class)
//    List<Device> getDevices(SupportSQLiteQuery query);

    @Query("SELECT * FROM device_table WHERE location = :locationName")
    List<Device> getDevices(String locationName);

    @Delete
    void delete(Device device);

    @Query("DELETE FROM device_table")
    void deleteAll();

    @Update
    void update(Device device);
}
