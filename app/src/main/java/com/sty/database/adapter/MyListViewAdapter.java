package com.sty.database.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sty.database.R;
import com.sty.database.bean.InfoBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/25/0025.
 */

public class MyListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<InfoBean> arrayList;

    public MyListViewAdapter(Context context, ArrayList<InfoBean> arrayList){
        this.mContext = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = null;
        if(convertView != null){
            view = convertView;
        }else{
            view = View.inflate(mContext, R.layout.item_list, null);
        }

        TextView tvId = (TextView) view.findViewById(R.id.tv_id);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);

        InfoBean bean = arrayList.get(position);

        tvId.setText(bean._id);
        tvName.setText(bean.name);
        tvPhone.setText(bean.phone);

        return view;
    }
}
