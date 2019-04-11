package com.example.weatherapp1.WeatherList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.weatherapp1.Data.Model.DisplayClass;
import com.example.weatherapp1.Data.Source.WeatherRepository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository mRepository;
    private LiveData<List<DisplayClass>> mAllWeather;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WeatherRepository(application);
        mAllWeather = mRepository.getmAllWeather();
    }

    public LiveData<List<DisplayClass>> getmAllWeather() {return mAllWeather;}

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(DisplayClass displayClass) {
        mRepository.insert(displayClass);
    }
}
