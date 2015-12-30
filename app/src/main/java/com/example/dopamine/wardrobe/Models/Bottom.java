package com.example.dopamine.wardrobe.Models;

import android.graphics.Bitmap;

import com.example.dopamine.wardrobe.Utils.Utils;

public class Bottom {
    private Bitmap bottomBitmap;
    private int bottom_pk;
    private String path;
    private int reqWidth;
    private int reqHeight;
    //private String fileName;

    public Bottom(){}

    public Bottom(String path) {
        this.path=path;
    }

    public Bottom(String path, int bottom_pk) {
        this.path=path;
        this.bottom_pk=bottom_pk;
    }

    public int getBottom_pk() {
        return bottom_pk;
    }

    public void setBottom_pk(int bottom_pk) {
        this.bottom_pk = bottom_pk;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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