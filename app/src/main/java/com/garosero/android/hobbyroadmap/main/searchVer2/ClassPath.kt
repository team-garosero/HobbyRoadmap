package com.garosero.android.hobbyroadmap.main.searchVer2

data class ClassPath(
    var lClassId : String? = null,
    var mClassId : String? = null,
    var sClassId : String? = null,
    var subClassId : String? = null,

    var lClassNm : String = "",
    var mClassNm : String = "",
    var sClassNm : String = "",
    var subClassNm : String = "",
) {
    fun getLastName(type : SearchType) : String{
        if (type == SearchType.subClass) return subClassNm
        if (type == SearchType.sClass) return sClassNm
        if (type == SearchType.mClass) return mClassNm
        if (type == SearchType.lClass) return  lClassNm

        return ""
    }

    fun getClassCD() : String = "${lClassId} ${mClassId} ${sClassId} ${subClassId}"
}