package com.example.dopamine.wardrobe.Models;

/**
 * Created by Dopamine on 1/12/2016.
 */
public class FavComb {
    private Top top;
    private Bottom bottom;
    private String timestamp;

    public FavComb(Top top, Bottom bottom){
        this.top=top;
        this.bottom=bottom;
    }

    public Top getTop(){
        return top;
    }

    public Bottom getBottom(){
        return bottom;
    }

    public void setTimestamp(String timestamp){
        this.timestamp=timestamp;
    }

    public String getTimestamp(){
        return timestamp;
    }
}