package com.example.android.sunshine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.SunshineDateUtils;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;

/**
 * Created by android on 1/15/2017.
 */

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
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

    private String mForecastSummary;

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

        if (mUri == null) {
            throw new NullPointerException("URI for DetailActivity cannot be null");
        }
        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            openSettingActivity();
        }
        if (id == R.id.action_share) {
            Intent shareIntent = createShareForecastIntent();
            startActivity(shareIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettingActivity() {
        Intent intent = new Intent(DetailActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecast + FORCAST_SHARE_HASHTAG)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            //          COMPLETED (23) If the loader requested is our detail loader, return the appropriate CursorLoader
            case ID_DETAIL_LOADER:

                return new CursorLoader(this,
                        mUri,
                        WEATHER_DETAIL_PROJECTION,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //COMPLETED (25) Check before doing anything that the Cursor has valid data
               /*
         * Before we bind the data to the UI that will display that data, we need to check the
         * cursor to make sure we have the results that we are expecting. In order to do that, we
         * check to make sure the cursor is not null and then we call moveToFirst on the cursor.
         * Although it may not seem obvious at first, moveToFirst will return true if it contains
         * a valid first row of data.
         *
         * If we have valid data, we want to continue on to bind that data to the UI. If we don't
         * have any data to bind, we just return from this method.
         */
                        boolean cursorHasValidData = false;
                if (data != null && data.moveToFirst()) {
                       /* We have valid data, continue on to bind the data to the UI */
                                cursorHasValidData = true;
                    }

                        if (!cursorHasValidData) {
                        /* No data to display, simply return and do nothing */
                                return;
                    }

                //      COMPLETED (26) Display a readable data string
                                /****************
                                 * Weather Date *
                                 ****************/
                                /*
        * Read the date from the cursor. It is important to note that the date from the cursor
        * is the same date from the weather SQL table. The date that is stored is a GMT
         * representation at midnight of the date when the weather information was loaded for.
         *
         * When displaying this date, one must add the GMT offset (in milliseconds) to acquire
         * the date representation for the local date in local time.
         * SunshineDateUtils#getFriendlyDateString takes care of this for us.
         */
                                               long localDateMidnightGmt = data.getLong(INDEX_WEATHER_DATE);
                String dateText = SunshineDateUtils.getFriendlyDateString(this, localDateMidnightGmt, true);

                        mDateView.setText(dateText);

                //      COMPLETED (27) Display the weather description (using SunshineWeatherUtils)
                                /***********************
                                 * Weather Description *
                                 ***********************/
                                /* Read weather condition ID from the cursor (ID provided by Open Weather Map) */
                                int weatherId = data.getInt(INDEX_WEATHER_CONDITION_ID);
                /* Use the weatherId to obtain the proper description */
                        String description = SunshineWeatherUtils.getStringForWeatherCondition(this, weatherId);

                        /* Set the text */
                                mDescriptionView.setText(description);

                //      COMPLETED (28) Display the high temperature
                                /**************************
                                 * High (max) temperature *
                                  **************************/
                                        /* Read high temperature from the cursor (in degrees celsius) */
                                                double highInCelsius = data.getDouble(INDEX_WEATHER_MAX_TEMP);
                /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
                       String highString = SunshineWeatherUtils.formatTemperature(this, highInCelsius);

                      /* Set the text */
                                mHighTemperatureView.setText(highString);

                //      COMPLETED (29) Display the low temperature
                       /*************************
                        * Low (min) temperature *
                        *************************/
                        /* Read low temperature from the cursor (in degrees celsius) */
                       double lowInCelsius = data.getDouble(INDEX_WEATHER_MIN_TEMP);
                /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
                       String lowString = SunshineWeatherUtils.formatTemperature(this, lowInCelsius);

               /* Set the text */
               mLowTemperatureView.setText(lowString);

        //      COMPLETED (30) Display the humidity
                /************
                * Humidity *
                ************/
                /* Read humidity from the cursor */
               float humidity = data.getFloat(INDEX_WEATHER_HUMIDITY);
                String humidityString = getString(R.string.format_humidity, humidity);

               /* Set the text */
                mHumidityView.setText(humidityString);

        //      COMPLETED (31) Display the wind speed and direction
                /****************************
                 * Wind speed and direction *
                ****************************/
                /* Read wind speed (in MPH) and direction (in compass degrees) from the cursor  */
                float windSpeed = data.getFloat(INDEX_WEATHER_WIND_SPEED);
                float windDirection = data.getFloat(INDEX_WEATHER_DEGREES);
                String windString = SunshineWeatherUtils.getFormattedWind(this, windSpeed, windDirection);

               /* Set the text */
               mWindView.setText(windString);

        //      COMPLETED (32) Display the pressure
                /************
                * Pressure *
                ************/
               /* Read pressure from the cursor */
               float pressure = data.getFloat(INDEX_WEATHER_PRESSURE);

                /*
         * Format the pressure text using string resources. The reason we directly access
         * resources using getString rather than using a method from SunshineWeatherUtils as
         * we have for other data displayed in this Activity is because there is no
         * additional logic that needs to be considered in order to properly display the
         * pressure.
         */
        String pressureString = getString(R.string.format_pressure, pressure);
       /* Set the text */
        mPressureView.setText(pressureString);
//      COMPLETED (33) Store a forecast summary in mForecastSummary
       /* Store the forecast summary String in our forecast summary field to share later */
       mForecastSummary = String.format("%s - %s - %s/%s",
            dateText, description, highString, lowString);
 }

//  COMPLETED (34) Override onLoaderReset, but don't do anything in it yet
     /**
 * Called when a previously created loader is being reset, thus making its data unavailable.
     * The application should at this point remove any references it has to the Loader's data.
  * Since we don't store any of this cursor's data, there are no references we need to remove.
    *
 * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

