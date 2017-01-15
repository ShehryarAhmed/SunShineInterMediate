package com.example.android.sunshine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by android on 1/15/2017.
 */

public class DetailActivity extends AppCompatActivity{
    public static final String FORCAST_SHARE_HASHTAG = "#SUNSHINE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
    }

}
