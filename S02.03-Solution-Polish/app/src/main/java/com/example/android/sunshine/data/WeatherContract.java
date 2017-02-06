package com.example.android.sunshine.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.Settings;

import com.example.android.sunshine.utilities.SunshineDateUtils;

/**
 * Created by android on 1/22/2017.
 */

public class WeatherContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine";
    public static final Uri Base_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_WEATHER = "weather";


    public static final class WeatherEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Base_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

        public static final String TABLE_NAME = "weather";

        public static final String CoLUMN_DATE = "date";

        public static final String COLUMN_WEATHER_ID = "weather_id";

        public static final String COLUMN_MIN_TEMP = "min";

        public static final String COLUMN_MAX_TEMP = "max";

        public static final String COLUMN_HUMIDTIY = "humidity";

        public static final String COLUMN_PRESSURE = "pressure";

        public static final String  COLUMN_WING_SPEED = "wind";

        public static final String COLUMN_DEGREES = "degrees";

        public static Uri buildWeatherUriWithDate(long date){
            return CONTENT_URI.buildUpon().appendPath(Long.toString(date)).build();
        }
        public static String getSqlSelectForTodayONWards(){
            long normalizedUtcNow = SunshineDateUtils.normalizeDate(System.currentTimeMillis());
            return WeatherEntry.CoLUMN_DATE + "  >= " +normalizedUtcNow;
        }

    }

}
