package com.example.colorapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.List;

import android.app.Application;

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
}
