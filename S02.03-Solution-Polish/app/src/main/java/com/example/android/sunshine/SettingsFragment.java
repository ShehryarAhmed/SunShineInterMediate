package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import static com.example.android.sunshine.R.style.Preference;

/**
 * Created by android on 1/18/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
    private void setPreferenceSummary(android.support.v7.preference.Preference preference,Object value){
        String stringvalue =value.toString();
        String key = preference.getKey();

        if()

    }
}
