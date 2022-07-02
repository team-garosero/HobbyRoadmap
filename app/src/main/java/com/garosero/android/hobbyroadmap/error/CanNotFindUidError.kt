package com.garosero.android.hobbyroadmap.error

import java.lang.Exception

class CanNotFindUidError : Exception() {
    override val message: String?
        get() = "firebase uid를 찾을 수 없습니다."
}