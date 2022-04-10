package com.garosero.android.hobbyroadmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.garosero.android.hobbyroadmap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host)
        val navController = navHostFragment!!.findNavController()
        binding.navView.setupWithNavController(navController)
    }
}