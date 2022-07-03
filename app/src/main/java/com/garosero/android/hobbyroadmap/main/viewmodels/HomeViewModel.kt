package com.garosero.android.hobbyroadmap.main.viewmodels

import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.main.helper.CastHelper

class HomeViewModel {

    private val categoryIdList = AppApplication.categoryData.keys
    var roadmapList = mutableListOf<RoadmapItem>()
    var castHelper = CastHelper()

    init {
        initRoadmapList()
    }


    private fun initRoadmapList() {
        roadmapList.clear()

        categoryIdList.forEach {
            with(AppApplication.categoryData) {
                val categoryResp = get(it)
                val roadmapRespList = categoryResp?.roadmapMap

                roadmapRespList?.forEach {

                    val roadmapItem = castHelper.getRoadmapItem(categoryResp.categoryID, it.key)
                    roadmapList.add(roadmapItem)
                }
            }
        }
    }
}