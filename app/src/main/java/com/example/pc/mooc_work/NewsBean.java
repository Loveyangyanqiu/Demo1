package com.example.pc.mooc_work;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class NewsBean {
    public String newsTitle;
    public String newsIconUrl;
    public String newsContent;
    public String newsCss;
    public String contentBody;
    public String title;
    public String top_image;
    public String top_title;
    public String top_id;
    public String theme_title;
    public String theme_id;
    public String theme_images;
    public String theme_background;
    public String theme_description;
    public String contentImage;
    public String imageResoure;
    public String getContentBody(){
        return contentBody;
    }
    public void setContentBody(String contentBody){
        this.contentBody = contentBody;
    }
    public String Title(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

}
