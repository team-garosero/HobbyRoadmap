package com.garosero.android.hobbyroadmap.network.request

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateModulesRequest(
    val classPath : String,
    val moduleNum : String,
    val moduleName : String,
) : BaseRequest() {
    override val TAG: String = "CreateTilRequest"
    private val DATA_PATH = "Users"
    private val uid = FirebaseAuth.getInstance().uid

    override fun request() {
        if (uid == null){
            throw ReadUserRequest.CanNotFindUidError()
        }

        FirebaseDatabase.getInstance().getReference(DATA_PATH)
            .child(uid)
            .child("myClass")
            .child(classPath) // class key
            .child("modules")
            .child(moduleNum)
            .setValue(moduleName)
            .addOnFailureListener {
                Log.e(TAG, it.stackTraceToString())
                mlistener?.onRequestFail()

            }.addOnSuccessListener {
                mlistener?.onRequestSuccess()

            }
    }
}