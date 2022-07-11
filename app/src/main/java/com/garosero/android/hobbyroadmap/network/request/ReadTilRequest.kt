package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.network.response.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReadTilRequest : BaseRequest() {
    override val TAG = ReadTilRequest::javaClass.name
    private val DATA_PATH = "TIL"

    override fun request() {
        FirebaseDatabase.getInstance()
            .getReference(DATA_PATH)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val answer = mutableMapOf<String, TilResponse>()

                    snapshot.children.forEach {
                        val item = it.getValue(TilResponse::class.java)
                        val key = it.key.toString()

                        (item as TilResponse).tilId = key
                        answer[key] = item
                    }

                    mlistener?.onRequestSuccess(answer as Object)
                }

                override fun onCancelled(error: DatabaseError) {
                    mlistener?.onRequestFail()
                    Log.e(TAG, error.toString())
                }
            })
    }
}