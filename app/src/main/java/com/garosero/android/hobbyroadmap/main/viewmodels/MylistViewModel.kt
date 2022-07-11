package com.garosero.android.hobbyroadmap.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.main.helper.CastHelper
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.ApiRequest
import com.garosero.android.hobbyroadmap.network.request.ReadUserRequest
import com.garosero.android.hobbyroadmap.network.request.RequestListener
import com.garosero.android.hobbyroadmap.network.response.UserResponse

open class MylistViewModel : ViewModel() {
    private val TAG = "MylistViewModel"

    var myClass = MutableLiveData<MutableMap<String, ArrayList<MyClass>>>()

    init {
        NetworkFactory.request(ReadUserRequest(), object : RequestListener(){
            override fun onRequestSuccess(data: Any) {
                val _myClass = mutableMapOf<String, ArrayList<MyClass>>()

                (data as? UserResponse)?.myClass?.forEach {
                    val classItem = CastHelper.myClassResponseToMyClass(it.value)

                    if(_myClass[classItem.SClassId] == null){
                        _myClass[classItem.SClassId] = ArrayList()

                    } // end if

                    _myClass[classItem.SClassId]?.add(classItem)
                }

                myClass.value = _myClass
            }
        })
    }

    fun getMyClass(mClassID : String) : ArrayList<MyClass> {
        return myClass.value?.get(mClassID) ?: ArrayList()
    }

    fun getMClassName(mClassID : String) : String {
        // fixme
        //return ApiRequest.lClass.getmClassMap()[mClassID]?.name ?: ""
        return "name"
    }

}