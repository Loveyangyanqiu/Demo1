package com.example.pc.mooc_work;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    private List<View> views = null;
    private Context mContext;
    private  List<NewsBean> newsBeanList = new ArrayList<>();
    public MyViewPagerAdapter(List<View> views,List<NewsBean> newsBeanList,Context mContext) {
        super();
        this.mContext = mContext;
        this.views = views;
        this.newsBeanList = newsBeanList;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ((ViewPager) container).addView(views.get(position));
        View view = views.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Log.d("Item Click","你点击了"+position+"第个图片");
             Log.d("It's id is ",newsBeanList.get(position).top_id);
                String data = newsBeanList.get(position).top_id;
               Intent intent = new Intent(mContext,News_content.class);
                intent.putExtra("extra_data",data);
                mContext.startActivity(intent);
            }
        });


        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
