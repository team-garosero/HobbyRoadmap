package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.databinding.FragmentTilBinding
import java.time.LocalDate

class TilFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTilBinding.inflate(inflater, container, false)

        // todo-getWidth screen
        setRecyclerView(binding.recycler, 600)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setRecyclerView(recycler:RecyclerView, width:Int){
        val adapter = TilAdapter(
            focusDay = LocalDate.now(),
            layoutSize = width
        )
        recycler.adapter = adapter
    }
}