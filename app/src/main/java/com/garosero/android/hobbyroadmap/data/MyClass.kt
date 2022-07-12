package com.garosero.android.hobbyroadmap.data

import com.garosero.android.hobbyroadmap.network.response.MyClassResponse

class MyClass(
    var LClassId : String = "",
    var MClassId : String = "",
    var SClassId : String = "",
    var subClassId : String = "",
    var subClassName : String = "",
) : MyClassResponse(){
}