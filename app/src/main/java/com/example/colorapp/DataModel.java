package com.example.colorapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data")
public class DataModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String colorHashCode;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorHashCode() {
        return colorHashCode;
    }

    public void setColorHashCode(String colorHashCode) {
        this.colorHashCode = colorHashCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DataModel(String colorHashCode, String date) {
        this.colorHashCode = colorHashCode;
        this.date = date;
    }
}
