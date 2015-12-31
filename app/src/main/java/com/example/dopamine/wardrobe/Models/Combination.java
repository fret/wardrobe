package com.example.dopamine.wardrobe.Models;

/**
 * Created by Dopamine on 12/31/2015.
 */
public class Combination {
    int top_pos;
    int bottom_pos;

    public Combination(int top_pos, int bottom_pos){
        this.top_pos=top_pos;
        this.bottom_pos=bottom_pos;
    }

    public int getBottom_pos() {
        return bottom_pos;
    }

    public void setBottom_pos(int bottom_pos) {
        this.bottom_pos = bottom_pos;
    }

    public int getTop_pos() {
        return top_pos;
    }

    public void setTop_pos(int top_pos) {
        this.top_pos = top_pos;
    }
}
