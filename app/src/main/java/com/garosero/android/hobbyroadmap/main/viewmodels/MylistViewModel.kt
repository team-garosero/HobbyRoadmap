package com.garosero.android.hobbyroadmap.main.viewmodels

import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.CategoryItem
import com.garosero.android.hobbyroadmap.main.helper.CastHelper

class MylistViewModel : ViewModel() {
    private val TAG = "MylistViewModel"

    private var myCategoryResponse = AppApplication.userData.myCategory
    private var myRoadmapResponse = AppApplication.userData.roadmap

    val categoryItemList = mutableListOf<CategoryItem>()
    private val castHelper = CastHelper()

    init {
        initCategoryListVer2()
    }

    /**
     * recycler view 에 넣을 데이터를 초기화
     * firebase의 roadmap 대신 myCategory를 사용하게 되면, 삭제가 필요
     */
    private fun initCategoryList(){
        categoryItemList.clear()

        val title = castHelper.getCategoryItem("Category1")?.title ?: ""
        val categoryItem = CategoryItem(title)

        myRoadmapResponse.forEach {
            val roadmapId = it.key
            val roadmapItem = castHelper.getRoadmapItem("Category1", roadmapId)
            categoryItem.roadmapList.add(roadmapItem)
        }

        categoryItemList.add(categoryItem)
    }

    /**
     * // todo cast 부분 옮겨야 함
     * recycler view 에 넣을 데이터를 초기화 ver2
     */
    private fun initCategoryListVer2(){
        categoryItemList.clear()

        myCategoryResponse.forEach {
            val categoryId = it.key
            val categoryResponse = it.value

            val title = castHelper.getCategoryItem(categoryId)?.title ?: ""
            val categoryItem = CategoryItem(title)

            categoryResponse.myRoadmap.forEach {
                val roadmapId = it.key
                val roadmapIResponse = it.value

                val roadmapItem = castHelper.getRoadmapItem(categoryId, roadmapId)
                roadmapItem.last_access = roadmapIResponse.last_access
                categoryItem.roadmapList.add(roadmapItem)
            }

            categoryItemList.add(categoryItem)
        }
    }

}