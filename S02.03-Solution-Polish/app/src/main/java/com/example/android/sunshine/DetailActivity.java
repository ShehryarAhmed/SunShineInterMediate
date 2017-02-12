package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
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
}

