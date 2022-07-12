package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class DeleteTilRequest(
    val id : String
) : BaseRequest() {
    override val TAG: String = "DeleteTilRequest"
    private val DATA_PATH = "TIL"

    override fun request() {
        FirebaseDatabase.getInstance().
        getReference(DATA_PATH).child(id).removeValue()
            .addOnFailureListener {
                Log.e(TAG, it.stackTraceToString())
                mlistener?.onRequestFail()

            }.addOnSuccessListener {
                mlistener?.onRequestSuccess()

            }
    }
}