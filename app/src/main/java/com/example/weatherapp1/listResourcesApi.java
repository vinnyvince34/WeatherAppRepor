package com.example.weatherapp1;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface listResourcesApi {

    @GET("weather?&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    Call<MainWeatherClass> getMainWeatherClassParam(
            @Query("q") String City);

    @GET("weather?q=London&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    Call<MainWeatherClass> getMainWeatherClass();

    @GET("weather?q=London&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    Observable<MainWeatherClass> getMainWeatherClassObserve();

    @GET("weather?q={City}&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    Observable<MainWeatherClass> getMainWeatherClassObserve(
            @Path("City") String City);

    @GET("weather")
    Observable<MainWeatherClass> getMainWeatherClassJson(
            @Query("q") String city,
            @Query("units") String unit,
            @Query("appid") String appId);
}