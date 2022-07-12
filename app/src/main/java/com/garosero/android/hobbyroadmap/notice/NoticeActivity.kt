package com.garosero.android.hobbyroadmap.notice

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.garosero.android.hobbyroadmap.data.NoticeItem
import com.garosero.android.hobbyroadmap.databinding.ActivityNoticeBinding
import java.time.LocalDate

class NoticeActivity : AppCompatActivity() {
    val dataset = mutableListOf<NoticeItem>()
    private lateinit var noticeAdapter: NoticeAdapter

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
            dataset.apply {
                if (isEmpty()){
                    val today = LocalDate.now()
                    add(NoticeItem(title = "3일 동안 접속하지 않았습니다.", date = today.minusDays(3)))
                    add(NoticeItem(title = "로드맵에 새로운 글이 등록되었습니다.", date = today.minusDays(5)))
                }
            }
            noticeAdapter = NoticeAdapter(dataset)
            recycler.adapter = noticeAdapter
            recycler.itemAnimator = null
        }

        // add touch listener
        val itemTouchCallback = MyItemTouchCallback(noticeAdapter, baseContext, resources)
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.recycler)
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