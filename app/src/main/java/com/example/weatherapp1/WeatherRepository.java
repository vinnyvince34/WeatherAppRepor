package com.example.weatherapp1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class WeatherRepository {
    private WeatherDAO mWeatherDOA;
    private LiveData<List<DisplayClass>> mAllWeather;

    WeatherRepository(Application application) {
        WeatherRoomDB db = WeatherRoomDB.getDB(application);
        mWeatherDOA= db.weatherDAO();
        mAllWeather = mWeatherDOA.getAllMainWeather();
    }

    LiveData<List<DisplayClass>> getmAllWeather() {
        return mAllWeather;
    }

    public Observable
    insert (final DisplayClass displayClass) {
        return Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                mWeatherDOA.insert(displayClass);
                return Observable.just(true);
            }
        });

//        new insertAsyncTask(mWeatherDOA).doInBackground(displayClass);
    }

    private static class insertAsyncTask extends AsyncTask<DisplayClass, Void, Void> {
        private WeatherDAO mAsyncTaskDao;

        insertAsyncTask(WeatherDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(DisplayClass... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
