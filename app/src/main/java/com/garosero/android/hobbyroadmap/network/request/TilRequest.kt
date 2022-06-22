package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.network.response.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TilRequest : BaseRequest() {
    override val TAG = "TIL_REQUEST"
    private val DATA_PATH = "TIL"

    override fun request() {
        FirebaseDatabase.getInstance()
            .getReference(DATA_PATH)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val answer = mutableMapOf<String, _TilResponse>()

                    snapshot.children.forEach {
                        val item = it.getValue(_TilResponse::class.java)
                        val key = it.key.toString()
                        answer.put(key, item as _TilResponse)
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