package com.garosero.android.hobbyroadmap.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 22.07.03
 * MainActivity, SyllabusActivity 간 전달되는 데이터
 * bundle 에 넣어서 전달되어야 하므로, parcelable을 상속받음.
 */
class RoadmapItem() : Parcelable{
    var categoryId : String = ""
    var roadmapId : String = ""

    var title : String = "" // ex. 백엔드
    var desc : String="" // 로드맵 전체 설명
    var timelimit : Int = 0
    var percentage : Int = 0 // 0~100

    var last_access : String? = null
    var imageLink : String? = null //fixme : 아직 db에 반영되지 않은 컬럼

    constructor(parcel: Parcel) : this() {
        categoryId = parcel.readString().toString()
        roadmapId = parcel.readString().toString()
        title = parcel.readString().toString()
        desc = parcel.readString().toString()
        timelimit = parcel.readInt()
        percentage = parcel.readInt()
        last_access = parcel.readString()
        imageLink = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(categoryId)
        parcel.writeString(roadmapId)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeInt(timelimit)
        parcel.writeInt(percentage)
        parcel.writeString(last_access)
        parcel.writeString(imageLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoadmapItem> {
        override fun createFromParcel(parcel: Parcel): RoadmapItem {
            return RoadmapItem(parcel)
        }

        override fun newArray(size: Int): Array<RoadmapItem?> {
            return arrayOfNulls(size)
        }
    }
}