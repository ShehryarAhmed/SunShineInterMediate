package com.example.android.sunshine;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void loadWeatherData() {
        String location = SunshinePreferences.getPreferredWeatherLocation(this);

    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            String location = strings[0];

            URL weatherResult = NetworkUtils.buildUrl(location);
            try {
                String jsonweatherresponse = NetworkUtils.getResponseFromHttpUrl(weatherResult);
                String[] simpleWeatherJsonData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this, jsonweatherresponse);
                return simpleWeatherJsonData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
