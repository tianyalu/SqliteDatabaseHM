package com.sty.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sty.database.bean.InfoBean;
import com.sty.database.dao.InfoDao;
import com.sty.database.dao.InfoDao2;
import com.sty.database.db.BankOpenHelper;
import com.sty.database.db.MySqliteOpenHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;

    private Button btnAdd;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnQuery;

    private Button btnTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        //创建一个帮助类对象
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(mContext);
        //调用getReadableDatabase方法来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

        initViews();
        setListeners();
    }

    private void initViews(){
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnQuery = (Button) findViewById(R.id.btn_query);
        btnTransfer = (Button) findViewById(R.id.btn_transfer);
    }

    private void setListeners(){
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnTransfer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_add:
                //addInfo(mContext);
                addInfo2(mContext);
                break;
            case R.id.btn_delete:
                //deleteInfo(mContext);
                deleteInfo2(mContext);
                break;
            case R.id.btn_update:
                //updateInfo(mContext);
                updateInfo2(mContext);
                break;
            case R.id.btn_query:
                queryInfo(mContext);
                break;

            case R.id.btn_transfer:
                transaction();
                break;
            default:
                break;
        }
    }

    private void addInfo(Context context){
        InfoDao infoDao = new InfoDao(mContext); //创建一个dao对象做增删改查

        InfoBean bean = new InfoBean();
        bean.name = "张三";
        bean.phone = "110";
        infoDao.add(bean);

        InfoBean bean2 = new InfoBean();
        bean2.name = "李四";
        bean2.phone = "120";
        infoDao.add(bean2);
    }

    private void deleteInfo(Context context){
        InfoDao infoDao = new InfoDao(mContext); //创建一个dao对象做增删改查
        infoDao.delete("张三");
    }

    private void updateInfo(Context context){
        InfoDao infoDao = new InfoDao(mContext); //创建一个dao对象做增删改查

        InfoBean bean = new InfoBean();
        bean.name = "张三";
        bean.phone = "119";
        infoDao.update(bean);
    }

    private void queryInfo(Context context){
        InfoDao infoDao = new InfoDao(mContext); //创建一个dao对象做增删改查
        infoDao.query("张三");
    }

    private void addInfo2(Context context){
        InfoDao2 infoDao2 = new InfoDao2(mContext); //创建一个dao对象做增删改查

        InfoBean bean = new InfoBean();
        bean.name = "张三";
        bean.phone = "110";
        boolean result = infoDao2.add(bean);

        if(result){
            Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteInfo2(Context context){
        InfoDao2 infoDao2 = new InfoDao2(mContext); //创建一个dao对象做增删改查
        int del = infoDao2.delete("张三");
        Toast.makeText(mContext, "成功删除 " + del + " 行", Toast.LENGTH_SHORT).show();
    }

    private void updateInfo2(Context context){
        InfoDao2 infoDao2 = new InfoDao2(mContext); //创建一个dao对象做增删改查

        InfoBean bean = new InfoBean();
        bean.name = "张三";
        bean.phone = "119";
        int update = infoDao2.update(bean);
        Toast.makeText(mContext, "成功更新 " + update + " 行", Toast.LENGTH_SHORT).show();
    }

    private void queryInfo2(Context context){
        InfoDao infoDao = new InfoDao(mContext); //创建一个dao对象做增删改查
        infoDao.query("张三");
    }


    /**
     * 事务处理
     */
    private void transaction() {
        //1.创建一个帮助类对象
        BankOpenHelper bankOpenHelper = new BankOpenHelper(mContext);
        //2.调用数据库帮助类对象的getReadableDatabase创建数据库，初始化表数据，获取一个SQLiteDatabase对象
        //  去做转账(sql语句）
        SQLiteDatabase db = bankOpenHelper.getReadableDatabase();
        //3.转账，将李四的钱减200，张三加200

        db.beginTransaction(); //开启一个数据库事务
        try {
            db.execSQL("update account set money = money - 200 where name = ?", new String[]{"李四"});
            int i = 100 / 0; //模拟一个异常
            db.execSQL("update account set money = money + 200 where name = ?", new String[]{"张三"});

            db.setTransactionSuccessful(); //标记事务中的sql语句全部执行成功
        } finally {
            db.endTransaction(); //判断事务的标记是否成功，如果不成功，回滚错误之前执行的sql数据库
        }
    }
}
