package com.example.android.sunshine.data;

import android.provider.BaseColumns;

/**
 * Created by android on 1/22/2017.
 */

public class WeatherContract {
    public static final class WeatherEntry implements BaseColumns{
        public static final String TABLE_NAME = "weather";

        public static final String CoLUMN_DATE = "date";

        public static final String COLUMN_WEATHER_ID = "weather_id";

        public static final String COLUMN_MIN_TEMP = "min";

        public static final String COLUMN_MAX_TEMP = "max";

        public static final String COLUMN_HUMIDTIY = "humidity";

        public static final String COLUMN_PRESSURE = "pressure";

        public static final String  COLUMN_WING_SPEDD = "wind";

        public static final String COLUMN_DEGREES = "degrees";

    }
}
