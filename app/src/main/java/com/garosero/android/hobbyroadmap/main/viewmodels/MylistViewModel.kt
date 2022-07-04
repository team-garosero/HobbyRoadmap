package com.garosero.android.hobbyroadmap.main.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.CategoryItem
import com.garosero.android.hobbyroadmap.data.RoadmapItem

class MylistViewModel : ViewModel() {
    private val TAG = "MylistViewModel"

    private var categoryResponseList = AppApplication.categoryData
    private var myCategoryResponse = AppApplication.userData.myCategory
    private var myRoadmapResponse = AppApplication.userData.roadmap

    val categoryItemList = mutableListOf<CategoryItem>()

    init {
        initCategoryListVer2()
    }

    /**
     * recycler view 에 넣을 데이터를 초기화
     * firebase의 roadmap 대신 myCategory를 사용하게 되면, 삭제가 필요
     */
    private fun initCategoryList(){
        categoryItemList.clear()

        val title = getCategoryTitle("Category1")
        val categoryItem = CategoryItem(title)

        myRoadmapResponse.forEach {
            val roadmapId = it.key
            val roadmapItem = getRoadmapItem("Category1", roadmapId)
            categoryItem.roadmapList.add(roadmapItem)
        }

        categoryItemList.add(categoryItem)
    }

    /**
     * recycler view 에 넣을 데이터를 초기화 ver2
     */
    private fun initCategoryListVer2(){
        categoryItemList.clear()

        myCategoryResponse.forEach {
            val categoryId = it.key
            val categoryResponse = it.value

            val title = getCategoryTitle(categoryId)
            val categoryItem = CategoryItem(title)

            categoryResponse.myRoadmap.forEach {
                val roadmapId = it.key
                val roadmapIResponse = it.value

                val roadmapItem = getRoadmapItem(categoryId, roadmapId)
                roadmapItem.last_access = roadmapIResponse.last_access
                categoryItem.roadmapList.add(roadmapItem)
            }

            categoryItemList.add(categoryItem)
        }
    }

    /**
     * category id로 category title 찾기
     */
    private fun getCategoryTitle(categoryId: String) : String {
        val categoryResponse = categoryResponseList.get(categoryId)
        return categoryResponse?.title ?: ""
    }

    /**
     * category > roadmap 에서 item 가져오기
     */
    private fun getRoadmapItem(categoryId : String, roadmapId : String) : RoadmapItem {
        val categoryResponse = categoryResponseList.get(categoryId)
        val roadmapResponse = categoryResponse?.roadmapMap?.get(roadmapId)

        val roadmapItem = RoadmapItem()
        roadmapItem.let {
            it.title = roadmapResponse?.title ?: ""
            it.desc = roadmapResponse?.desc ?: ""
            it.timelimit = roadmapResponse?.timelimit ?: 0
            it.percentage = roadmapResponse?.percentage ?: 0

        }

        return roadmapItem
    }
}