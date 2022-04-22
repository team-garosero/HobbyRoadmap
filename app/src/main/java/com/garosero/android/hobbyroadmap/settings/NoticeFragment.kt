package com.garosero.android.hobbyroadmap.settings

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.data.NoticeItem
import com.garosero.android.hobbyroadmap.databinding.FragmentNoticeBinding
import java.time.LocalDate

class NoticeFragment : Fragment() {
    val dataset = mutableListOf<NoticeItem>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentNoticeBinding.inflate(layoutInflater)
        binding.apply {
            // todo : add datas
            dataset.apply {
                if (isEmpty()){
                    val today = LocalDate.now()
                    for (a in 1..5){
                        add(NoticeItem(title = "알림 내용${a+1}", date = today.minusDays(a.toLong())))
                   }
                }
            }
            binding.recycler.adapter = NoticeAdapter(dataset)
        }

        return binding.root
    }
}