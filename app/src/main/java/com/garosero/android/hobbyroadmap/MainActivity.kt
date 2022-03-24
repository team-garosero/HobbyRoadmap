package com.garosero.android.hobbyroadmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.main.adventure.AdventureFragment
import com.garosero.android.hobbyroadmap.main.home.HomeFragment
import com.garosero.android.hobbyroadmap.main.mylist.MylistFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init frag
        replaceFragment(HomeFragment())

        // bottom navi
        val navView = findViewById<BottomNavigationView>(R.id.navview_main)
        navView.run {
        setOnItemSelectedListener {
            replaceFragment(
                when (it.itemId){
                    R.id.item_home -> HomeFragment()
                    R.id.item_adventure -> AdventureFragment()
                    else -> MylistFragment()
                }
            )
            true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .commit()
    }
}