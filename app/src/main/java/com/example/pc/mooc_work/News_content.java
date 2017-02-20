package com.example.pc.mooc_work;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class News_content extends AppCompatActivity {
    WebView webView;
    ImageView imageView;
    private String htmlImage;
    private String htmlImageRousouse;
    private String htmlMode = "<img class=\"avatar\" src=";
    private  List<NewsBean> newsBeanList = new ArrayList<>();
    public String id,body ;
    private String contentUrl = "http://news-at.zhihu.com/api/4/news/";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        Intent intent = getIntent();
        id = intent.getStringExtra("extra_data");//data是新闻内容对应的ID
        Log.d("extra_data is ",id);
        new NewsAsyncTask().execute(contentUrl+id);
        webView = (WebView) findViewById(R.id.web_content_view);
    }
    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>>{

        @Override
        protected List<NewsBean> doInBackground(String... params) {
            List<NewsBean> beanList = new ArrayList<>();
            GetJsonData getjson = new GetJsonData();
            beanList = getjson.getJsonData(params[0],2);
            return beanList;
        }
        @Override
        protected void onPostExecute(List<NewsBean> newsBeen) {
            super.onPostExecute(newsBeen);
            newsBeanList = newsBeen;
            body =  newsBeanList.get(0).contentBody;
            Log.d("my_body",body);
            String css = newsBeanList.get(0).newsCss;
            if(newsBeanList.get(0).contentImage!=null) {
                htmlImage = newsBeanList.get(0).contentImage;

                htmlImageRousouse = "<p>" +newsBeanList.get(0).imageResoure +"</p>";

                body = htmlMode+htmlImage+">" +htmlImageRousouse+ body;
            }

            Log.d("css is",css);
            body = "<head><style>img{max-width:320px !important;}</style></head>"+body;
            //textView.setText(body);
            webView.loadDataWithBaseURL(css,body,"text/html","UTF-8",null);
         //   webView.getSettings().setUseWideViewPort(true);
         //   webView.getSettings().setLoadWithOverviewMode(true);

        }
    }
}
