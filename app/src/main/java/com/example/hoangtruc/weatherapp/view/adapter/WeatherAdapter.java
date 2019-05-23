package com.example.hoangtruc.weatherapp.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoangtruc.weatherapp.R;
import com.example.hoangtruc.weatherapp.databinding.ItemWeatherBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherAdapterViewHolder> {

    private List<com.example.hoangtruc.weatherapp.models.List> mLists;
    private LayoutInflater mInflater;

    public WeatherAdapter() {
        this.mLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeatherAdapter.WeatherAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        ItemWeatherBinding mItemWeatherBinding = DataBindingUtil.inflate(mInflater, R.layout.item_weather, parent, false);
        return new WeatherAdapterViewHolder(mItemWeatherBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherAdapterViewHolder holder, int position) {
        holder.bindWeather(mLists.get(position));
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }
      public void addData(List<com.example.hoangtruc.weatherapp.models.List> lists){
        if (lists==null){
            return;
        }
        mLists.clear();
        mLists.addAll(lists);
        notifyDataSetChanged();
      }
    public class WeatherAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemWeatherBinding mItemWeatherBinding;

        public WeatherAdapterViewHolder(ItemWeatherBinding itemWeatherBinding) {
            super(itemWeatherBinding.getRoot());
            mItemWeatherBinding = itemWeatherBinding;
        }

        void bindWeather(com.example.hoangtruc.weatherapp.models.List list) {
            mItemWeatherBinding.setList(list);
            mItemWeatherBinding.setPosition(String.valueOf(getAdapterPosition() + 1));
            mItemWeatherBinding.executePendingBindings();
        }
    }
}
