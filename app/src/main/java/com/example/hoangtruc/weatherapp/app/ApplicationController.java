package com.example.hoangtruc.weatherapp.app;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

import com.example.hoangtruc.weatherapp.retrofit.DataClient;
import com.example.hoangtruc.weatherapp.retrofit.RetrofitClient;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class ApplicationController extends Application {
    private static Application mApplication;
    private DataClient mDataClient;
    private Scheduler mScheduler;

    private static ApplicationController get(Application context){
        return (ApplicationController) context.getApplicationContext();
    }
    public static ApplicationController create(Application context){
        return ApplicationController.get(context);
    }
    public DataClient getDataClient(){
        if (mDataClient==null){
            mDataClient= RetrofitClient.getClient();
        }
        return mDataClient;
    }
    public Scheduler subcribeScheduler(){
        if (mScheduler==null){
            mScheduler= Schedulers.io();
        }
        return mScheduler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
    public static Application getInstance() {
        return mApplication;
    }
    public void setDataClient(DataClient dataClient) {
        mDataClient = dataClient;
    }

    public void setScheduler(Scheduler scheduler) {
        mScheduler = scheduler;
    }
}
