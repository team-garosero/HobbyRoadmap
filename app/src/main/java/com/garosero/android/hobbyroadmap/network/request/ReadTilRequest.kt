package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.error.CanNotFindUidError
import com.garosero.android.hobbyroadmap.network.response.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReadTilRequest : BaseRequest() {
    override val TAG = ReadTilRequest::javaClass.name
    private val DATA_PATH = "TIL"

    override fun request() {
        var uid = AppApplication().getUid()

        FirebaseDatabase.getInstance()
            .getReference(DATA_PATH)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val answer = mutableMapOf<String, _TilResponse>()

                    snapshot.children.forEach {
                        val item = it.getValue(_TilResponse::class.java)

                        if (item?.uid.equals(uid)) {
                            item?.tilId = it.key.toString()
                            answer.put(item?.tilId!!, item)
                        } // end if
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