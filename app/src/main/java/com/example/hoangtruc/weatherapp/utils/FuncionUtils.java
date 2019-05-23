package com.example.hoangtruc.weatherapp.utils;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hoangtruc.weatherapp.app.ApplicationController;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

public class FuncionUtils {
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();
    public static String formatTime(long seconds) {
        Date date = new Date(seconds* 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
    public static String formatDate(long seconds) {
        Date date = new Date(seconds* 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }



    public static Typeface getTypeface(String name) {
        Typeface typeface = fontCache.get(name);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(ApplicationController.getInstance().getAssets(), ConstantUtil.PATH_FONT + name);
            } catch (NullPointerException e) {
                Log.e("FontUtil", "Font's not found " + name + "");
                return null;
            }
            fontCache.put(name, typeface);
        }
        return typeface;
    }
    public static String setWind(Double wind){
        return wind  +"  meter/sec";

    }
    public static String convertKilometers(int visibility){
        return visibility/1000  +" Km";
    }


}
