package com.garosero.android.hobbyroadmap.request

import android.provider.ContactsContract
import android.util.Log
import com.garosero.android.hobbyroadmap.data.CategoryItem
import com.garosero.android.hobbyroadmap.data.CourseItem
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.data.SubContentItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class RoadmapRequest : BaseRequest() {
    override val TAG = "ROADMAP_REQUEST"
    private val DATA_PATH = "Roadmap"

    /**
     * roadmap은 데이터의 변경이 거의 일어나지 않는 부분이라,
     * 데이터를 계속 관찰해야 할 필요가 없다는 생각이 들었다.
     * 우선 데이터를 한번만 가져오도록 처리해두고,
     * 이후 데이터의 변경이 자주 일어난다고 하면,
     * addValueEventListener 로 변경이 필요하다.
     */
    override fun request() {
        FirebaseDatabase.getInstance()
        .reference
        .child(DATA_PATH)
        .get()
        .addOnSuccessListener {
            val categoryItem = parsingCategory(it)
            mlistener?.onRequestSuccess(categoryItem as Object)
        }
        .addOnFailureListener {
            Log.e(TAG, it.toString())
            mlistener?.onRequestFail()
        }
    }

    /*
    파싱하는 데이터의 깊이가 깊어서, 함수를 나누어 구성해둠
    추후에 데이터 구조가 변경되면 해당하는 함수를 변경해야 한다.
    category > roadmap > subContent > course
    순으로 호출함.

    추후에 오류처리가 필요할 수 있다.
    현재 category의 자식이 roamap 여러개와 title(String).. 등으로 구성 되어 있음
    그래서 roadmap으로 데이터를 받으면, title(String)에서 오류가 발생함.
    이럴 때마다 로그를 찍으면, 실제 오류가 발생 했을 때와
    구분이 가지 않을까봐 따로 오류를 처리하는 로직을 구현하지는 않았음

    category
        - title
        - roadmapID1
        - roadmapID2...

    W/ClassMapper: No setter/field for CourseID3 found on class com.garosero.android.hobbyroadmap.data.SubContentItem
    로그가 여러번 발생해서, 가능하다면 아래처럼 구조 변경을 고려해 볼것

    category
        - title
        - roadmap
            - roadmapID1
            - roadmapID2...
     */
    private fun parsingCategory(it : DataSnapshot) : MutableMap<String, CategoryItem> {
        val categoryMap = mutableMapOf<String, CategoryItem>()
        try {
            it.children.forEach {
                val categoryItem = it.getValue(CategoryItem::class.java) ?: CategoryItem()
                categoryItem.roadmapMap = parsingRoadmap(it)
                categoryItem.categoryID = it.key.toString()
                categoryMap.put(it.key.toString(), categoryItem)
            }
        } catch (error : Exception){}

        return categoryMap
    }

    private fun parsingRoadmap(it : DataSnapshot) : MutableMap<String, RoadmapItem> {
        val roadmapMap = mutableMapOf<String, RoadmapItem>()

        try {
            it.children.forEach {
                val roadmapItem = it.getValue(RoadmapItem::class.java) ?: RoadmapItem()
                roadmapItem.subContentMap = parsingSubContent(it)
                roadmapItem.roadmapID = it.key.toString()
                roadmapMap.put(it.key.toString(), roadmapItem)
            }
        } catch (e : Exception){}

        return roadmapMap
    }

    private fun parsingSubContent(it : DataSnapshot) : MutableMap<String, SubContentItem> {
        val subContentMap = mutableMapOf<String, SubContentItem>()

        try {
            it.children.forEach {
                val subContentItem = it.getValue(SubContentItem::class.java) ?: SubContentItem()
                subContentItem.courseMap = parsingCourse(it)
                subContentItem.subContentID = it.key.toString()
                subContentMap.put(it.key.toString(), subContentItem)
            }
        } catch (e : Exception){}

        return subContentMap
    }

    private fun parsingCourse(it : DataSnapshot) : MutableMap<String, CourseItem> {
        val courseMap = mutableMapOf<String, CourseItem>()
        try {
            it.children.forEach {
                val courseItem = it.getValue(CourseItem::class.java) ?: CourseItem()
                courseItem.courseID = it.key.toString()
                courseMap.put(it.key.toString(), courseItem)
            }
        } catch (e : Exception){}

        return courseMap
    }
}