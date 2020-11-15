package ua.tarastom.gufi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ua.tarastom.gufi.model.Service;

@Dao
public interface ServiceDao {

    @Query("SELECT * FROM services")
    LiveData<List<Service>> getAllServices();

    @Query("SELECT * FROM services WHERE idService == :id")
    Service getServiceById(int id);

    @Query("DELETE FROM services")
    void deleteAllServices();

    @Delete
    void deleteService(Service service);

    @Insert
    void insertService(Service service);

}

