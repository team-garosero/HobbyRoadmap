package com.garosero.android.hobbyroadmap.main.helper

import android.content.Context
import android.util.Log
import androidx.core.database.getStringOrNull
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.MClassItem
import com.garosero.android.hobbyroadmap.helper.DBHelper
import com.garosero.android.hobbyroadmap.helper.SqlCol

/**
 * LclassId, MClassId, SClassId로 sqlite에 저장 되어 있는 값에 접근할 때,
 * 공통으로 사용하는 함수를 모아두려고 합니다.
 * fixme 추후에 다른 클래스에서도 공통으로 사용 가능한지 확인이 필요합니다.
 */
class SQLiteSearchHelper(
    private val mContext : Context,
    ) {

    private val packageName = DBHelper.packageName
    private val dbName = DBHelper.dbName
    private val version = DBHelper.version
    private val TAG = "SQLiteSearchHelper"
    private val helper = DBHelper(mContext,null, version)
    private val errorMsg = mContext.getString(R.string.error_msg)

    fun getLClassName(LClassID: String): String {
        try {
            val db = helper.readableDatabase
            val sql = "select ${SqlCol.l_class_name.name} from $dbName " +
                    "where ${SqlCol.l_class_code.name}='$LClassID'"
            val cursor = db.rawQuery(sql, null)
            cursor.moveToFirst()
            Log.d(TAG, sql)

            val lClassName = cursor.getStringOrNull(0) ?: errorMsg
            cursor.close()
            db.close()

            return lClassName
        } catch (e : Exception){
            Log.d(TAG, e.stackTraceToString())
        }

        return errorMsg
    }

    fun getMClassName(LClassID : String, MClassID : String ) : String {

        try {

            val db = helper.readableDatabase
            val sql =
                "select ${SqlCol.m_class_name.name} from $dbName " +
                        "where ${SqlCol.l_class_code.name}='$LClassID' " +
                        "and ${SqlCol.m_class_code.name}='$MClassID'"
            val cursor = db.rawQuery(sql, null)
            cursor.moveToFirst()
            Log.d(TAG, sql)

            val mClassName = cursor.getStringOrNull(0)
            cursor.close()
            db.close()

            return  mClassName ?: errorMsg
        } catch (e : Exception){
            Log.d(TAG, e.stackTraceToString())
        }

        return errorMsg
    }

    fun getSClassName(LClassID: String, MClassID: String, SClassID: String): String {
        try {

            val db = helper.readableDatabase
            val sql =
                "select ${SqlCol.s_class_name.name} from $dbName " +
                        "where ${SqlCol.l_class_code.name}='$LClassID' " +
                        "and ${SqlCol.m_class_code.name}='$MClassID'" +
                        "and ${SqlCol.s_class_code.name} = '$SClassID'"
            val cursor = db.rawQuery(sql, null)
            cursor.moveToFirst()
            Log.d(TAG, sql)

            val sClassName = cursor.getStringOrNull(0)
            cursor.close()
            db.close()

            return  sClassName ?: errorMsg
        } catch (e : Exception){
            Log.d(TAG, e.stackTraceToString())
        }

        return errorMsg
    }

    fun getSubClassName(LClassID : String, MClassID : String, SClassID : String, subClassID : String): String? {

        try {
            val db = helper.readableDatabase
            val sql =
                "select ${SqlCol.sub_class_name.name} from $dbName " +
                        "where ${SqlCol.l_class_code.name}='$LClassID' " +
                        "and ${SqlCol.m_class_code.name}='$MClassID' " +
                        "and ${SqlCol.s_class_code.name}='$SClassID' " +
                        "and ${SqlCol.sub_class_code.name}='$subClassID'"
            val cursor = db.rawQuery(sql, null)
            cursor.moveToFirst()
            Log.d(TAG, sql)

            val index = cursor.getColumnIndex(SqlCol.sub_class_name.name)
            val subClassName = cursor.getStringOrNull(index)
            cursor.close()
            db.close()

            return subClassName
        } catch (e : Exception){
            Log.d(TAG, e.stackTraceToString())
        }

        return errorMsg
    }

    fun getSubClassSize(LClassID : String, MClassID : String, SClassID : String, subClassID : String) : Int{

        val db = helper.readableDatabase
        val sql =
            "select * from ${dbName} " +
                    "where ${SqlCol.l_class_code.name}='$LClassID' " +
                    "and ${SqlCol.m_class_code.name}='$MClassID' " +
                    "and ${SqlCol.s_class_code.name}='$SClassID' " +
                    "and ${SqlCol.sub_class_code.name}='$subClassID'"
        val cursor = db.rawQuery(sql, null)
        cursor.moveToFirst()

        var count = cursor.count

        cursor.close()
        db.close()

        return count
    }

}