package com.submission.githubuser.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.submission.githubuser.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}