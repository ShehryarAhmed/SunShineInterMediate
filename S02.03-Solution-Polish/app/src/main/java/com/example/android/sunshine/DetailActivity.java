package com.example.android.sunshine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.sunshine.data.WeatherContract;

/**
 * Created by android on 1/15/2017.
 */

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String FORCAST_SHARE_HASHTAG = "#SUNSHINE";




    public static final String[] WEATHER_DETAIL_PROJECTION = {
                        WeatherContract.WeatherEntry.COLUMN_DATE,
                        WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
                        WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
                        WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
                        WeatherContract.WeatherEntry.COLUMN_PRESSURE,
                        WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
                        WeatherContract.WeatherEntry.COLUMN_DEGREES,
                        WeatherContract.WeatherEntry.COLUMN_WEATHER_ID
               };

        public static final int INDEX_WEATHER_DATE = 0;
        public static final int INDEX_WEATHER_MAX_TEMP = 1;
        public static final int INDEX_WEATHER_MIN_TEMP = 2;
        public static final int INDEX_WEATHER_HUMIDITY = 3;
        public static final int INDEX_WEATHER_PRESSURE = 4;
        public static final int INDEX_WEATHER_WIND_SPEED = 5;
        public static final int INDEX_WEATHER_DEGREES = 6;
        public static final int INDEX_WEATHER_CONDITION_ID = 7;

    private static final int ID_DETAIL_LOADER = 353;

    private String mForecast;

    private Uri mUri;

        private TextView mDateView;
        private TextView mDescriptionView;
        private TextView mHighTemperatureView;
       private TextView mLowTemperatureView;
        private TextView mHumidityView;
        private TextView mWindView;
        private TextView mPressureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDateView = (TextView) findViewById(R.id.date);
                mDescriptionView = (TextView) findViewById(R.id.weather_description);
                mHighTemperatureView = (TextView) findViewById(R.id.high_temperature);
                mLowTemperatureView = (TextView) findViewById(R.id.low_temperature);
                mHumidityView = (TextView) findViewById(R.id.humidity);
                mWindView = (TextView) findViewById(R.id.wind);
                mPressureView = (TextView) findViewById(R.id.pressure);
        mUri = getIntent().getData();

        
}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.detail,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_setting){
            openSettingActivity();
        }
        if(id == R.id.action_share){
            Intent shareIntent = createShareForecastIntent();
            startActivity(shareIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void openSettingActivity(){
        Intent intent = new Intent(DetailActivity.this,SettingActivity.class);
        startActivity(intent);
    }
    private Intent createShareForecastIntent(){
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecast + FORCAST_SHARE_HASHTAG)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

