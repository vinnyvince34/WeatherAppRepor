package com.example.weatherapp1.WeatherList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

import com.example.weatherapp1.Data.Model.DisplayClass
import com.example.weatherapp1.R
import com.example.weatherapp1.WeatherList.MainActivity
import com.example.weatherapp1.WeatherManipulator.Main3Activity

import java.util.ArrayList

class ListAdapter(internal var activity: Activity, weatherList: ArrayList<DisplayClass>) : BaseAdapter() {
    private var weatherList: ArrayList<DisplayClass> = ArrayList(weatherList)

    override fun getCount(): Int {
        return weatherList.size
    }

    override fun getItem(position: Int): Any {
        return weatherList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder {
        var cityField: TextView? = null
        var detailsField: TextView? = null
        var currentTemperatureField: TextView? = null
        var humidity_field: TextView? = null
        var pressure_field: TextView? = null
        var updatedField: TextView? = null
        var deleteButton: Button? = null
        var editButton: Button? = null
        var lv: ListView? = null
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        val inflater = activity.layoutInflater
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_part, null)
            holder = ViewHolder()
            holder.cityField = convertView!!.findViewById<View>(R.id.cityField) as TextView
            holder.detailsField = convertView.findViewById<View>(R.id.detailField) as TextView
            holder.currentTemperatureField = convertView.findViewById<View>(R.id.tempField) as TextView
            holder.humidity_field = convertView.findViewById<View>(R.id.humidField) as TextView
            holder.pressure_field = convertView.findViewById<View>(R.id.pressureField) as TextView
            holder.updatedField = convertView.findViewById<View>(R.id.updateField) as TextView
            holder.deleteButton = convertView.findViewById<View>(R.id.deleteBTN) as Button
            holder.editButton = convertView.findViewById<View>(R.id.editBTN) as Button
            holder.lv = convertView.findViewById<View>(R.id.weatherLV) as? ListView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.deleteButton!!.tag = convertView
        holder.deleteButton!!.tag = position
        holder.deleteButton!!.setOnClickListener {
            weatherList.removeAt(position)
            notifyDataSetChanged()
        }

        holder.editButton!!.tag = convertView
        holder.editButton!!.tag = position
        holder.editButton!!.setOnClickListener { v ->
            index = position
            MainActivity.bEdit = true
            MainActivity.bIntentEmpty = false
            val intent = Intent(v.context, Main3Activity::class.java).putExtra("EditUser", weatherList[position] as Parcelable)
            activity.startActivityForResult(intent, MainActivity.REQUEST_CODE)
        }

        Log.d("Network", "getView: " + weatherList[position].toString())
        holder.cityField!!.text = "City: " + weatherList[position].name!!
        holder.detailsField!!.text = "Visibility: " + weatherList[position].visibility + " m"
        holder.currentTemperatureField!!.text = "Temperature: " + weatherList[position].main!!.temp + " C"
        holder.humidity_field!!.text = "Humidity: " + weatherList[position].main!!.pressure + "g/m3"
        holder.pressure_field!!.text = "Pressure: " + weatherList[position].main!!.pressure + "Pa"
        holder.updatedField!!.text = "Wind speed:" + weatherList[position].wind!!.speed + "m/s"
        return convertView
    }

    fun setmWeathers(weathers: List<DisplayClass>) {
        weatherList.clear()
        weatherList.addAll(weathers)
        notifyDataSetChanged()
    }

    companion object {
        var index = 0
    }
}
