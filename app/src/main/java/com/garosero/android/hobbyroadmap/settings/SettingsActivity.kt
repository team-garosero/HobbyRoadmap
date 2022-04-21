package com.garosero.android.hobbyroadmap.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_settings)
        val navController = navHostFragment!!.findNavController()

        // get intent
        val itemId = intent.getIntExtra("menuItem", 0)
        if (itemId==R.id.item_notice){
            navController.popBackStack()
            navController.navigate(R.id.notificationsFragment)
        }

        // event
        binding.ibnBackspace.setOnClickListener{
            finish()
        }
    }
}