package com.garosero.android.hobbyroadmap.main.helper

import android.annotation.SuppressLint
import android.content.Context
import com.garosero.android.hobbyroadmap.helper.DBHelper

/**
 * LclassId, MClassId, SClassId로 sqlite에 저장 되어 있는 값에 접근할 때,
 * 공통으로 사용하는 함수를 모아두려고 합니다.
 * fixme 추후에 다른 클래스에서도 공통으로 사용 가능한지 확인이 필요합니다.
 */
class SQLiteSearchHelper(
    private val mContext : Context,
    ) {

    private val dbName = "newdb.db"
    private val version = 1

    @SuppressLint("Range")
    fun getSubClassName(
        LClassID : String,
        MClassID : String,
        SClassID : String,
        subClassID : String): String? {

        val helper = DBHelper(mContext, dbName, null, version)
        val db = helper.readableDatabase
        val sql =
            "select * from module_table where l_class_code='$LClassID' and m_class_code='$MClassID' and s_class_code='$SClassID' and sub_class_code='$subClassID'"
        val cursor = db.rawQuery(sql, null)
        cursor.moveToNext()
        val subClassName = cursor.getString(cursor.getColumnIndex("sub_class_name"))

        cursor.close()
        db.close()

        return subClassName
    }

    fun getSubClassSize(
        LClassID : String,
        MClassID : String,
        SClassID : String,
        subClassID : String
    ) : Int{

        val helper = DBHelper(mContext, dbName, null, version)
        val db = helper.readableDatabase
        val sql =
            "select * from module_table where l_class_code='$LClassID' and m_class_code='$MClassID' and s_class_code='$SClassID' and sub_class_code='$subClassID'"
        val cursor = db.rawQuery(sql, null)
        cursor.moveToNext()
        val count = cursor.count

        cursor.close()
        db.close()

        return count
    }

    @SuppressLint("Range")
    fun getMClassName(
        LClassID : String,
        MClassID : String,
    ) : String {
        val helper = DBHelper(mContext, dbName, null, version)
        val db = helper.readableDatabase
        val sql =
            "select * from module_table where l_class_code='$LClassID' and m_class_code='$MClassID'"
        val cursor = db.rawQuery(sql, null)
        cursor.moveToNext()
        val mClassName = cursor.getString(cursor.getColumnIndex("m_class_name"))

        cursor.close()
        db.close()

        return  mClassName
    }
}