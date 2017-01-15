package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by android on 1/15/2017.
 */

public class DetailActivity extends AppCompatActivity{
    public static final String FORCAST_SHARE_HASHTAG = "#SUNSHINE";

    private String mForecast;
    private TextView mWeatherDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        mWeatherDisplay = (TextView) findViewById(R.id.tv_display_weather);

        Intent intentTheStartedThisActivity = getIntent();

        if(intentTheStartedThisActivity != null){
        if(intentTheStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            mForecast = intentTheStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mWeatherDisplay.setText(mForecast);

        }
    }}

    private Intent createShareForecastIntent(){
        Intent shareIntent = ShareCompat.IntentBuilder.from(this).setType("text/plain").setText(mForecast + FORCAST_SHARE_HASHTAG).getIntent();

        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }
}
