package com.garosero.android.hobbyroadmap.data

import com.garosero.android.hobbyroadmap.network.response.TilResponse

class TilItem(
    var LClassId : String = "",
    var MClassId : String = "",
    var SClassId : String = "",
    var subClassId : String = "",
    var moduleNum : String = "", // update 데이터에서는 사용되지 않는다.
) : TilResponse() {
}