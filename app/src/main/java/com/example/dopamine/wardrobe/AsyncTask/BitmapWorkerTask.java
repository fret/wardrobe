package com.example.dopamine.wardrobe.AsyncTask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.dopamine.wardrobe.Utils.Utils;

import java.lang.ref.WeakReference;

/**
 * Created by Dopamine on 12/31/2015.
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private String data = null;
    int reqWidth;
    int reqHeight;

    public BitmapWorkerTask(ImageView imageView, int reqWidth, int reqHeight) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.reqWidth=reqWidth;
        this.reqHeight=reqHeight;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        data = params[0];
        return Utils.decodeSampledBitmapFromFile(data, reqWidth, reqHeight);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}