package com.example.news_app.features.ui.main

import android.app.Activity
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.news_app.BuildConfig
import com.example.news_app.R
import com.example.news_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener{


    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppConfiguration()
        setupNavigation()

    }

    private fun setupAppConfiguration() {
        preferences=PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkTheme=preferences.getBoolean(getString(R.string.themeKey),false)
        setAppTheme(isDarkTheme)
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    private fun setAppTheme(isDarkTheme:Boolean) {
        if(isDarkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        sharedPreferences?.let {
            if(key!=null){
                if(key==getString(R.string.themeKey)){
                    val isDartTheme=sharedPreferences.getBoolean(key,false)
                    setAppTheme(isDartTheme)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}