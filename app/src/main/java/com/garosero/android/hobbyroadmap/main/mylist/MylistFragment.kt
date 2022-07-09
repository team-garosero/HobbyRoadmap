package com.garosero.android.hobbyroadmap.main.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.databinding.FragmentMylistBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.MylistViewModel

class MylistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMylistBinding.inflate(inflater, container, false)
        val model = MylistViewModel()

        //binding.recycler.adapter = MylistParentAdapter(model.categoryItemList)
        return binding.root
    }
}