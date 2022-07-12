package com.garosero.android.hobbyroadmap.main.adventure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.databinding.FragmentAdventureBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.AdventureViewModel
import com.garosero.android.hobbyroadmap.network.response.UserResponse

class AdventureFragment : Fragment() {
    private val model = AdventureViewModel()
    private lateinit var binding : FragmentAdventureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdventureBinding.inflate(inflater, container, false)

        registerObserver()
        initView()
        return binding.root
    }

    private fun registerObserver(){
        AppApplication.requestSubscribeUser()
        val observer = Observer<UserResponse>{
            model.initData(it)
            initView()
        }

        AppApplication.userData.observe(viewLifecycleOwner, observer)
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