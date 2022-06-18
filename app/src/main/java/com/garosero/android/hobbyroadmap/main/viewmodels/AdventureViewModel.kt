package com.garosero.android.hobbyroadmap.main.viewmodels

import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.R

class AdventureViewModel : ViewModel() {
    private var userData = AppApplication.userData

    var characterName = userData.name
    var life = userData.life // todo : custom view 로 분리해서 적용

    var maxXp = 32 // todo : 레벨 당 xp를 임의 지정, 추후에 논의 필요
    private var totalXp = userData.xp
    var xp = totalXp.mod(maxXp)
    var level = totalXp.div(maxXp)
    var progress = (xp*100)/maxXp

    var prevIvSrc = R.drawable.adv_cat_box
    var fullIvSrc = R.drawable.adv_cat

    /*
    todo :
    data binding 을 사용하려 했으나,
    인식이 안되는 문제로, 우선 직접 연결
    추후에 data binding 으로 변경하기
     */
}