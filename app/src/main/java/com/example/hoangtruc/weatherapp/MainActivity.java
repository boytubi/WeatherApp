package com.example.hoangtruc.weatherapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.example.hoangtruc.weatherapp.databinding.ActivityMainBinding;
import com.example.hoangtruc.weatherapp.viewmodels.CurWeatherViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;
    private CurWeatherViewModel mCurWeatherViewModel;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private List<Place.Field> fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        initDatabinding();
        Places.initialize(getApplicationContext(), BuildConfig.THE_GOOGLE_API);
        PlacesClient placesClient = Places.createClient(this);

    }

    private void initDatabinding() {
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mCurWeatherViewModel = ViewModelProviders.of(this).get(CurWeatherViewModel.class);
        mActivityMainBinding.setCurWeatherViewModel(mCurWeatherViewModel);
        mActivityMainBinding.setLifecycleOwner(this);
        mCurWeatherViewModel.setLifecycleOwner(this);
        mCurWeatherViewModel.loadCurrentWeather(getApplicationContext());
        mActivityMainBinding.setMainActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCurWeatherViewModel.reset();
    }

    public void onClickSearch() {
        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)

                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== AUTOCOMPLETE_REQUEST_CODE){

            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.d("Place", "Place: " + place.getName() + ", " +place.getLatLng().latitude);

                mCurWeatherViewModel.loadWeatherBySearch(String.valueOf(place.getLatLng().latitude),String.valueOf(place.getLatLng().longitude));
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}
