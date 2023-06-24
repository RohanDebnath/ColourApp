package com.example.colorapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data")
public class DataModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "color_hash_code")
    private String colorHashCode;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "is_synced")
    private boolean isSynced;

    public DataModel(String colorHashCode, String date) {
        this.colorHashCode = colorHashCode;
        this.date = date;
        this.isSynced = false; // Set the initial value of isSynced to false
    }

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

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
