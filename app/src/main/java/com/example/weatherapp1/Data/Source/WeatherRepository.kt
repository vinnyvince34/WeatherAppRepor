package com.example.weatherapp1.Data.Source

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

import com.example.weatherapp1.Data.Model.DisplayClass
import com.example.weatherapp1.Data.Source.Local.WeatherDAO
import com.example.weatherapp1.Data.Source.Local.WeatherRoomDB
import java.util.concurrent.Callable

import io.reactivex.Observable
import io.reactivex.ObservableSource

class WeatherRepository(application: Application) {
    private val mWeatherDOA: WeatherDAO
    private val mAllWeather: LiveData<List<DisplayClass>>

    init {
        val db = WeatherRoomDB.getDB(application)
        mWeatherDOA = db!!.weatherDAO()
        mAllWeather = mWeatherDOA.allMainWeather
    }

    fun getmAllWeather(): LiveData<List<DisplayClass>> {
        return mWeatherDOA.allMainWeather
    }

    fun

            insert(displayClass: DisplayClass): Observable<DisplayClass> {
        return Observable.defer({
            mWeatherDOA.insert(displayClass)
            Observable.just(displayClass)
        } as Callable<ObservableSource<DisplayClass>>)
    }

    fun deleteAll() {
        mWeatherDOA.deleteAll()
        return
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: WeatherDAO) : AsyncTask<DisplayClass, Void, Void>() {

        override fun doInBackground(vararg params: DisplayClass): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}
