package com.sty.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/10/0010.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = MySqliteOpenHelper.class.getSimpleName();

    public MySqliteOpenHelper(Context context) {
        //context:上下文  name:数据库文件的名称  factory:用来创建cursor对象，默认为null
        // version:数据库版本号，从1开始，如果发生改变，onUpgrade方法将会被调用-->4.0之后只能升不能降
        super(context, "info.db", null, 1);
    }

    /**
     * 数据库第一次创建的时候被调用，特别适合做表结构的初始化，需要执行sql语句
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //通过SQLiteDatabase执行一个创建表的sql语句
        db.execSQL("create table info (_id integer primary key autoincrement, name varchar(20)," +
                " phone varchar(11))");
    }

    /**
     * 数据库版本号发生改变时才会执行，特别适合做表结构的修改
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "数据库版本号由 " + oldVersion + " 升级为 " + newVersion);
        //添加一个phone字段
        //db.execSQL("alter table info add phone varchar(11)");
    }
}
