package com.garosero.android.hobbyroadmap.notice

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.NoticeItem
import com.garosero.android.hobbyroadmap.databinding.ActivityNoticeBinding
import java.time.LocalDate

class NoticeActivity : AppCompatActivity() {
    val dataset = mutableListOf<NoticeItem>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tool bar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // recycler view
        binding.apply {
            // todo : add datas
            dataset.apply {
                if (isEmpty()){
                    val today = LocalDate.now()
                    for (a in 1..20){
                        add(NoticeItem(title = "알림 내용${a+1}", date = today.minusDays(a.toLong())))
                    }
                }
            }
            binding.recycler.adapter = NoticeAdapter(dataset)
        }
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