package com.garosero.android.hobbyroadmap.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.ActivityMainBinding
import com.garosero.android.hobbyroadmap.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        setSupportActionBar(binding.toolbar)

        // navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main)
        val navController = navHostFragment!!.findNavController()
        binding.navView.setupWithNavController(navController)
    }

    // set toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.items_toolbar, menu)
        return true
    }

    // toolbar click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra("menuItem", item.itemId)
        startActivity(intent)
        return true
    }
}