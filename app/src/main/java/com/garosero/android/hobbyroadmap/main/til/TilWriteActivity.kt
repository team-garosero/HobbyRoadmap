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

        // fragment
        val tilItem = TilItem()

        with(tilItem){
            uid = FirebaseAuth.getInstance().uid.toString()
            date = DateHelper.getTodayString()
            LClassId = getIntentData(LCLASSID)
            MClassId = getIntentData(MCLASSID)
            SClassId = getIntentData(SCLASSID)
            subClassId = getIntentData(SUBCLASSID)
            modulePath = "${LClassId} ${MClassId} ${SClassId} ${subClassId}"
            moduleName = getIntentData(MODULENAME)
            moduleDesc = getIntentData(MODULEDESC)
        }

        changeFragment(TilItemFragment(TilItemFragment.TilWriteMode.CREATE, tilItem))

    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

    private fun getIntentData(key : String) : String{
        return if (intent.hasExtra(key)) {
            return intent.getStringExtra(key).toString()
        } else ""
    }

    companion object {
        val LCLASSID = "LCLASSID"
        val MCLASSID = "MCLASSID"
        val SCLASSID = "SCLASSID"
        val SUBCLASSID = "SUBCLASSID"

        val MODULENAME = "MODULENAME"
        val MODULEDESC = "MODULEDESC"
    }
}