package com.example.weatherapp1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository mRespository;
    private LiveData<List<DisplayClass>> mAllWeather;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mRespository = new WeatherRepository(application);
        mAllWeather = mRespository.getmAllWeather();
    }

    LiveData<List<DisplayClass>> getmAllWeather() {return mAllWeather;}

    public void insert(DisplayClass displayClass) {mRespository.insert(displayClass);}


}
