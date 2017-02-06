package com.example.android.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by android on 1/22/2017.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather.db";

    public static final int DATABASE_VERSION = 3;

    public WeatherDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE =
                "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " ("+
                WeatherContract.WeatherEntry._ID + " INTEGER PREIMARY KEY AUTOINCREMENT," +
                WeatherContract.WeatherEntry.CoLUMN_DATE + " INTEGER NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_WEATHER_ID + "INTEGER NOT NULL," +
                WeatherContract.WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, "+
                WeatherContract.WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_HUMIDTIY + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_WING_SPEED + " REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL, " +
                        " UNIQUE (" + WeatherContract.WeatherEntry.CoLUMN_DATE + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
