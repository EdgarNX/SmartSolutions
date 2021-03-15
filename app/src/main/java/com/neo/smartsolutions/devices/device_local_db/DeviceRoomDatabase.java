package com.neo.smartsolutions.devices.device_local_db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Device.class}, version = 1, exportSchema = false)
public abstract class DeviceRoomDatabase extends RoomDatabase {

    public abstract DeviceDao deviceDao();

    private static volatile DeviceRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DeviceRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DeviceRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DeviceRoomDatabase.class, "device_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                DeviceDao dao = INSTANCE.deviceDao();
                dao.deleteAll();
            });
        }
    };
}