package com.example.pc.mooc_work;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public  class ImageLoader{
    ImageView mImage;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImage.setImageBitmap((Bitmap) msg.obj);
        }
    };
    public void  showImage(ImageView image,final String url){//要修改的图片和所对应的URL
        mImage = image;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmapFromURL(url);
                Message message = Message.obtain();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }).start();
    }

    public static Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return  bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}