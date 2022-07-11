package com.garosero.android.hobbyroadmap.main.adventure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.databinding.FragmentAdventureBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.AdventureViewModel

class AdventureFragment : Fragment() {
    private val model = AdventureViewModel()
    private lateinit var binding : FragmentAdventureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdventureBinding.inflate(inflater, container, false)

        initView()
        return binding.root
    }

    private fun initView(){
        with(binding) {
            btnInfo.setOnClickListener {
                with(binding.tvExplain) {
                    visibility = if (visibility == android.view.View.GONE) android.view.View.VISIBLE else android.view.View.GONE
                }
            }

            tvCharacterName.text = model.characterName
            tvCharacterXp.text = "${model.xp}/${model.maxXp}"
            tvLevel.text = "Lv.${model.level}"
            tvExplain.text = model.pageDescription

            pgXp.progress = model.progress

            ivFull.setImageResource(model.fullIvSrc)
            ivPreview.setImageResource(model.prevIvSrc)
        }
    }
}