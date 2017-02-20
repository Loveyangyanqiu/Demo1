package com.example.pc.mooc_work;

import android.util.Log;

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

/**
 * Created by pc on 2017/2/13.
 */

public class GetJsonData {
    public   List<NewsBean>  getJsonData(String url,int mode){//url所对应的json格式数据转化为所需要的NewsBean格式对象（List）。
        List<NewsBean> newsBeanList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());//相当于url.openConnection.getInputStream  返回值为InputStream
            String a,b;
            JSONObject jsonObject;
            NewsBean newsBean;
            if (mode == 1){//对主页内容的获取
                try {
                    jsonObject = new JSONObject(jsonString);//把解析的数据放入list
                    JSONArray jsonArray = jsonObject.getJSONArray("stories");//取出里面的data数组，然后遍历，然后可以提出想要的数据。
                    for (int i = 0; i < jsonArray.length(); i++) {//因为data是数组  所以jsonarray中每一个元素就是一个json object
                        jsonObject = jsonArray.getJSONObject(i);
                        newsBean = new NewsBean();
                        a = jsonObject.getString("images");//因为返回的是["http:\/\/pic4.zhimg.com\/0c837a245092260cc418d1d785f09147.jpg"]
                        //所以bitmap不行 所以去除过后使其为http://pic4.zhimg.com/0c837a245092260cc418d1d785f09147.jpg
                        b = a.replace("[", "");
                        b = b.replace("]", "");
                        b = b.replace("\\", "");
                        b = b.replace("\"", "");
                        newsBean.newsIconUrl = b;
                        newsBean.newsTitle = jsonObject.getString("title");
                        newsBean.newsContent = jsonObject.getString("id");
                        newsBeanList.add(newsBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(mode ==2){//对content的内容获取

                try {
                    newsBean = new NewsBean();
                    JSONObject jsonObject1 = new JSONObject(jsonString);
                    newsBean.contentBody = jsonObject1.getString("body");
                  //  JSONArray jsonArray = new JSONArray("css");
                    newsBean.newsCss = jsonObject1.getString("css");
                    if(jsonObject1.has("image")) {
                        newsBean.contentImage = jsonObject1.getString("image");
                    }
                    a = newsBean.newsCss;
                    a = a.replace("[", "");
                    a = a.replace("]", "");
                    a = a.replace("\\", "");
                   // a = a.replace("\"", "");
                    if (jsonObject1.has("image_source")) {
                        newsBean.imageResoure = jsonObject1.getString("image_source");
                    }
                    newsBean.newsCss = a;
                    Log.d("Are you ok?",newsBean.contentBody);
                    newsBeanList.add(newsBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(mode == 3){
                try {
                    JSONObject jsonObject2 = new JSONObject(jsonString);
                    JSONArray jsonArray2 = jsonObject2.getJSONArray("top_stories");
                    for (int i = 0;i < jsonArray2.length(); i++) {
                        jsonObject2 = jsonArray2.getJSONObject(i);
                        newsBean = new NewsBean();
                        newsBean.top_title = jsonObject2.getString("title");
                        Log.d("Title",newsBean.top_title);
                        newsBean.top_id = jsonObject2.getString("id");
                        Log.d("ID",newsBean.top_id);
                        newsBean.top_image = jsonObject2.getString("image");
                        Log.d("image",newsBean.top_image);
                        newsBeanList.add(newsBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(mode == 4){
                try {
                    JSONObject jsonObject4;
                    jsonObject4 = new JSONObject(jsonString);//把解析的数据放入list
                    JSONArray jsonArray4 = jsonObject4.getJSONArray("stories");//取出里面的data数组，然后遍历，然后可以提出想要的数据。

                    for (int i = 0; i < jsonArray4.length(); i++) {//因为data是数组  所以jsonarray中每一个元素就是一个json object
                        jsonObject4 = jsonArray4.getJSONObject(i);
                        newsBean = new NewsBean();
                        if( !jsonObject4.has("images")){
                            newsBean.theme_title = jsonObject4.getString("title");
                            newsBean.theme_id = jsonObject4.getString("id");
                        }else {
                            a = jsonObject4.getString("images");//因为返回的是["http:\/\/pic4.zhimg.com\/0c837a245092260cc418d1d785f09147.jpg"]
                            //所以bitmap不行 所以去除过后使其为http://pic4.zhimg.com/0c837a245092260cc418d1d785f09147.jpg
                            b = a.replace("[", "");
                            b = b.replace("]", "");
                            b = b.replace("\\", "");
                            b = b.replace("\"", "");
                            newsBean.theme_images = b;
                            Log.d("图片是", b);
                            newsBean.theme_title = jsonObject4.getString("title");
                            newsBean.theme_id = jsonObject4.getString("id");
                        }
                        newsBeanList.add(newsBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(mode == 5){
                try {
                    newsBean = new NewsBean();
                    JSONObject jsonObject5 = new JSONObject(jsonString);
                    newsBean.theme_description = jsonObject5.getString("description");
                    newsBean.theme_background = jsonObject5.getString("background");
                    Log.d("Are you ok?",newsBean.theme_background);
                    newsBeanList.add(newsBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }
    private String readStream(InputStream is){//读取网上返回的stream
        InputStreamReader isr;
        String result = "";
        try {
            String line;
            isr = new InputStreamReader(is,"utf-8");//字节流转化为字符流
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine())!=null){
                result +=line;//每一行拼接到result里面还可以用StringBuilder一个result result.append（line）
            }
        }catch (Exception  e) {
            e.printStackTrace();
        }
        return result;
    }
}
