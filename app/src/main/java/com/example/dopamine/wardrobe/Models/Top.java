package com.example.dopamine.wardrobe.Models;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.dopamine.wardrobe.Utils.Utils;

/**
 * Created by Dopamine on 12/30/2015.
 */
public class Top {
    private Bitmap topBitmap;
    private int top_pk;
    private String path;
    private int reqWidth;
    private int reqHeight;
    //private String fileName;

    public Top(){}

    public Top(String path) {
        this.path=path;
    }

    public Top(String path, int top_pk) {
        this.path=path;
        this.top_pk=top_pk;
    }

    /*
    public Top(String path, String fileName) {

        this.path=path;
        this.fileName=fileName;
    }
    */

    /*
    public void setTopImage(byte[] pictureData) {
        topBitmap = Utils.byteToBitmap(pictureData);
    }

    public byte[] getPictureData(){
        return Utils.bitmapToByteArray(topBitmap);
    }
    */


    public int getTop_pk() {
        return top_pk;
    }

    public void setTop_pk(int top_pk) {
        this.top_pk = top_pk;
    }

    /*
    public Bitmap getTopBitmap(){
        return topBitmap;
    }

    public void setTopBitmap(Bitmap topBitmap) {
        this.topBitmap = topBitmap;
    }
    */

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /*
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    */

    public void setReqHeight(int reqHeight) {
        this.reqHeight = reqHeight;
    }

    public int getReqHeight() {
        return reqHeight;
    }

    public void setReqWidth(int reqWidth) {
        this.reqWidth = reqWidth;
    }

    public int getReqWidth() {
        return reqWidth;
    }
}