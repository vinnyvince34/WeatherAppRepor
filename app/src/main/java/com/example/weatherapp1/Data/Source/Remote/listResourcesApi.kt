package com.example.weatherapp1.Data.Source.Remote

import com.example.weatherapp1.Data.Model.MainWeatherClass
import com.google.gson.JsonObject

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface listResourcesApi {

    @get:GET("weather?q=London&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    val mainWeatherClass: Call<MainWeatherClass>

    @get:GET("weather?q=London&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    val mainWeatherClassObserve: Observable<MainWeatherClass>

    @GET("weather?&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    fun getMainWeatherClassParam(
            @Query("q") City: String): Call<MainWeatherClass>

    @GET("weather?q={City}&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    fun getMainWeatherClassObserve(
            @Path("City") City: String): Observable<MainWeatherClass>

    @GET("weather")
    fun getMainWeatherClassJson(
            @Query("q") city: String,
            @Query("units") unit: String,
            @Query("appid") appId: String): Observable<MainWeatherClass>

    @GET("weather")
    fun getMainWeatherClassJsonObject(
            @Query("q") city: String,
            @Query("units") unit: String,
            @Query("appid") appId: String): Observable<JsonObject>
}