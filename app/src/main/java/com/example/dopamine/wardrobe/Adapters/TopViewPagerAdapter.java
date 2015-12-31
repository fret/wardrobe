package com.example.dopamine.wardrobe.Adapters;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dopamine.wardrobe.AsyncTask.BitmapWorkerTask;
import com.example.dopamine.wardrobe.Database.AppDB;
import com.example.dopamine.wardrobe.Models.Top;
import com.example.dopamine.wardrobe.R;
import com.example.dopamine.wardrobe.Utils.Utils;

import java.util.zip.Inflater;

public class TopViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private AppDB appDB;
    private int width;
    private int height;

    public TopViewPagerAdapter(Context context, int width, int height){
        mContext=context;
        this.width=width;
        this.height=height;
        appDB=new AppDB(mContext);
    }

    @Override
    public int getCount() {
        return appDB.getTopCount();
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
        imageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.placeholde));
        int reqWidth=0,reqHeight=0;
        if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            reqHeight=height/2;
            reqWidth=width;
        }
        else {
            reqHeight=height;
            reqWidth=width/2;
        }
        loadBitmap(appDB.getTop(position).getPath(), imageView, reqWidth, reqHeight);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    public void loadBitmap(String path, ImageView imageView, int width, int height) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, width, height);
        task.execute(path);
    }

}