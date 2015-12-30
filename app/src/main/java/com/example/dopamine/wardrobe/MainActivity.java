package com.example.dopamine.wardrobe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.dopamine.wardrobe.Adapters.BottomViewPagerAdapter;
import com.example.dopamine.wardrobe.Adapters.TopViewPagerAdapter;
import com.example.dopamine.wardrobe.Database.AppDB;
import com.example.dopamine.wardrobe.Models.Bottom;
import com.example.dopamine.wardrobe.Models.Top;
import com.example.dopamine.wardrobe.Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Activity activity;
    private String mCurrentPhotoPath;
    private String mCurrentFileName;
    private int REQUEST_TAKE_TOP_PHOTO = 1;
    private int RESULT_LOAD_IMG_TOP = 2;
    private int REQUEST_TAKE_BOTTOM_PHOTO = 3;
    private int RESULT_LOAD_IMG_BOTTOM = 4;
    private AppDB appDB;
    private TopViewPagerAdapter topViewPagerAdapter;
    private BottomViewPagerAdapter bottomViewPagerAdapter;
    private int reqWidth;
    private int reqLength;
    private int mCurrentTop;
    private int mCurrentBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        appDB=new AppDB(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ViewPager topViewPager = (ViewPager)findViewById(R.id.topViewPager);
        ViewPager bottomViewPager = (ViewPager)findViewById(R.id.bottomViewPager);

        topViewPagerAdapter=new TopViewPagerAdapter(this, width, height);
        bottomViewPagerAdapter=new BottomViewPagerAdapter(this, width, height);

        (topViewPager).setAdapter(topViewPagerAdapter);
        (bottomViewPager).setAdapter(bottomViewPagerAdapter);

        topViewPager.setOnPageChangeListener(onTopPageChangeListener);
        bottomViewPager.setOnPageChangeListener(onBottomPageChangeListener);

        findViewById(R.id.topAddButton).setOnClickListener(topAddButtonListener);
        findViewById(R.id.bottomAddButton).setOnClickListener(bottomAddButtonListener);
        findViewById(R.id.favouriteButton).setOnClickListener(favouriteButtonListener);
        findViewById(R.id.shuffleButton).setOnClickListener(shuffleButtonListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener topAddButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new BottomSheet.Builder(activity, R.style.BottomSheet_StyleDialog).title("Select Source").grid().sheet(R.menu.bottom_sheet_camera).listener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case R.id.camera:
                            dispatchTakePictureIntent(REQUEST_TAKE_TOP_PHOTO);
                            break;
                        case R.id.gallery:
                            startGallery(RESULT_LOAD_IMG_TOP);
                            break;
                    }
                }
            }).show();
        }
    };

    View.OnClickListener bottomAddButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new BottomSheet.Builder(activity, R.style.BottomSheet_StyleDialog).title("Select Source").grid().sheet(R.menu.bottom_sheet_camera).listener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case R.id.camera:
                            dispatchTakePictureIntent(REQUEST_TAKE_BOTTOM_PHOTO);
                            break;
                        case R.id.gallery:
                            startGallery(RESULT_LOAD_IMG_BOTTOM);
                            break;
                    }
                }
            }).show();
        }
    };

    View.OnClickListener favouriteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            long res = appDB.addFav(mCurrentTop, mCurrentBottom);
            Log.v("long_res", Long.toString(res));
            if(res > 0 ){
                Toast.makeText(getBaseContext(), "Added successfully", Toast.LENGTH_SHORT).show();
            }
            else if (res == 0) {
                Toast.makeText(getBaseContext(), "Already added", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener shuffleButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((ViewPager)findViewById(R.id.topViewPager)).setCurrentItem(1);
            ((ViewPager)findViewById(R.id.bottomViewPager)).setCurrentItem(0);
        }
    };

    ViewPager.OnPageChangeListener onTopPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentTop=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    ViewPager.OnPageChangeListener onBottomPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentBottom=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void dispatchTakePictureIntent(int REQUEST_TYPE) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TYPE);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_WARD" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = storageDir.getAbsolutePath() + "/" + imageFileName + ".jpg";
        mCurrentPhotoPath=currentPhotoPath;
        mCurrentFileName=imageFileName;
        File image = new File(currentPhotoPath);
        return image;
    }

    private void startGallery(int REQUEST_TYPE) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, REQUEST_TYPE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMG_TOP && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mCurrentPhotoPath = cursor.getString(columnIndex);
            //mCurrentFileName = mCurrentPhotoPath.split("/")[mCurrentPhotoPath.split("/").length-1];
            cursor.close();
            //Bitmap bitmap = Utils.decodeSampledBitmapFromFile(mCurrentPhotoPath, (findViewById(R.id.topViewPager)).getMeasuredWidth(), (findViewById(R.id.topViewPager)).getMeasuredHeight());
            appDB.addTop(new Top(mCurrentPhotoPath));
            topViewPagerAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_TAKE_TOP_PHOTO && resultCode == RESULT_OK) {
            appDB.addTop(new Top(mCurrentPhotoPath));
            //appDB.addTop(new Top(mCurrentPhotoPath, mCurrentFileName));
            topViewPagerAdapter.notifyDataSetChanged();
        }
        if (requestCode == RESULT_LOAD_IMG_BOTTOM && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mCurrentPhotoPath = cursor.getString(columnIndex);
            cursor.close();
            appDB.addBottom(new Bottom(mCurrentPhotoPath));
            bottomViewPagerAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_TAKE_BOTTOM_PHOTO && resultCode == RESULT_OK) {
            appDB.addBottom(new Bottom(mCurrentPhotoPath));
            bottomViewPagerAdapter.notifyDataSetChanged();
        }

    }
}