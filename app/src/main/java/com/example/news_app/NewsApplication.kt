package com.example.news_app

import android.app.Application
import androidx.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication :Application(){

    companion object{
        lateinit var country:String
    }
    override fun onCreate() {
        super.onCreate()
        val preferences=PreferenceManager.getDefaultSharedPreferences(this)
        country=preferences.getString(getString(R.string.countryKey),"eg")?:"eg"
    }
}