package com.example.colorapp;

public class DataModel {
    private String colorHashCode;
    private String date;

    public DataModel(String colorHashCode, String date) {
        this.colorHashCode = colorHashCode;
        this.date = date;
    }

    public String getColorHashCode() {
        return colorHashCode;
    }

    public String getDate() {
        return date;
    }
}
