package com.garosero.android.hobbyroadmap.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.garosero.android.hobbyroadmap.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}