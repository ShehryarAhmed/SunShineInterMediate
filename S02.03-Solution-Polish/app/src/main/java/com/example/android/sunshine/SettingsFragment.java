package com.example.android.sunshine;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.data.WeatherContract;

import static com.example.android.sunshine.R.style.Preference;

/**
 * Created by android on 1/18/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Activity activity = getActivity();
        if(key.equals(getString(R.string.pref_location_key))){
            SunshinePreferences.resetLocationCoordinates(activity);
        }
        else if(key.equals(getString(R.string.pref_units_key))) {
            activity.getContentResolver().notifyChange(WeatherContract.WeatherEntry.CONTENT_URI,null);
        }

        android.support.v7.preference.Preference preference = findPreference(key);
        if(null != preference){
            if(!(preference instanceof CheckBoxPreference)){
                setPreferenceSummary(preference,sharedPreferences.getString(key,""));
            }
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);

        SharedPreferences sharedPreferences =getPreferenceScreen().getSharedPreferences();

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for (int i =0; i< count; i++){
            android.support.v7.preference.Preference p = preferenceScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){
                String value= sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummary(p,value);
                }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


    private void setPreferenceSummary(android.support.v7.preference.Preference preference, Object value){
        String stringvalue =value.toString();
        String key = preference.getKey();
        if(preference instanceof ListPreference){

            ListPreference listPreference = (ListPreference) preference;
            int prefindex =listPreference.findIndexOfValue(stringvalue);
            if(prefindex >= 0){
                preference.setSummary(listPreference.getEntries()[prefindex]);

            }
            else {
                preference.setSummary(stringvalue);
            }
        }

    }

}
