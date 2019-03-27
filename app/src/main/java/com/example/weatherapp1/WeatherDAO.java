package com.example.weatherapp1;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WeatherDAO {
    @Insert
    void insert (MainWeatherClass... mainWeatherClass);

    @Insert
    void insert(MainWeatherClass mainWeatherClass);

    @Query("DELETE FROM WeatherContent")
    void deleteAll();

    @Query("SELECT * FROM WeatherContent")
    LiveData<List<MainWeatherClass>> getAllMainWeather();
}

