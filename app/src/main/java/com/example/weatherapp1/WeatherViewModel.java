package com.example.weatherapp1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository mRepository;
    private LiveData<List<DisplayClass>> mAllWeather;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WeatherRepository(application);
        mAllWeather = mRepository.getmAllWeather();
    }

    LiveData<List<DisplayClass>> getmAllWeather() {return mAllWeather;}

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(DisplayClass displayClass) {
        mRepository.insert(displayClass);
    }
}
