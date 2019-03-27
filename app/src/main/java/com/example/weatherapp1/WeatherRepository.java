package com.example.weatherapp1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WeatherRepository {
    private WeatherDAO mWeatherDOA;
    private LiveData<List<MainWeatherClass>> mAllWeather;

    WeatherRepository(Application application) {
        WeatherRoomDB db = WeatherRoomDB.getDB(application);
        mWeatherDOA= db.weatherDAO();
        mAllWeather = mWeatherDOA.getAllMainWeather();
    }

    LiveData<List<MainWeatherClass>> getmAllWeather() {
        return mAllWeather;
    }

    public void insert (MainWeatherClass mainWeatherClass) {
        new insertAsyncTask(mWeatherDOA).doInBackground(mainWeatherClass);
    }

    private static class insertAsyncTask extends AsyncTask<MainWeatherClass, Void, Void> {
        private WeatherDAO mAsyncTaskDao;

        insertAsyncTask(WeatherDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(MainWeatherClass... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
