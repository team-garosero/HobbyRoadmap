package com.garosero.android.hobbyroadmap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.garosero.android.hobbyroadmap.databinding.ActivityMainBinding
import com.garosero.android.hobbyroadmap.syllabus.SyllabusActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set context
        mcontext = binding.root.context

        // navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host)
        val navController = navHostFragment!!.findNavController()
        binding.navView.setupWithNavController(navController)
    }

    // todo:change
    companion object {
        lateinit var mcontext:Context

        fun gotoSyllabusActivity() {
            if (mcontext==null) return
            val intent = Intent(mcontext, SyllabusActivity::class.java)
            mcontext.startActivity(intent)
        }
    }
}