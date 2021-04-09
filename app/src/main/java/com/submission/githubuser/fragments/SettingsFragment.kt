package com.submission.githubuser.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.submission.githubuser.R
import com.submission.githubuser.alarmmanagers.AppAlarmManager

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var alarmManager: AppAlarmManager
    private lateinit var REMINDER : String
    private lateinit var reminderPreference : SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        init()
    }

    private fun init(){
        REMINDER = resources.getString(R.string.key_reminder)
        reminderPreference = findPreference<SwitchPreferenceCompat>(REMINDER) as SwitchPreferenceCompat
        alarmManager = AppAlarmManager()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == REMINDER) {
            reminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)
            if (reminderPreference.isChecked){
                context?.let { alarmManager.setRepeatingAlarm(it, "09:00:00") }
            }else{
                context?.let { alarmManager.cancelAlarm(it) }
            }
        }
    }

}
