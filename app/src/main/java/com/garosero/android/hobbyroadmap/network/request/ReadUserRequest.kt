package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.network.response.UserResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val result = snapshot.getValue(UserResponse::class.java)
                    (result as UserResponse).myClass.forEach {
                        it.value.classPath = it.key
                    }

                    mlistener?.onRequestSuccess(result as Object)
                }

                override fun onCancelled(error: DatabaseError) {
                    mlistener?.onRequestFail()
                    Log.e(TAG, error.toString())
                }

            })
    }

    class CanNotFindUidError : Exception(){
        override val message: String?
            get() = "firebase uid를 찾을 수 없습니다."

    }
}