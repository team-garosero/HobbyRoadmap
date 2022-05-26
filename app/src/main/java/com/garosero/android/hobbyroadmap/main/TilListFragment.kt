package com.garosero.android.hobbyroadmap.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilListBinding
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilListFragment : Fragment() {
    private val model = TilParentFragment.model

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTilListBinding.inflate(layoutInflater)
        val view = binding.root

        // recycler view
        val adapter = TilListAdapter(model)
        binding.recyclerList.adapter = adapter
        adapter.setOnItemClickListener(object : TilListAdapter.OnItemClickListener{
            override fun onItemClick(item: TilItem) {
                view.findNavController().navigate(R.id.action_tilListFragment_to_tilItemFragment)
            }
        })

        // live data
        val observer = Observer<LocalDate>{
            adapter.submitData(it)
            adapter.notifyDataSetChanged()
        }
        model.focusDate.observe(viewLifecycleOwner, observer)

        return view
    }
}