package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class UpdateTilRequest(
    val tilId : String,
    val content : String
) : BaseRequest() {
    override val TAG: String = "CreateTilRequest"
    private val DATA_PATH = "TIL"

    override fun request() {
        FirebaseDatabase.getInstance().getReference(DATA_PATH)
            .child(tilId)
            .child("content")
            .setValue(content)
            .addOnFailureListener {
                Log.e(TAG, it.stackTraceToString())
                mlistener?.onRequestFail()

            }.addOnSuccessListener {
                mlistener?.onRequestSuccess()

            }
    }
}