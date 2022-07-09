package com.garosero.android.hobbyroadmap.main.til

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.ActivityTilWriteBinding
import com.garosero.android.hobbyroadmap.main.helper.DateHelper
import com.google.firebase.auth.FirebaseAuth

class TilWriteActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_til_write)
        val binding = ActivityTilWriteBinding.inflate(layoutInflater)

        // tool bar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // fragment
        val tilItem = TilItem()
        tilItem.uid = FirebaseAuth.getInstance().uid.toString()
        tilItem.date = DateHelper.getTodayString()

        // todo : fill

        changeFragment(TilItemFragment(tilItem))

    }

    fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_til, fragment).commit()
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