package com.garosero.android.hobbyroadmap.main.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.main.helper.CastHelper
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.ReadUserRequest
import com.garosero.android.hobbyroadmap.network.request.RequestListener
import com.garosero.android.hobbyroadmap.network.response.UserResponse

open class MylistViewModel : ViewModel() {
    private val TAG = "MylistViewModel"

    var classList = MutableLiveData<ArrayList<MyClass>>() // data for adapter ver 2

    fun subscribeUserData(context: Context){
        AppApplication.requestSubscribeUser()

        NetworkFactory.request(ReadUserRequest(), object : RequestListener(){
            override fun onRequestSuccess(data: Any) {
                val _classList = ArrayList<MyClass>() // data for adapter ver 2

                (data as? UserResponse)?.myClass?.forEach {
                    val classItem = CastHelper.myClassResponseToMyClass(it.value, context)
                    _classList.add(classItem)
                }

                classList.value = _classList
            }
        })
    }

    //private fun createKey(classItem : MyClass) : String  = "${classItem.LClassId} ${classItem.MClassId}"
}