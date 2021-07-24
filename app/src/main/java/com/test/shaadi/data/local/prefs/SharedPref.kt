package com.test.shaadi.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.test.shaadi.Shaadi

object SharedPref {

    private val SHARED_PREF_APP_SETTINGS = "AppSettings"

    private val PREF_KEY_IS_FIRST_TIME = "isFirstTime"


    private val preferences: SharedPreferences = Shaadi.applicationContext().getSharedPreferences(
        SHARED_PREF_APP_SETTINGS,
        Context.MODE_PRIVATE
    )
    var isFirstTime: Boolean
        get() = preferences.getBoolean(PREF_KEY_IS_FIRST_TIME, true)
        set(value) = preferences.edit().putBoolean(PREF_KEY_IS_FIRST_TIME, value).apply()
}