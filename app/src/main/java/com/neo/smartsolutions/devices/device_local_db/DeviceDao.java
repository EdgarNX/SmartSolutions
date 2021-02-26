package com.neo.smartsolutions.devices.device_local_db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DeviceDao {

    @Query("SELECT * FROM device_table")
    LiveData<List<Device>> getAll();

    @Insert
    void insert(Device device);

    @Delete
    void delete(Device device);

    @Query("DELETE FROM device_table")
    void deleteAll();

    @Update
    void update(Device device);
}
