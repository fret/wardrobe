package com.example.dopamine.wardrobe.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.dopamine.wardrobe.Models.Bottom;
import com.example.dopamine.wardrobe.Models.Top;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        //File image = new File(path);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static byte[] bitmapToByteArray(Bitmap imageBitmap) {

        if (imageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteData = baos.toByteArray();

            return byteData;
        } else
            return null;

    }

    public static Bitmap byteToBitmap(byte[] data) {

        if (data == null)
            return null;
        else
            return (new BitmapDrawable(BitmapFactory.decodeByteArray(data, 0, data.length))).getBitmap();
    }

    public static String saveToInternalSorageTop(Context context, Top top){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String imageName=top.getPath().split("/")[top.getPath().split("/").length-1];
        File mypath=new File(directory, imageName);

        //FileOutputStream fos = null;
        try {
            //fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            copyFile(new File(top.getPath()), mypath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return directory.getAbsolutePath();
    }

    public static String saveToInternalSorageBottom(Context context, Bottom bottom){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String imageName=bottom.getPath().split("/")[bottom.getPath().split("/").length-1];
        File mypath=new File(directory, imageName);

        //FileOutputStream fos = null;
        try {
            //fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            copyFile(new File(bottom.getPath()), mypath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String path, String imageName)
    {
        Bitmap b=null;
        try {
            File f=new File(path, imageName);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return b;
    }

    private static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}