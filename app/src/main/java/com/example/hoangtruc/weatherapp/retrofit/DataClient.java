package com.example.hoangtruc.weatherapp.retrofit;

import android.databinding.ObservableField;

import com.example.hoangtruc.weatherapp.models.HourlyWeatherResponse;
import com.example.hoangtruc.weatherapp.models.Weather;
import com.example.hoangtruc.weatherapp.models.WeatherResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface DataClient {
    @GET("weather")
    Observable<WeatherResponse> getWeather(@QueryMap Map<String, String> local);

    @GET("forecast")
    Observable<HourlyWeatherResponse> getHourlyWeather(@QueryMap Map<String, String> local);
}
