package com.example.colorapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private DataDao dataDao;
    private LiveData<List<DataModel>> dataList;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = Room.databaseBuilder(application, AppDatabase.class, "data-db").build();
        dataDao = database.dataDao();
        dataList = dataDao.getAllData();
    }

    public LiveData<List<DataModel>> getDataList() {
        return dataList;
    }

    public void insertData(String colorHashCode, String date) {
        DataModel data = new DataModel(colorHashCode, date);
        new Thread(() -> dataDao.insertData(data)).start();
    }

    public int getSyncedDataCount() {
        int count = 0;
        List<DataModel> dataList = getDataList().getValue();
        if (dataList != null) {
            for (DataModel data : dataList) {
                if (data.isSynced()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getPendingDataCount() {
        int count = 0;
        List<DataModel> dataList = getDataList().getValue();
        if (dataList != null) {
            for (DataModel data : dataList) {
                if (!data.isSynced()) {
                    count++;
                }
            }
        }
        return count;
    }
    public void updateData(DataModel data) {
        new Thread(() -> dataDao.updateData(data)).start();
    }

}
