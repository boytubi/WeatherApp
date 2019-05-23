package com.example.hoangtruc.weatherapp.storage;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class CurrentLocationListener extends LiveData<Location> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleApiClient googleApiClient;

    private static CurrentLocationListener instance = null;
    private static SharePreferenceHelper mSharePreferenceHelper;
    public static CurrentLocationListener getInstance(Context appContext) {
        if (instance == null) {
            instance = new CurrentLocationListener(appContext);
            mSharePreferenceHelper= SharePreferenceHelper.getInstance(appContext);
        }
        return instance;
    }

    private CurrentLocationListener(Context appContext) {
        buildGoogleApiClient(appContext);
    }

    private synchronized void buildGoogleApiClient(Context appContext) {

        googleApiClient = new GoogleApiClient.Builder(appContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onActive() {
        googleApiClient.connect();
    }

    @Override
    protected void onInactive() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, this);
        }
        googleApiClient.disconnect();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle connectionHint) {

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (lastLocation != null) {
            setValue(lastLocation);
            mSharePreferenceHelper.setLastLocation(lastLocation);
        } else {
            setValue(mSharePreferenceHelper.getLastLocation());
        }

        if (hasActiveObservers() && googleApiClient.isConnected()) {

            LocationRequest locationRequest = LocationRequest.create();
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        Log.d(TAG, "Location changed received: " + location);
        setValue(location);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.w("onConnectionSuspended", "On Connection suspended " + cause);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.d("onConnectionFailed", "GoogleApiClient connection has failed " + result);
    }
}
