package com.example.weatherapp1.Misc

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log

import com.example.weatherapp1.Data.Model.Cloud
import com.example.weatherapp1.Data.Model.Coord
import com.example.weatherapp1.Data.Model.Main
import com.example.weatherapp1.Data.Model.MainWeatherClass
import com.example.weatherapp1.Data.Model.Sys
import com.example.weatherapp1.Data.Model.Weather
import com.example.weatherapp1.Data.Model.Wind
import com.example.weatherapp1.Data.Source.Remote.listResourcesApi
import com.google.gson.JsonParser
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import org.json.JSONArray
import org.json.JSONObject

import java.io.IOException

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Networking {
    internal var client = OkHttpClient()

    private val retrofit: Retrofit? = null
    private val BASEURL = "http://api.openweathermap.org/data/2.5/"

    val retrofitObject: listResourcesApi
        get() {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            val service = retrofit.create<listResourcesApi>(listResourcesApi::class.java!!)
            val call = service.mainWeatherClassObserve

            return service
        }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Throws(IOException::class)
    fun run(url: String): String {
        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).execute().use { response -> return response.body()!!.string() }
    }

    fun ConvertJSON(json: JSONObject): MainWeatherClass? {
        var i = 0
        val main: MainWeatherClass? = null
        try {
            var jsonChild: JSONObject? = null
            var jsonArray: JSONArray? = null
            val jsonParser = JsonParser()

            val base = json.get("base").toString()
            val visibility = json.get("visibility").toString()
            val dt = json.get("dt").toString()
            val id = json.get("id").toString()
            val name = json.get("name").toString()
            val httpCode = json.get("cod").toString()

            jsonChild = json.get("coord") as JSONObject
            val coord = Coord(jsonChild.get("lon").toString(), jsonChild.get("lat").toString())
            i++

            jsonChild = json.get("clouds") as JSONObject
            val cloud = Cloud(jsonChild.get("all").toString())
            i++

            jsonChild = json.get("wind") as JSONObject
            val wind = Wind(jsonChild.get("speed").toString())
            i++

            jsonChild = json.get("sys") as JSONObject
            val system = Sys(jsonChild.get("type").toString(), jsonChild.get("id").toString(), jsonChild.get("message").toString(), jsonChild.get("country").toString(), jsonChild.get("sunrise").toString(), jsonChild.get("sunset").toString())
            i++

            jsonArray = json.get("weather") as JSONArray
            jsonChild = jsonArray.getJSONObject(0)
            Log.d("Network", "ConvertJSON: " + jsonChild!!.toString())
            Log.d("Network", "ConvertJSON: " + jsonChild.get("id").toString())
            //            Weather weather = new Weather(jsonChild.get("id").toString(), jsonChild.get("main").toString(), jsonChild.get("description").toString(), jsonChild.get("icon").toString());
            var weather = Weather()
            weather = Weather(jsonChild.get("id").toString(), jsonChild.get("icon").toString(), jsonChild.get("main").toString(), jsonChild.get("description").toString())
            i++

            jsonChild = json.get("main") as JSONObject
            val other = Main(jsonChild.get("temp").toString(), jsonChild.get("pressure").toString(), jsonChild.get("humidity").toString(), jsonChild.get("temp_min").toString(), jsonChild.get("temp_max").toString())
            i++

            //            main = new MainWeatherClass(weather, cloud, wind, coord, system, other, base, visibility, dt, id, name, httpCode);
            Log.d("Network", "ConvertJSON: " + main!!.toString())
        } catch (e: Exception) {
            Log.d("Exception", "ConvertJSON: " + e.message + ", " + i)
            return null
        }

        return main
    }
}
