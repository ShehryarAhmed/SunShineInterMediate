package com.example.android.sunshine.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by android on 12/27/2016.
 */
    public final class NetworkUtils {

        private static final String TAG = NetworkUtils.class.getSimpleName();

        private static final String DYNAMIC_WEATHER_URL =
                "https://andfun-weather.udacity.com/weather";

        private static final String STATIC_WEATHER_URL =
                "https://andfun-weather.udacity.com/staticweather";

        private static final String FORECAST_BASE_URL = STATIC_WEATHER_URL;

    /*
     * NOTE: These values only effect responses from OpenWeatherMap, NOT from the fake weather
     * server. They are simply here to allow us to teach you how to build a URL if you were to use
     * a real API.If you want to connect your app to OpenWeatherMap's API, feel free to! However,
     * we are not going to show you how to do so in this course.
     */

        /* The format we want our API to return */
        private static final String format = "json";
        /* The units we want our API to return */
        private static final String units = "metric";
        /* The number of days we want our API to return */
        private static final int numDays = 14;

        final static String QUERY_PARAM = "q";
        final static String LAT_PARAM = "lat";
        final static String LON_PARAM = "lon";
        final static String FORMAT_PARAM = "mode";
        final static String UNITS_PARAM = "units";
        final static String DAYS_PARAM = "cnt";

        /**
         * Builds the URL used to talk to the weather server using a location. This location is based
         * on the query capabilities of the weather provider that we are using.
         *
         * @param locationQuery The location that will be queried for.
         * @return The URL to use to query the weather server.
         */
        public static URL buildUrl(String locationQuery) {
            // TODO (1) Fix this method to return the URL used to query Open Weather Map's API
            return null;
        }

        /**
         * Builds the URL used to talk to the weather server using latitude and longitude of a
         * location.
         *
         * @param lat The latitude of the location
         * @param lon The longitude of the location
         * @return The Url to use to query the weather server.
         */
        public static URL buildUrl(Double lat, Double lon) {
            /** This will be implemented in a future lesson **/
            return null;
        }

        /**
         * This method returns the entire result from the HTTP response.
         *
         * @param url The URL to fetch the HTTP response from.
         * @return The contents of the HTTP response.
         * @throws IOException Related to network and stream reading
         */
        public static String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }
    }
