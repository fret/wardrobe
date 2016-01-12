package com.example.dopamine.wardrobe.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dopamine.wardrobe.AsyncTask.BitmapWorkerTask;
import com.example.dopamine.wardrobe.Database.AppDB;
import com.example.dopamine.wardrobe.Models.FavComb;
import com.example.dopamine.wardrobe.R;

import org.w3c.dom.Text;

/**
 * Created by Dopamine on 1/12/2016.
 */
public class historyViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private AppDB appDB;
    private ViewPager viewPager;
    private int width;
    private int height;

    public historyViewPagerAdapter(Context context, int width, int height){
        mContext=context;
        appDB=new AppDB(mContext);
        this.width=width;
        this.height=height;
    }

    @Override
    public int getCount() {
        return appDB.getHistoryCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o==view;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        /*
        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.placeholde));
        */
        int reqWidth=0,reqHeight=0;
        if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            reqHeight=height/2;
            reqWidth=width;
        }
        else {
            reqHeight=height;
            reqWidth=width/2;
        }
        //loadBitmap(appDB.getBottom(position).getPath(), imageView, reqWidth, reqHeight);
        //container.addView(imageView);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.history_pager_content, null);
        LinearLayout linearLayout = (LinearLayout)view;
        ImageView topImageView = (ImageView)view.findViewById(R.id.topImageView);
        ImageView bottomImageView = (ImageView)view.findViewById(R.id.bottomImageView);
        FavComb favComb = appDB.getHistory(position);
        ((TextView) view.findViewById(R.id.timestamp)).setText(favComb.getTimestamp());
        loadBitmap(favComb.getTop().getPath(), topImageView, reqWidth, reqHeight);
        loadBitmap(favComb.getBottom().getPath(), bottomImageView, reqWidth, reqHeight);
        container.addView(linearLayout);
        return linearLayout;
    }

    public void loadBitmap(String path, ImageView imageView, int width, int height) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, width, height);
        task.execute(path);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}