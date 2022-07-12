package com.garosero.android.hobbyroadmap.main.viewmodels

import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.network.response.UserResponse

class AdventureViewModel : ViewModel() {
    var userData = AppApplication.userData.value ?: UserResponse()

    var characterName = userData.name
    var life = userData.life // todo : custom view 로 분리해서 적용

    var maxXp = 500 // 경험치 최대 500
    var totalXp = userData.xp
    var xp = totalXp.mod(maxXp)
    var level = totalXp.div(maxXp)
    var progress = (xp*100)/maxXp

    var prevIvSrc = R.drawable.adv_cat_box
    var fullIvSrc = R.drawable.adv_cat

    fun initData(userData: UserResponse){
        characterName = userData.name
        life = userData.life

        totalXp = userData.xp
        xp = totalXp.mod(maxXp)
        level = totalXp.div(maxXp)
        progress = (xp * 100) / maxXp
    }

    var pageDescription =
            "로드맵을 따라 학습모듈을 공부하며 \n" +
            "받은 경험치를 통해 캐릭터를 성장시킬 수 있습니다.\n" +
            "최고 레벨에 도전해보세요! "

    /*
    todo :
    data binding 을 사용하려 했으나,
    인식이 안되는 문제로, 우선 직접 연결
    추후에 data binding 으로 변경하기
     */
}