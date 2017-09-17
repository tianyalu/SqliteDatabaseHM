package com.sty.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sty.database.db.MySqliteOpenHelper;
import com.sty.database.bean.InfoBean;

/**
 * Created by Administrator on 2017/9/17/0017.
 */

public class InfoDao2 {
    private static final String TAG = InfoDao2.class.getSimpleName();
    private MySqliteOpenHelper mySqliteOpenHelper;

    public InfoDao2(Context context){
        mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    public boolean add(InfoBean bean){
        //执行sql语句需要SqliteDatabase对象
        //调用getReadableDatabase方法来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

        ContentValues values = new ContentValues(); //是用map封装的对象，用来存放值
        values.put("name", bean.name);
        values.put("phone", bean.phone);
        //table:表名， nullColumnHack:可以为空，标识添加一个空行， values:数据一行的值
        //返回值：代表添加这个新行的ID  -1代表添加失败
        long result = db.insert("info", null, values); //底层在拼装sql语句

        //关闭数据库
        db.close();
        if(result != -1){ //-1代表添加失败
            return true;
        }else{
            return false;
        }
    }

    public int delete(String name){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

        //table:表名 sql:sql语句 whereArgs:条件的占位符参数 返回值：成功删除了多少行
        int result = db.delete("info", "name = ?", new String[]{name});
        //关闭数据库对象
        db.close();

        return result;
    }

    public int update(InfoBean bean){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

        ContentValues values = new ContentValues(); //是用map封装的对象
        values.put("phone", bean.phone);
        //table:表名 values:更新的值 whereClause:更新的条件 whereArgs:更新条件占位符的值
        //返回值：成功修改多少行
        int result = db.update("info", values, "name = ?", new String[]{bean.name});

        //关闭数据库对象
        db.close();

        return result;
    }

    public void query(String name){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

        //table:表名 columns:查询的列名，如果为null代表查询所有列 selection:查询条件
        //selectionArgs:条件占位符的参数值 groupBy:按什么字段分组 having:分组的条件 orderBy:按什么字段排序
        Cursor cursor = db.query("info", new String[]{"_id", "name", "phone"}, "name = ?", new String[]{name},
                null, null, "_id desc");
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
