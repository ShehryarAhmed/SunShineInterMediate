package com.example.android.sunshine.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.sunshine.utilities.SunshineDateUtils;

/**
 * Created by android on 2/6/2017.
 */

public class WeatherProvider extends ContentProvider {

    public static final int CODE_WEATHER = 100;

    public static final int CODE_WEATHER_DATE = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private WeatherDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WeatherContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,WeatherContract.PATH_WEATHER,CODE_WEATHER);
        matcher.addURI(authority,WeatherContract.PATH_WEATHER,CODE_WEATHER_DATE);
        return matcher;
    }



    @Override
    public boolean onCreate() {
    mOpenHelper = new WeatherDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case CODE_WEATHER_DATE:{
                String normalizedUtcDateString = uri.getLastPathSegment();
                String[] selectionArgument = new String[]{normalizedUtcDateString};
                cursor = mOpenHelper.getReadableDatabase().query(
                        WeatherContract.WeatherEntry.TABLE_NAME,
                        projection,
                        WeatherContract.WeatherEntry.CoLUMN_DATE+" = ? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_WEATHER:{
                cursor = mOpenHelper.getReadableDatabase().query(
                        WeatherContract.WeatherEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
               throw new UnsupportedOperationException("UnknowUri " + uri);

       }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case CODE_WEATHER:
                db.beginTransaction();
                int rowInserted = 0;
                try{
                    for(ContentValues value : values){
                       long weatherData =
                               value.getAsLong(WeatherContract.WeatherEntry.CoLUMN_DATE);
                        if(SunshineDateUtils.isDateNormalized(weatherData)){
                            throw  new IllegalArgumentException("Date Must be normalized To Insert");
                        }
                        long _id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME,null,value);
                        if(_id != -1){
                            rowInserted++;
                        }
                        }
                    db.setTransactionSuccessful();
                }
                finally {
                    db.endTransaction();

                }
                if(rowInserted > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return rowInserted;

            default:
                return super.bulkInsert(uri,values);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
