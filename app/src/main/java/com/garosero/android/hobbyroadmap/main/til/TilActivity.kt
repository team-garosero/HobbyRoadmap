package com.garosero.android.hobbyroadmap.main.til

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.notice.NoticeActivity
import com.garosero.android.hobbyroadmap.settings.SettingsActivity

class TilActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_til)

        // toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        // set fragment
        val tilItemFragment = TilItemFragment()
        tilItemFragment.arguments = intent.extras
        supportFragmentManager.beginTransaction().replace(R.id.frag, tilItemFragment).commit()
    }

    // set toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.items_toolbar, menu)
        return true
    }

    // toolbar click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item_notice -> {
                val intent = Intent(this, NoticeActivity::class.java)
                startActivity(intent)
            }
            R.id.item_setting -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}