package com.garosero.android.hobbyroadmap.main.viewmodels

import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.RoadmapItem

class HomeViewModel {

    private val categoryIdList = AppApplication.categoryData.keys
    var roadmapList = mutableListOf<RoadmapItem>()

    init {
        cast_to_categoryItem()
    }

    /**
     * Category 가 한개라, 따로 구분하지 않고, 모든 Roadmap을 roadmapList에 넣어둠
     * 추후에 카테고리가 늘어나면, 분리해서 저장해야 함.
     */
    private fun cast_to_categoryItem() {
        roadmapList.clear()

        categoryIdList.forEach {
            with(AppApplication.categoryData) {
                val categoryResp = get(it)
                //val categoryTitle = categoryResp?.title
                val roadmapRespList = categoryResp?.roadmapMap

                roadmapRespList?.forEach {
                    val roadmapItem = RoadmapItem()
                    roadmapItem.title = it.value.title
                    roadmapItem.desc = it.value.desc

                    // todo :  fill other data
                    roadmapList.add(roadmapItem)
                }
            }
        }
    }
}