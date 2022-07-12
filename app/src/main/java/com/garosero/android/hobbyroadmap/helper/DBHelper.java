package com.garosero.android.hobbyroadmap.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static String packageName = "newdb.db";
    public static String dbName = "module_table";
    public static Integer version = 1;

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory, int version){
        super(context, packageName, factory, version);
    }

    public boolean exists(SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='module_table'", null);
        if(c.getCount() == 0) {
            c.close();
            return false;
        }

        c = db.rawQuery("select l_class_code from module_table", null);
        boolean res = c.getCount() != 0;
        c.close();

        return res;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not exists module_table" +
                "(ID integer primary key autoincrement, l_class_code text, l_class_name text, " +
                "m_class_code text, m_class_name text, s_class_code text, s_class_name text, sub_class_code text, sub_class_name text," +
                "module_num text, module_name text, module_text text)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE if exists module_table";

        db.execSQL(sql);
        onCreate(db);
    }
}
