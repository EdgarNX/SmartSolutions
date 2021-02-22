package com.neo.smartsolutions.locations.location_local_db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM location_table")
    LiveData<List<Location>> getAll();

    @Insert
    void insert(Location location);

    @Delete
    void delete(Location location);

    @Query("DELETE FROM location_table")
    void deleteAll();

    @Update
    void update(Location location);
}
