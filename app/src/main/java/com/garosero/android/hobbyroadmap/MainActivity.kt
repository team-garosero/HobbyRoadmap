package com.garosero.android.hobbyroadmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.main.AdventureFragment
import com.garosero.android.hobbyroadmap.main.HomeFragment
import com.garosero.android.hobbyroadmap.main.MylistFragment
import com.garosero.android.hobbyroadmap.main.TilFragment
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
                    R.id.item_til -> TilFragment()
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