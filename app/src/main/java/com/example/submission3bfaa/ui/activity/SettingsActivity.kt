package com.example.submission3bfaa.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission3bfaa.R
import com.example.submission3bfaa.ui.fragment.SettingFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        title = getString(R.string.setting)

        loadFragment(fragment = SettingFragment())
    }

    private fun loadFragment(fragment: SettingFragment):Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_settings, fragment)
            .commit()
        return true
    }
}