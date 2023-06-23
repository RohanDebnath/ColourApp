package com.example.colorapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataDao dataDao();
}
