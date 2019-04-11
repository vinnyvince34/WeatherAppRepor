package com.example.weatherapp1.WeatherList

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

import com.example.weatherapp1.Data.Model.DisplayClass
import com.example.weatherapp1.Data.Source.WeatherRepository

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: WeatherRepository
    private val mAllWeather: LiveData<List<DisplayClass>>

    init {
        mRepository = WeatherRepository(application)
        mAllWeather = mRepository.getmAllWeather()
    }

    fun getmAllWeather(): LiveData<List<DisplayClass>> {
        return mAllWeather
    }

    fun deleteAll() {
        mRepository.deleteAll()
    }

    fun insert(displayClass: DisplayClass) {
        mRepository.insert(displayClass)
    }
}
