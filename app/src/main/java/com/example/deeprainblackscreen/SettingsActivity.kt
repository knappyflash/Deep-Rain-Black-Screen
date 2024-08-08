package com.example.deeprainblackscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.activity.OnBackPressedCallback
import android.view.MenuItem
import android.widget.Toast
import androidx.preference.SwitchPreferenceCompat
import android.graphics.Color
import android.widget.TextView
import androidx.preference.PreferenceViewHolder

class SettingsActivity : AppCompatActivity() {

    private lateinit var mySwitchPreferenceCompat: SwitchPreferenceCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

//        Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show()

        // Handle the back button press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button click
                finish()
            }
        })

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the back button click
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val showClockPreference = findPreference<SwitchPreferenceCompat>("show_clock")



        }
    }
}