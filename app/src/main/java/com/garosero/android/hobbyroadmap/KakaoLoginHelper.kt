package com.garosero.android.hobbyroadmap

import android.content.Context
import android.util.Log
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class KakaoLoginHelper {
    companion object {
        fun login(context:Context) {

            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("KakaoLoginHelper", "로그인 실패", error)
                }
                else if (token != null) {
                    Log.d("KakaoLoginHelper", "로그인 성공 ${token.accessToken}")
                }
            }
        }
    }

}