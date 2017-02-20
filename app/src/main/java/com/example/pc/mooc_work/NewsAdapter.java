package com.example.pc.mooc_work;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<NewsBean> mList;
    private LayoutInflater mInflater;
    private int myMode;
    public NewsAdapter(Context context,List<NewsBean> date,int mode){
        mList = date;
        myMode = mode;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//优化了2次的ListView
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout,null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            if (myMode == 1) {
                viewHolder.title.setText(mList.get(position).newsTitle);//为每一项设置内容
                viewHolder.content.setText(mList.get(position).newsContent);
                new ImageLoader().showImage(viewHolder.ivIcon, mList.get(position).newsIconUrl);//确定改变哪一个的image
            } else if (myMode == 2) {
                viewHolder.title.setText(mList.get(position).theme_title);//为每一项设置内容
                Log.d("theme_title is ", mList.get(position).theme_title);
                viewHolder.content.setText(mList.get(position).theme_id);
                //if(mList.get(position).theme_images!="") {
                if(mList.get(position).theme_images!=null) {
                    new ImageLoader().showImage(viewHolder.ivIcon, mList.get(position).theme_images);//确定改变哪一个的image
                    Log.d("theme_images is", mList.get(position).theme_images);
                }
            }

        return convertView;
    }

    class ViewHolder{
        public TextView title;
        public TextView content;
        public ImageView ivIcon;
    }

}
