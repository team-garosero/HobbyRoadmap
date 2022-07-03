package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.main.helper.CastHelper
import com.google.firebase.database.FirebaseDatabase

class CreateTilRequest(
    val tilItem : TilItem
) : BaseRequest() {
    override val TAG: String = "CreateTilRequest"
    private val DATA_PATH = "TIL"
    private val castHelper = CastHelper()

    override fun request() {
        val response = castHelper.tilItem_to_tilResponse(tilItem)

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