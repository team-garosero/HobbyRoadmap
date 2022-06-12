package com.garosero.android.hobbyroadmap.request

import android.util.Log
import com.garosero.android.hobbyroadmap.data.TilItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TilItemRequest : BaseRequest() {
    override val TAG = "TIL_REQUEST"
    private val DATA_PATH = "TIL"

    override fun request() {

        FirebaseDatabase.getInstance()
            .getReference(DATA_PATH)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<TilItem>()
                    snapshot.children.forEach {
                        val item = it.getValue(TilItem::class.java)
                        list.add(item as TilItem)
                    }

                    mlistener?.onRequestSuccess(list as Object)
                }

                override fun onCancelled(error: DatabaseError) {
                    mlistener?.onRequestFail()
                    Log.e(TAG, error.toString())
                }
            })
    }
}