package com.example.hoangtruc.weatherapp.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import com.example.hoangtruc.weatherapp.utils.ConstantUtil;

public class SharePreferenceHelper {
    private static SharePreferenceHelper sharePreferenceHelper;
    private static SharedPreferences sharedPreferences;

    public static SharePreferenceHelper getInstance(Context context) {
        if (sharePreferenceHelper == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharePreferenceHelper = new SharePreferenceHelper();
        }
        return sharePreferenceHelper;
    }

    public Location getLastLocation() {
        Location location = new Location("");
        location.setLatitude(getDouble(ConstantUtil.LAST_LOCATION_LAT));
        location.setLongitude(getDouble(ConstantUtil.LAST_LOCATION_LONG));
        return location;
    }

    public void setLastLocation(Location lastLocation) {
        setDouble(ConstantUtil.LAST_LOCATION_LAT, lastLocation.getLatitude());
        setDouble(ConstantUtil.LAST_LOCATION_LONG, lastLocation.getLongitude());
    }

    private double getDouble(String key) {
        return Double.longBitsToDouble(sharedPreferences.getLong(key, Double.doubleToLongBits(0)));

    }

    private void setDouble(String key, double val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, Double.doubleToRawLongBits(val));
        editor.commit();
    }

}
