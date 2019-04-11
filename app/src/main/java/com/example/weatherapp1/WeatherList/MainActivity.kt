package com.example.weatherapp1.WeatherList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

import com.example.weatherapp1.Data.Model.DisplayClass
import com.example.weatherapp1.R
import com.example.weatherapp1.WeatherManipulator.Main3Activity

import java.util.ArrayList

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main3.view.*

class MainActivity : AppCompatActivity() {
    private var weatherList = ArrayList<DisplayClass>()
    private lateinit var adapter: ListAdapter
    private val observable = object : Completable() {
        override fun subscribeActual(observer: CompletableObserver) {

        }
    }
    private var mWeatherViewModel: WeatherViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)

        weatherList.clear()
        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java!!)

        val cityField = findViewById<View>(R.id.cityField) as? TextView
        val updatedField = findViewById<View>(R.id.updateField) as? TextView
        val detailsField = findViewById<View>(R.id.detailField) as? TextView
        val currentTemperatureField = findViewById<View>(R.id.tempField) as? TextView
        val humidity_field = findViewById<View>(R.id.humidField) as? TextView
        val pressure_field = findViewById<View>(R.id.pressureField) as? TextView
        val lv = findViewById<View>(R.id.weatherLV) as? ListView
        val addButton = findViewById<View>(R.id.AddBTN) as? Button

        if (addButton != null) {
            addButton.setOnClickListener { v ->
                bEdit = false
                bIntentEmpty = false
                val intent = Intent(v.context, Main3Activity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }

        adapter = ListAdapter(this, weatherList)
        if (lv != null) {
            lv.adapter = adapter
        }
        mWeatherViewModel!!.getmAllWeather().observe(this, Observer { displayClasses -> displayClasses?.let { adapter.setmWeathers(it) } })

    }

    override fun onResume() {
        super.onResume()
        mWeatherViewModel!!.getmAllWeather().observe(this, Observer { displayClasses -> displayClasses?.let { adapter.setmWeathers(it) } })
    }

    override fun onPause() {
        super.onPause()
        observable.unsubscribeOn(Schedulers.io())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (bEdit == true) {
            val receiver = data!!.getParcelableExtra<DisplayClass>("UpdateData")
            val position = receiver.uid
            weatherList[position] = receiver
        }
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
        val REQUEST_CODE = 1
        var bEdit = false
        var bIntentEmpty = true
    }
}
