package com.example.weatherapp1.WeatherManipulator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.TextInputEditText
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.example.weatherapp1.Data.Model.DisplayClass
import com.example.weatherapp1.Data.Model.MainWeatherClass
import com.example.weatherapp1.Data.Source.Remote.listResourcesApi
import com.example.weatherapp1.Data.Source.WeatherRepository
import com.example.weatherapp1.WeatherList.MainActivity
import com.example.weatherapp1.Misc.Networking
import com.example.weatherapp1.R

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.CompletableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class Main3Activity : AppCompatActivity() {
    internal lateinit var intent: Intent
    internal var toEdit: MainWeatherClass? = null
    internal var toAdd: MainWeatherClass? = null
    internal var observable: Completable = object : Completable() {
        override fun subscribeActual(observer: CompletableObserver) {
            return
        }
    }
    internal lateinit var service: listResourcesApi
    internal lateinit var DisplayAdd: DisplayClass
    internal lateinit var DisplayEdit: DisplayClass
    internal var repository = WeatherRepository(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val cityInput = findViewById<View>(R.id.CityInput) as TextInputEditText
        val SaveBtn = findViewById<View>(R.id.SaveBtn) as Button
        val textView = findViewById<View>(R.id.TextView) as TextView

        observable.subscribeOn(Schedulers.newThread())
        service = Networking.retrofitObject

        observable = service.mainWeatherClassObserve
                .flatMapCompletable {
                    val repository: WeatherRepository? = null
                    repository!!.getmAllWeather().value
                    repository as CompletableSource?
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


        if (MainActivity.bEdit == true) {
            intent = getIntent()
            toEdit = intent.getParcelableExtra<Parcelable>("EditUser") as MainWeatherClass
            DisplayEdit = DisplayClass()

            textView.text = "Edit City"
            cityInput.setText(toEdit!!.name)

            SaveBtn.setOnClickListener { v ->
                val sNewAddress = cityInput.text.toString()
                service.getMainWeatherClassJson(sNewAddress, "metric", "b60c7e86a1a0721e4f380436455f7f25")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.io())
                        .map { mainWeatherClass -> mappingMainWeather(mainWeatherClass) }
                        .flatMap { displayClass -> repository.insert(displayClass) }
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError { throwable -> Log.e(TAG, "accept: " + throwable.cause) }
                        .doOnNext { displayClass -> DisplayEdit = displayClass }
                        .doOnComplete {
                            val passIntent = Intent(v.context, DisplayClass::class.java).putExtra("UpdateData", toEdit as Parcelable?)
                            setResult(Activity.RESULT_OK, passIntent)
                            finish()
                        }
                        .subscribe()
            }
        } else {
            intent = getIntent()
            toAdd = MainWeatherClass()
            DisplayAdd = DisplayClass()

            textView.text = "Add City"

            SaveBtn.setOnClickListener { v ->
                val sNewAddress = cityInput.text.toString()
                service.getMainWeatherClassJson(sNewAddress, "metric", "b60c7e86a1a0721e4f380436455f7f25")
                        .subscribeOn(Schedulers.newThread())
                        .map { mainWeatherClass -> mappingMainWeather(mainWeatherClass) }
                        .flatMap { displayClass -> repository.insert(displayClass) }
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError { throwable -> Log.e(TAG, "accept: " + throwable.cause) }
                        .doOnNext { displayClass -> DisplayAdd = displayClass }
                        .doOnComplete {
                            val passIntent = Intent(v.context, DisplayClass::class.java).putExtra("AddData", DisplayAdd as Parcelable)
                            setResult(Activity.RESULT_OK, passIntent)
                            finish()
                        }
                        .subscribe()
            }
        }
    }

    private fun mappingMainWeather(mainWeatherClass: MainWeatherClass): DisplayClass {
        val displayAdd = DisplayClass()
        Log.e(TAG, "onNext: $mainWeatherClass")
        displayAdd.weather = mainWeatherClass.weather!![0]
        displayAdd.visibility = Integer.parseInt(mainWeatherClass.visibility!!)
        displayAdd.main = mainWeatherClass.main
        displayAdd.cloud = mainWeatherClass.cloud
        displayAdd.name = mainWeatherClass.name
        displayAdd.wind = mainWeatherClass.wind
        Log.e(TAG, "onNext: $displayAdd")
        return displayAdd
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private val TAG = "Main3Activity"
    }
}
