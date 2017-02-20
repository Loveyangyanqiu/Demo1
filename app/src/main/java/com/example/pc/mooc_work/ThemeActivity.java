package com.example.pc.mooc_work;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/2/17.
 */

public class ThemeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;
    private ListView mListView;
    private List<NewsBean> newsBeanList = new ArrayList<>();
    private  List<NewsBean> homepage_url = new ArrayList<>();
    private List<View> list =  new ArrayList<View>();
    private MyViewPagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private String URL = "http://news-at.zhihu.com/api/4/theme/";
    private ImageView imageView;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        mListView = (ListView) findViewById(R.id.lv_theme);


        Intent intent = getIntent();
        final String data = intent.getStringExtra("theme_data");
        Log.d("URL is ",URL+data);

        View headerView = LayoutInflater.from(this).inflate(R.layout.theme_list_head, null);
        imageView = (ImageView) headerView.findViewById(R.id.theme_head_imageview);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mListView.addHeaderView(headerView);


        new NewsAsyncTask().execute(URL+data);
        new NewsAsyncTask1().execute(URL+data);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    NewsBean newsbean = newsBeanList.get(position - 1);
                    Toast.makeText(ThemeActivity.this, newsbean.theme_id, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThemeActivity.this, News_content.class);
                    String data = newsbean.theme_id;
                    intent.putExtra("extra_data", data);
                    startActivity(intent);
                }
            }
        });


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_theme);

        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_theme);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view_theme);
        ActionBar actionBar = getSupportActionBar();
       if(actionBar!=null) {
           actionBar.setDisplayHomeAsUpEnabled(true);
           actionBar.setHomeAsUpIndicator(R.drawable.menu2);
       }


        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_theme);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits(data);
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sports://id为8
                        Toast.makeText(ThemeActivity.this,"进入体育运动专题",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThemeActivity.this,ThemeActivity.class);
                        intent.putExtra("theme_data","8");
                        startActivity(intent);
                        break;
                    case R.id.start_game:
                        Toast.makeText(ThemeActivity.this,"进入开始游戏专题",Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ThemeActivity.this,ThemeActivity.class);
                        intent1.putExtra("theme_data","2");
                        startActivity(intent1);
                        break;
                    case R.id.big_company:
                        Toast.makeText(ThemeActivity.this,"进入大公司专题",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(ThemeActivity.this,ThemeActivity.class);
                        intent2.putExtra("theme_data","5");
                        startActivity(intent2);
                        break;
                    case R.id.film:
                        Toast.makeText(ThemeActivity.this,"进入电影专题",Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(ThemeActivity.this,ThemeActivity.class);
                        intent3.putExtra("theme_data","3");
                        startActivity(intent3);
                        break;
                    case R.id.design:
                        Toast.makeText(ThemeActivity.this,"进入设计专题",Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(ThemeActivity.this,ThemeActivity.class);
                        intent4.putExtra("theme_data","4");
                        startActivity(intent4);
                        break;
                    case R.id.yonghu_tuijian:
                        Toast.makeText(ThemeActivity.this,"进入用户推荐专题",Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(ThemeActivity.this,ThemeActivity.class);
                        intent5.putExtra("theme_data","12");
                        startActivity(intent5);
                        break;
                    case R.id.hulianwang_anquan:
                        Toast.makeText(ThemeActivity.this,"进入互联网安全专题",Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(ThemeActivity.this,ThemeActivity.class);
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
    private void refreshFruits(String data) {
        new NewsAsyncTask().execute(URL+data);
        swipeRefresh.setRefreshing(false);
        Toast.makeText(this,"刷新新闻成功！",Toast.LENGTH_SHORT).show();
    }
    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>> {//每一行NewsBean代表一组数据
        //getJson需要在子线程中进行  用asynctask方便而且好返回list   不然用handler
        @Override
        protected List<NewsBean> doInBackground(String... params) {
            List<NewsBean> beanList = new ArrayList<>();
            GetJsonData getjson = new GetJsonData();
            beanList = getjson.getJsonData(params[0],4);
            return beanList;
        }

        @Override
        protected void onPostExecute( List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);
            NewsAdapter adapter = new NewsAdapter(ThemeActivity.this,newsBeans,2);//根据所返回的list（NewsBeanList）配置相应的Adapter

            mListView.setAdapter(adapter);
            newsBeanList = newsBeans;
        }
    }
    class NewsAsyncTask1 extends AsyncTask<String,Void,List<NewsBean>> {//每一行NewsBean代表一组数据
        //getJson需要在子线程中进行  用asynctask方便而且好返回list   不然用handler
        @Override
        protected List<NewsBean> doInBackground(String... params) {
            List<NewsBean> beanList = new ArrayList<>();
            GetJsonData getjson = new GetJsonData();
            Log.d("params is ",params[0]);
            beanList = getjson.getJsonData(params[0],5);
            return beanList;
        }

        @Override
        protected void onPostExecute( List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);

           String data =  newsBeans.get(0).theme_background;

            new ImageLoader().showImage(imageView,data);

        }
    }



}
