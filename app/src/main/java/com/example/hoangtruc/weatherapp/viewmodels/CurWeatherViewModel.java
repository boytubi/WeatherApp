package com.example.hoangtruc.weatherapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.ObservableField;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.hoangtruc.weatherapp.models.WeatherResponse;
import com.example.hoangtruc.weatherapp.repositories.CurWeatherRepository;
import com.example.hoangtruc.weatherapp.storage.CurrentLocationListener;
import com.example.hoangtruc.weatherapp.view.adapter.WeatherAdapter;


public class CurWeatherViewModel extends AndroidViewModel {
    private CurWeatherRepository mCurWeatherRepository;
    public MutableLiveData<WeatherResponse> mWeather = new MutableLiveData<>();
    public ObservableField<WeatherAdapter> mWeatherAdapter;
    private LifecycleOwner mLifecycleOwner;
private CurrentLocationListener mCurrentLocationListener;

    public CurWeatherViewModel(@NonNull final Application application) {
        super(application);
        mCurWeatherRepository = new CurWeatherRepository();
        mWeatherAdapter = new ObservableField<>(new WeatherAdapter());


    }

    public void loadCurrentWeather(Context context) {
        mCurrentLocationListener= CurrentLocationListener.getInstance(context);
        mCurrentLocationListener.observe(mLifecycleOwner, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                String lat = String.valueOf(location.getLatitude());
                String lon = String.valueOf(location.getLongitude());

                mCurWeatherRepository.fetchHourlyWeather(lat, lon, mWeatherAdapter, getApplication());
                mCurWeatherRepository.fetchCurrentDataWeather(lat, lon, mWeather, getApplication());
            }
        });
    }
    public void loadWeatherBySearch(String lat, String lon){
        mCurrentLocationListener.removeObservers(mLifecycleOwner);
        mCurWeatherRepository.fetchHourlyWeather(lat, lon, mWeatherAdapter, getApplication());
        mCurWeatherRepository.fetchCurrentDataWeather(lat, lon, mWeather, getApplication());

    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }

    public void reset() {
        mCurWeatherRepository.reset();

    }
}
