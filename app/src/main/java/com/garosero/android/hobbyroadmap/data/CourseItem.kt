package com.garosero.android.hobbyroadmap.data

import java.io.Serializable

/**
 * 22.07.03
 * intent를 통해 전달해 줄 데이터
 * Parcelable 과 Serializable 등의 방법이 있음
 * 현재,
 * CourseItem 은 Serializable 로
 * RoadMapItem 은 Parcelable 로 구성되어 있음
 *
 * 추후에 하나만 사용하도록 조정
 */
class CourseItem(
    var courseId : String = "",
    var roadmapId : String = "",
    var categoryId : String = "",

    var xp : Int = 0,
    var title : String = "",
    var desc : String = "",
    var order : Int = 0,
) : Serializable
