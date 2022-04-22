package com.garosero.android.hobbyroadmap.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.R

class SettingsActivity : AppCompatActivity() {
    lateinit var toolbar:androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        // tool bar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // set fragment
        if (savedInstanceState == null) {
            when (intent.getIntExtra(getString(R.string.menuItem_toolbar), 0)){
                R.id.item_setting -> setFragment("환경설정", SettingsFragment())
                R.id.item_notice -> setFragment("내 알림", NoticeFragment())
            }
        }
    }

    private fun setFragment(title:String, fragment:Fragment){
        supportActionBar?.title = title
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}