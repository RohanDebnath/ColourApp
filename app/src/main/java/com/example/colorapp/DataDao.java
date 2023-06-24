package com.example.colorapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {
    @Query("SELECT * FROM data")
    LiveData<List<DataModel>> getAllData();

    @Insert
    void insertData(DataModel data);

    @Delete
    void deleteData(DataModel data);

    @Update
    void updateData(DataModel data);
}
