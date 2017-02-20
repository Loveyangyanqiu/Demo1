package com.example.pc.mooc_work;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;
    private ListView mListView;
    private  List<NewsBean> newsBeanList = new ArrayList<>();
    private  List<NewsBean> homepage_url = new ArrayList<>();
    private List<View> list =  new ArrayList<View>();
    private MyViewPagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    public  static String fuckyou ;
    private static String URL = "http://news-at.zhihu.com/api/4/news/latest";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.mode_night:
                Toast.makeText(this,"还未有夜间模式功能",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments=new ArrayList<Fragment>();

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });


        mListView = (ListView) findViewById(R.id.lv_main);

        View headerView = LayoutInflater.from(this).inflate(R.layout.list_head, null);
        mViewPager = (ViewPager) headerView.findViewById(R.id.viewpager);

       // initPageData();
       // pagerAdapter = new MyViewPagerAdapter(list);
        //mViewPager.setAdapter(pagerAdapter);
        new  NewsAsyncTask1().execute("http://news-at.zhihu.com/api/4/news/latest");
        mListView.addHeaderView(headerView);
        new NewsAsyncTask().execute(URL);//这是关键
        //new  NewsAsyncTask1().execute("http://news-at.zhihu.com/api/4/news/latest");
        //这是获取Top_title top_image
        ///ddddddddddddddddddddddddddddddddddd
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean newsbean = newsBeanList.get(position-1);
                Log.d("wewe",newsbean.newsContent);
                //Toast.makeText(MainActivity.this,newsbean.newsContent,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,News_content.class);
                String data = newsbean.newsContent;
                intent.putExtra("extra_data",data);
                startActivity(intent);
            }
        });
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu2);
        }
         navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sports://id为8
                        Toast.makeText(MainActivity.this,"进入体育运动专题",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,ThemeActivity.class);
                        intent.putExtra("theme_data","8");
                        startActivity(intent);
                        break;
                    case R.id.start_game:
                        Toast.makeText(MainActivity.this,"进入开始游戏专题",Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainActivity.this,ThemeActivity.class);
                        intent1.putExtra("theme_data","2");
                        startActivity(intent1);
                        break;
                    case R.id.big_company:
                        Toast.makeText(MainActivity.this,"进入大公司专题",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(MainActivity.this,ThemeActivity.class);
                        intent2.putExtra("theme_data","5");
                        startActivity(intent2);
                        break;
                    case R.id.film:
                        Toast.makeText(MainActivity.this,"进入电影专题",Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(MainActivity.this,ThemeActivity.class);
                        intent3.putExtra("theme_data","3");
                        startActivity(intent3);
                        break;
                    case R.id.design:
                        Toast.makeText(MainActivity.this,"进入设计专题",Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(MainActivity.this,ThemeActivity.class);
                        intent4.putExtra("theme_data","4");
                        startActivity(intent4);
                        break;
                    case R.id.yonghu_tuijian:
                        Toast.makeText(MainActivity.this,"进入用户推荐专题",Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(MainActivity.this,ThemeActivity.class);
                        intent5.putExtra("theme_data","12");
                        startActivity(intent5);
                        break;
                    case R.id.hulianwang_anquan:
                        Toast.makeText(MainActivity.this,"进入互联网安全专题",Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(MainActivity.this,ThemeActivity.class);
                        intent6.putExtra("theme_data","10");
                        startActivity(intent6);
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void refreshFruits() {
        new NewsAsyncTask().execute(URL);
        swipeRefresh.setRefreshing(false);
        Toast.makeText(this,"刷新新闻成功！",Toast.LENGTH_SHORT).show();
    }



    //实现网络的异步访问
    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>>{//每一行NewsBean代表一组数据
         //getJson需要在子线程中进行  用asynctask方便而且好返回list   不然用handler
        @Override
        protected List<NewsBean> doInBackground(String... params) {
            List<NewsBean> beanList = new ArrayList<>();
            //beanList = getJsonData(URL);
            //beanList = get;//params只有一个就是请求的网址。  URL
            GetJsonData getjson = new GetJsonData();
            beanList = getjson.getJsonData(URL,1);
            return beanList;
        }

          @Override
          protected void onPostExecute( List<NewsBean> newsBeans) {
              super.onPostExecute(newsBeans);
              NewsAdapter adapter = new NewsAdapter(MainActivity.this,newsBeans,1);//根据所返回的list（NewsBeanList）配置相应的Adapter
              mListView.setAdapter(adapter);
              newsBeanList = newsBeans;
          }
      }
    class NewsAsyncTask1 extends AsyncTask<String,Void,List<NewsBean>>{//每一行NewsBean代表一组数据
        //getJson需要在子线程中进行  用asynctask方便而且好返回list   不然用handler
        @Override
        protected List<NewsBean> doInBackground(String... params) {
            List<NewsBean> beanList = new ArrayList<>();
            //beanList = getJsonData(URL);
            //beanList = get;//params只有一个就是请求的网址。  URL
            GetJsonData getjson = new GetJsonData();
            beanList = getjson.getJsonData("http://news-at.zhihu.com/api/4/news/latest",3);
            return beanList;
        }

        @Override
        protected void onPostExecute( List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);

            View view1= LayoutInflater.from(MainActivity.this).inflate(R.layout.pager_item, null);
            ImageView imageView1 = (ImageView) view1.findViewById(R.id.pager_img);
            imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            TextView textView1 = (TextView) view1.findViewById(R.id.pager_text);
            textView1.setText(newsBeans.get(0).top_title);
            ImageLoader imageLoader1 = new ImageLoader();
            imageLoader1.showImage(imageView1,newsBeans.get(0).top_image);
            list.add(view1);

            View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.pager_item, null);
            ImageView imageView2 = (ImageView) view2.findViewById(R.id.pager_img);
            imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            TextView textView2 = (TextView) view2.findViewById(R.id.pager_text);
            textView2.setText(newsBeans.get(1).top_title);
            ImageLoader imageLoader2 = new ImageLoader();
            imageLoader2.showImage(imageView2,newsBeans.get(1).top_image);
            list.add(view2);

            View view3= LayoutInflater.from(MainActivity.this).inflate(R.layout.pager_item, null);
            ImageView imageView3 = (ImageView) view3.findViewById(R.id.pager_img);
            imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
            TextView textView3 = (TextView) view3.findViewById(R.id.pager_text);
            textView3.setText(newsBeans.get(2).top_title);
            ImageLoader imageLoader3 = new ImageLoader();
            imageLoader3.showImage(imageView3,newsBeans.get(2).top_image);
            list.add(view3);

            View view4 = LayoutInflater.from(MainActivity.this).inflate(R.layout.pager_item, null);
            ImageView imageView4 = (ImageView) view4.findViewById(R.id.pager_img);
            imageView4.setScaleType(ImageView.ScaleType.CENTER_CROP);
            TextView textView4 = (TextView) view4.findViewById(R.id.pager_text);
            textView4.setText(newsBeans.get(3).top_title);
            ImageLoader imageLoader4 = new ImageLoader();
            imageLoader4.showImage(imageView4,newsBeans.get(3).top_image);
            list.add(view4);

            View view5 = LayoutInflater.from(MainActivity.this).inflate(R.layout.pager_item, null);
            ImageView imageView5 = (ImageView) view5.findViewById(R.id.pager_img);
            imageView5.setScaleType(ImageView.ScaleType.CENTER_CROP);
            TextView textView5 = (TextView) view5.findViewById(R.id.pager_text);
            textView5.setText(newsBeans.get(4).top_title);
            ImageLoader imageLoader5 = new ImageLoader();
            imageLoader5.showImage(imageView5,newsBeans.get(4).top_image);
            list.add(view5);


            pagerAdapter = new MyViewPagerAdapter(list,newsBeans,MainActivity.this);
            mViewPager.setAdapter(pagerAdapter);
        }
    }
    }




