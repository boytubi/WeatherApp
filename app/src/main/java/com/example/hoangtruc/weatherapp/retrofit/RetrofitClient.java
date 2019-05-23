package com.example.hoangtruc.weatherapp.retrofit;

import com.example.hoangtruc.weatherapp.utils.ConstantUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit  retrofit=null;
    public static  DataClient getClient(){
        if (retrofit==null){
            retrofit= new retrofit2.Retrofit.Builder()
            .baseUrl(ConstantUtil.OPENMAP_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        }
        return retrofit.create(DataClient.class);
    }
}
