package com.example.android.sunshine;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        loadWeatherData();

    }

    private void loadWeatherData() {
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
new FetchWeatherTask().execute(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forcast_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int get = item.getItemId();
        switch (get){
            case R.id.refresh :
                //refresh
                Toast.makeText(MainActivity.this, "Refresh", Toast.LENGTH_SHORT).show();
break;
            
        }
        return super.onOptionsItemSelected(item);
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

        @Override
        protected void onPostExecute(String[] weatherData) {
            if(weatherData != null){
                for(String weatherString : weatherData){
                    mWeatherTextView.append((weatherString) + "\n\n\n");

                }
            }
        }
    }

}
