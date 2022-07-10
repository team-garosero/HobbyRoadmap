package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.network.response.TilResponse
import com.google.firebase.database.FirebaseDatabase

class CreateTilRequest(
    val tilResponse: TilResponse
) : BaseRequest() {
    override val TAG: String = "CreateTilRequest"
    private val DATA_PATH = "TIL"

    override fun request() {
        FirebaseDatabase.getInstance().getReference(DATA_PATH).push()
            .setValue(tilResponse)
            .addOnFailureListener {
                Log.e(TAG, it.stackTraceToString())
                mlistener?.onRequestFail()

            }.addOnSuccessListener {
                mlistener?.onRequestSuccess()

            }
    }
}