package ua.tarastom.gufi.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ua.tarastom.gufi.model.Service;
import ua.tarastom.gufi.utils.Converters;

@Database(entities = {Service.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ServiceDataBase extends RoomDatabase {

    private static ServiceDataBase serviceDatabase;
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "allservices.db";

    public static ServiceDataBase getInstance(Context context) {
        synchronized (LOCK) {
            if (serviceDatabase == null) {
                serviceDatabase = Room.databaseBuilder(context, ServiceDataBase.class, DB_NAME)
                        .fallbackToDestructiveMigration().build();
            }
            return serviceDatabase;
        }
    }

    public abstract ServiceDao serviceDao();
}
