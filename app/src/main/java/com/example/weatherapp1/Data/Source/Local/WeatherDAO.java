package com.example.weatherapp1.Data.Source.Local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.weatherapp1.Data.Model.DisplayClass;

import java.util.List;

@Dao
public interface WeatherDAO {
    @Insert
    void insert (DisplayClass... displayClass);

    @Insert
    void insert(DisplayClass displayClass);

    @Query("DELETE FROM WeatherContent")
    void deleteAll();

    @Query("SELECT * FROM WeatherContent")
    LiveData<List<DisplayClass>> getAllMainWeather();
}

