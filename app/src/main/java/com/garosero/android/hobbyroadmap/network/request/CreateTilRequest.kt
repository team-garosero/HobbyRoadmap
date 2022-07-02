package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.TilItem
import com.google.firebase.database.FirebaseDatabase

class CreateTilRequest(
    val tilItem : TilItem
) : BaseRequest() {
    override val TAG: String = "CreateTilRequest"
    private val DATA_PATH = "TIL"

    override fun request() {
        val response = AppApplication.castHelper.tilItem_to_tilResponse(tilItem)

        FirebaseDatabase.getInstance().getReference(DATA_PATH).push()
            .setValue(response)
            .addOnFailureListener {
                Log.e(TAG, it.stackTraceToString())
                mlistener?.onRequestFail()

            }.addOnSuccessListener {
                mlistener?.onRequestSuccess()

            }
    }
}