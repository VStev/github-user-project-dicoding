package com.submission.githubuser.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.submission.githubuser.R
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(), TimePickerFragment.DialogTimeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        // Set text dari textview berdasarkan tag

    }
}