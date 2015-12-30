package com.example.dopamine.wardrobe.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dopamine.wardrobe.Database.AppDB;
import com.example.dopamine.wardrobe.Utils.Utils;

/**
 * Created by Dopamine on 12/30/2015.
 */
public class BottomViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private AppDB appDB;
    private ViewPager viewPager;
    private int width;
    private int height;

    public BottomViewPagerAdapter(Context context, int width, int height){
        mContext=context;
        appDB=new AppDB(mContext);
        this.width=width;
        this.height=height;
    }

    @Override
    public int getCount() {
        return appDB.getBottomCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o==view;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int reqWidth=0,reqHeight=0;
        if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            reqHeight=height/2;
            reqWidth=width;
        }
        else {
            reqHeight=height;
            reqWidth=width/2;
        }
        imageView.setImageBitmap(Utils.decodeSampledBitmapFromFile(appDB.getBottom(position).getPath(), reqWidth, reqHeight));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}