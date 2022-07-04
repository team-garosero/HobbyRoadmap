package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.network.response._UserResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class ReadUserRequest : BaseRequest() {
    override val TAG = "ReadUserRequest"
    private val DATA_PATH = "Users"

    override fun request() {
        var uid = FirebaseAuth.getInstance().currentUser?.uid
        try {
            if(uid == null) {
                // 우선 throw error 던지도록 처리
                throw CanNotFindUidError()
            }
        } catch (e : CanNotFindUidError){
            /**
             * 지금 uid 값이 안들어옴 나중에 여기 처리해야 함
             */
            uid = "M8mYC1eUs6RqEUrxTj7mARW3dK72"
            Log.e(TAG, e.stackTraceToString())
        }

        FirebaseDatabase.getInstance()
            .reference
            .child(DATA_PATH)
            .child(uid!!)
            .get()
            .addOnSuccessListener {
                val result = it.getValue(_UserResponse::class.java)
                mlistener?.onRequestSuccess(result as Object)
            }
            .addOnFailureListener {
                mlistener?.onRequestFail()
                Log.e(TAG, it.toString())
            }
    }

    class CanNotFindUidError : Exception(){
        override val message: String?
            get() = "firebase uid를 찾을 수 없습니다."

    }
}