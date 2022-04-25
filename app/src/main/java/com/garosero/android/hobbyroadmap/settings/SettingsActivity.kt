package com.garosero.android.hobbyroadmap.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tool bar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title=""

        // set fragment
        if (savedInstanceState == null) {
            when (intent.getIntExtra(getString(R.string.menuItem_toolbar), 0)){
                R.id.item_setting -> setFragment(getString(R.string.title_fragment_settings), SettingsFragment())
                R.id.item_notice -> setFragment(getString(R.string.title_fragment_notice), NoticeFragment())
            }
        }
    }

    private fun setFragment(title:String, fragment:Fragment){
        binding.tvPageTitle.text = title
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