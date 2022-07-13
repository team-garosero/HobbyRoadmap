package com.garosero.android.hobbyroadmap.main.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.main.helper.CastHelper
import com.garosero.android.hobbyroadmap.main.helper.SQLiteSearchHelper
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.ApiRequest
import com.garosero.android.hobbyroadmap.network.request.ReadUserRequest
import com.garosero.android.hobbyroadmap.network.request.RequestListener
import com.garosero.android.hobbyroadmap.network.response.UserResponse

open class MylistViewModel : ViewModel() {
    private val TAG = "MylistViewModel"

    var myClass = MutableLiveData<MutableMap<String, ArrayList<MyClass>>>()

    fun subscribeUserData(context: Context){
        AppApplication.requestSubscribeUser()

        NetworkFactory.request(ReadUserRequest(), object : RequestListener(){
            override fun onRequestSuccess(data: Any) {
                val _myClass = mutableMapOf<String, ArrayList<MyClass>>()

                (data as? UserResponse)?.myClass?.forEach {
                    val classItem = CastHelper.myClassResponseToMyClass(it.value, context)
                    val keyString = createKey(classItem)

                    if(_myClass[keyString] == null){
                        _myClass[keyString] = ArrayList()

                    } // end if

                    _myClass[keyString]?.add(classItem)
                }

                myClass.value = _myClass
            }
        })
    }

    private fun createKey(classItem : MyClass) : String  = "${classItem.LClassId} ${classItem.MClassId}"
    fun parsingKey(key : String) : ArrayList<String>{
        val parsingAnswer = java.util.ArrayList(key.split(" "))

        try {
            if (parsingAnswer.size != 2){
                throw IllegalAccessError()
            }
        } catch (e : IllegalAccessError){
            Log.e(TAG, e.stackTraceToString())
        }

        return parsingAnswer
    }

    fun getMyClass(keyString : String) : ArrayList<MyClass> {
        return myClass.value?.get(keyString) ?: ArrayList()
    }

}