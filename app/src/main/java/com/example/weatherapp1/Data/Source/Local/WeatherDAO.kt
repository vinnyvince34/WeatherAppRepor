package com.example.weatherapp1.Data.Source.Local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

import com.example.weatherapp1.Data.Model.DisplayClass

@Dao
interface WeatherDAO {

    @get:Query("SELECT * FROM WeatherContent")
    val allMainWeather: LiveData<List<DisplayClass>>

    @Insert
    fun insert(vararg displayClass: DisplayClass)

    @Insert
    fun insert(displayClass: DisplayClass)

    @Query("DELETE FROM WeatherContent")
    fun deleteAll()
}

