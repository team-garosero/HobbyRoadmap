package com.garosero.android.hobbyroadmap.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.garosero.android.hobbyroadmap.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentNotificationsBinding.inflate(layoutInflater)
        return binding.root
    }
}