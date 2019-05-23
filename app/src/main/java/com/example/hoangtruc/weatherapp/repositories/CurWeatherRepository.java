package com.example.hoangtruc.weatherapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.util.Log;

import com.example.hoangtruc.weatherapp.BuildConfig;
import com.example.hoangtruc.weatherapp.app.ApplicationController;
import com.example.hoangtruc.weatherapp.models.HourlyWeatherResponse;
import com.example.hoangtruc.weatherapp.models.List;
import com.example.hoangtruc.weatherapp.utils.ConstantUtil;
import com.example.hoangtruc.weatherapp.models.WeatherResponse;
import com.example.hoangtruc.weatherapp.retrofit.DataClient;
import com.example.hoangtruc.weatherapp.view.adapter.WeatherAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CurWeatherRepository {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void fetchCurrentDataWeather(String lat, String lon, final MutableLiveData<WeatherResponse> data, Application application) {

        ApplicationController applicationController = ApplicationController.create(application);
        DataClient dataClient = applicationController.getDataClient();
        Map<String, String> map = new HashMap<>();
        map.put("lat", lat);
        map.put("lon", lon);
        map.put("units", ConstantUtil.METRIC);
        map.put("appid", BuildConfig.THE_OPENMAP_API);
        Disposable disposable = dataClient.getWeather(map)
                .subscribeOn(applicationController.subcribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Consumer<WeatherResponse>() {
                    @Override
                    public void accept(WeatherResponse weatherResponse) throws Exception {
                       data.setValue(weatherResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ErrorThrowble", throwable.getMessage());
                    }
                });

        mCompositeDisposable.add(disposable);

    }

    public void fetchHourlyWeather(String lat, String lon, final ObservableField<WeatherAdapter> weatherAdapter, Application application) {


        ApplicationController applicationController = ApplicationController.create(application);
        DataClient dataClient = applicationController.getDataClient();
        Map<String, String> map = new HashMap<>();
        map.put("lat", lat);
        map.put("lon", lon);
        map.put("units", ConstantUtil.METRIC);
        map.put("appid", BuildConfig.THE_OPENMAP_API);

        Disposable disposableHourly = dataClient.getHourlyWeather(map)
                .subscribeOn(applicationController.subcribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HourlyWeatherResponse>() {
                    @Override
                    public void accept(HourlyWeatherResponse hourlyWeatherResponse) throws Exception {
                        Log.d("listData", hourlyWeatherResponse.getList().get(0).getDtTxt() );

                        weatherAdapter.get().addData(hourlyWeatherResponse.getList());


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ErrorThrowble", throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposableHourly);

    }
    public void fetchWeatherByID(String id ,final MutableLiveData<WeatherResponse> data,final ObservableField<WeatherAdapter> weatherAdapter,Application application) {


        ApplicationController applicationController = ApplicationController.create(application);
        DataClient dataClient = applicationController.getDataClient();
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("units", ConstantUtil.METRIC);
        map.put("appid", BuildConfig.THE_OPENMAP_API);

        Disposable disposableHourly = dataClient.getHourlyWeather(map)
                .subscribeOn(applicationController.subcribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HourlyWeatherResponse>() {
                    @Override
                    public void accept(HourlyWeatherResponse hourlyWeatherResponse) throws Exception {


                        weatherAdapter.get().addData(hourlyWeatherResponse.getList());


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ErrorThrowble", throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposableHourly);

    }
    public void unsubscribeFromObservable() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    public void reset() {
        unsubscribeFromObservable();
        mCompositeDisposable = null;

    }

}
