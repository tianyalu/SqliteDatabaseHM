package com.sty.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sty.database.db.MySqliteOpenHelper;
import com.sty.database.bean.InfoBean;

/**
 * Created by Administrator on 2017/9/11/0011.
 */

public class InfoDao {
    private static final String TAG = InfoDao.class.getSimpleName();
    private MySqliteOpenHelper mySqliteOpenHelper;

    public InfoDao(Context context){
        //创建一个帮助类对象
        mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    public void add(InfoBean bean){
        //执行sql语句需要SqliteDatabase对象
        //调用getReadableDatabase方法来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase(); //先尝以读写方式打开数据库，如果磁盘空间满了，会重新尝试以只读方式打开数据库
        //SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase(); //直接以读写方式打开数据库，如果磁盘空间满了，直接报错
        //sql:sql语句  bindArgs:sql语句中占位符的值
        db.execSQL("insert into info(name, phone) values(?, ?);", new Object[]{bean.name, bean.phone});
        //关闭数据库
        db.close();
    }

    public void delete(String name){
        //执行sql语句需要SqliteDatabase对象
        //调用getReadableDatabase方法来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //sql:sql语句  bindArgs:sql语句中占位符的值
        db.execSQL("delete from info where name = ?;", new String[]{name});
        //关闭数据库
        db.close();
    }

    public void update(InfoBean bean){
        //执行sql语句需要SqliteDatabase对象
        //调用getReadableDatabase方法来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //sql:sql语句  bindArgs:sql语句中占位符的值
        db.execSQL("update info set phone = ? where name = ?;", new Object[]{bean.phone, bean.name});
        //关闭数据库
        db.close();
    }

    public void query(String name){
        //执行sql语句需要SqliteDatabase对象
        //调用getReadableDatabase方法来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //sql:sql语句  selectionArgs:查询条件占位符的值，返回一个cursor对象
        Cursor cursor = db.rawQuery("select _id, name, phone from info where name = ?;", new String[]{name});
        //解析Cursor中的数据
        if(cursor != null && cursor.getCount() > 0){ //判断cursor中是否存在数据
            //循环遍历结果集，获取每一行的内容
            while(cursor.moveToNext()){ //条件，游标能否定位到下一行
                //获取数据
                int id = cursor.getInt(0);
                String name_str = cursor.getString(1);
                String phone = cursor.getString(2);

                Log.i(TAG, "_id: " + id + "   name: " + name_str + "   phone: " + phone);
            }

            //关闭结果集
            cursor.close();
        }

        //关闭数据库
        db.close();
    }
}
