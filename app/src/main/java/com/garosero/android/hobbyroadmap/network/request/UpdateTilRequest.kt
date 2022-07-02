package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class UpdateTilRequest(
    val id : String,
    val content : String
) : BaseRequest() {
    override val TAG: String = "UpdateTilRequest"
    private val DATA_PATH = "TIL"

    override fun request() {
        val basePath = FirebaseDatabase.getInstance().getReference(DATA_PATH).child(id)
        with(basePath){
            child("content").setValue(content)
            // 추후에 더 필요하면 항목 추가...
        }.addOnFailureListener {
            Log.e(TAG, it.stackTraceToString())
            mlistener?.onRequestFail()

        }.addOnSuccessListener {
            mlistener?.onRequestSuccess()

        }
    }
}