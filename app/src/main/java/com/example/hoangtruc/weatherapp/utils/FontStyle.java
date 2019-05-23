package com.example.hoangtruc.weatherapp.utils;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import com.example.hoangtruc.weatherapp.app.ApplicationController;

import java.util.Hashtable;

public enum FontStyle {
    ROBOTO_MLIGHT("MLight.ttf"),
    ROBOTO_REGULAR("MRegular.ttf");

    private String name;

    FontStyle(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

