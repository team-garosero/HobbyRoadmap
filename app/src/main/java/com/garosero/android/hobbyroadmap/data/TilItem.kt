package com.garosero.android.hobbyroadmap.data

import com.garosero.android.hobbyroadmap.network.response.TilResponse

class TilItem(
    var LClassId : String = "",
    var MClassId : String = "",
    var SClassId : String = "",
    var subClassId : String = "",
) : TilResponse() {
}