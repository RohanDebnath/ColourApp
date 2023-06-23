package com.example.colorapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<DataModel>> dataList = new MutableLiveData<>();

    public LiveData<List<DataModel>> getDataList() {
        return dataList;
    }

    public void insertData(String colorHashCode, String date) {
        List<DataModel> currentList = dataList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(new DataModel(colorHashCode, date));
        dataList.setValue(currentList);
    }
}
