package com.garosero.android.hobbyroadmap.main.til

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilItemBinding

@RequiresApi(Build.VERSION_CODES.O)
class TilItemFragment(val tilItem : TilItem) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTilItemBinding.inflate(layoutInflater)
        val view = binding.root

        // init view
        with(binding){
            tvDate.text = tilItem.date
            tvTitle.text = tilItem.moduleName
            tvSubtitle.text = tilItem.moduleDesc
            etContent.setText(tilItem.content)
        }

        // onclick
        with(binding){
            btnDelete.setOnClickListener {
                moveToList()

            }
            btnSave.setOnClickListener {
                moveToList()

            }
        }

        return view
    }

    private fun moveToList(){
        val parent : TilParentFragment  = parentFragment as TilParentFragment
        parent.changeFragment(TilListFragment())
    }
}