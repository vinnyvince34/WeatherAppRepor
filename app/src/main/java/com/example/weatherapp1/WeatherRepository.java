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
        return mWeatherDOA.getAllMainWeather();
    }

    public Observable<DisplayClass>

    insert (final DisplayClass displayClass) {
        return Observable.defer((Callable<ObservableSource<DisplayClass>>) () -> {
            mWeatherDOA.insert(displayClass);
            return Observable.just(displayClass);
        });
    }

    public void deleteAll() {
        mWeatherDOA.deleteAll();
        return;
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
