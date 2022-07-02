package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.network.response._UserResponse
import com.google.firebase.database.FirebaseDatabase

class ReadUserRequest : BaseRequest() {
    override val TAG = "ReadUserRequest"
    private val DATA_PATH = "Users"

    override fun request() {
        val uid = AppApplication().getUid()

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
}