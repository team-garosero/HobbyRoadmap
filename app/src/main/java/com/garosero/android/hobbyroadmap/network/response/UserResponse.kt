package com.garosero.android.hobbyroadmap.network.response

/**
 * Firebase 에서 데이터를 받아올때만 사용하는 클래스
 * 파싱할 때 사용한다.
 * 외부에서 직접 호출하지 않아야 한다.
 */
data class UserResponse(
    var life : Int = 0,
    var name : String = "",
    var nickname : String = "",
    var token : String = "",
    var xp : Int = 0,

    var myClass : Map<String, MyClassResponse> = mapOf()
) {
    override fun toString(): String {
        return "UserResponse(life=$life, name='$name', nickname='$nickname', token='$token', xp=$xp, myClass=$myClass)"
    }
}