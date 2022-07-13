package com.garosero.android.hobbyroadmap.main.searchVer2

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.databinding.FragmentSearchVer2Binding
import com.garosero.android.hobbyroadmap.main.helper.SQLiteSearchHelper
import com.garosero.android.hobbyroadmap.syllabus.SyllabusActivity

@RequiresApi(Build.VERSION_CODES.O)
class SearchVer2Fragment : Fragment(){
    private val adpter = SearchListVer2Adapter()
    private val TAG = "SearchVer2Fragment"
    private var searchHelper : SQLiteSearchHelper? = null
    private var binding : FragmentSearchVer2Binding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchVer2Binding.inflate(inflater, container, false)
        searchHelper = SQLiteSearchHelper(requireContext())

        initRecyclerView()
        initView()

        return binding!!.root
    }

    // ==================================== fetch recyclerview data ================================

    private fun showLClass(){
        adpter.searchType = SearchType.lClass
        adpter.submitData(searchHelper!!.getLPathList())

        with(binding!!){
            checkRadioBtn(rbLclass)

            tvPath.text = "대분류를 선택해주세요"
        }

    }

    private fun showMClass(classPath: ClassPath){

        adpter.searchType = SearchType.mClass
        adpter.submitData(searchHelper!!.getMPathList(classPath))

        with(binding!!){
            checkRadioBtn(rbMclass)

            tvPath.text = classPath.lClassNm
        }
    }

    private fun showSClass(classPath: ClassPath){

        adpter.searchType = SearchType.sClass
        adpter.submitData(searchHelper!!.getSPathList(classPath))

        with(binding!!){
            checkRadioBtn(rbSclass)

            tvPath.text = "${classPath.lClassNm} > ${classPath.mClassNm}"
        }
    }

    private fun showSubClass(classPath: ClassPath){

        adpter.searchType = SearchType.subClass
        adpter.submitData(searchHelper!!.getSubPathList(classPath))

        with(binding!!){
            checkRadioBtn(rbSubclass)

            tvPath.text = "${classPath.lClassNm} > ${classPath.mClassNm} > ${classPath.sClassNm}"
        }
    }


    //========================================= init view ==========================================

    private fun initRecyclerView(){
        binding!!.recyclerList.adapter = adpter
        showLClass()

        adpter.onClickCallback = object : SearchListVer2Adapter.OnClickCallback{
            override fun onClick(classPath: ClassPath) {
                when (adpter.searchType){
                    SearchType.lClass -> {
                        showMClass(classPath.copy())
                    }
                    SearchType.mClass -> {
                        showSClass(classPath.copy())
                    }
                    SearchType.sClass -> {
                        showSubClass(classPath.copy())
                    }
                    SearchType.subClass -> {
                        val intent = Intent(requireActivity(), SyllabusActivity::class.java)
                        intent.putExtra("classCd", ArrayList(classPath.getClassCD().split(" ")))
                        Log.e(TAG, classPath.getClassCD())
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun initView(){
        with(binding!!){

            // click listener
            rbLclass.setOnClickListener{showLClass()}
            rbMclass.setOnClickListener{
                val first = adpter.dataset.firstOrNull()
                if (adpter.searchType == SearchType.lClass) return@setOnClickListener
                if (first == null) return@setOnClickListener
                if (first.lClassId == null) return@setOnClickListener

                val classPath = ClassPath()
                classPath.lClassId = first.lClassId
                classPath.lClassNm = first.lClassNm

                showMClass(classPath)
            }
            rbSclass.setOnClickListener{
                val first = adpter.dataset.firstOrNull()
                if (adpter.searchType == SearchType.lClass) return@setOnClickListener
                if (adpter.searchType == SearchType.mClass) return@setOnClickListener
                if (first == null) return@setOnClickListener
                if (first.lClassId == null) return@setOnClickListener
                if (first.mClassId == null) return@setOnClickListener

                val classPath = ClassPath()
                classPath.lClassId = first.lClassId
                classPath.lClassNm = first.lClassNm
                classPath.mClassId = first.mClassId
                classPath.mClassNm = first.mClassNm

                showSClass(classPath)
            }
        }
    }


    private fun checkRadioBtn(textView: TextView){
        with(binding!!){
            rbLclass.setTextColor(Color.parseColor("#3E3E3E"))
            rbMclass.setTextColor(Color.parseColor("#3E3E3E"))
            rbSclass.setTextColor(Color.parseColor("#3E3E3E"))
            rbSubclass.setTextColor(Color.parseColor("#3E3E3E"))

            rbMclass.setTextColor(Color.parseColor("#3E3E3E"))
            rbSclass.setTextColor(Color.parseColor("#3E3E3E"))
            rbSubclass.setTextColor(Color.parseColor("#3E3E3E"))

            textView.setTextColor(Color.parseColor("#3249BB"))
        }
    }
}