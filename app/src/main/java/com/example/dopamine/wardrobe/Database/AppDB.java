package com.example.dopamine.wardrobe.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dopamine.wardrobe.Models.Bottom;
import com.example.dopamine.wardrobe.Models.Top;
import com.example.dopamine.wardrobe.R;
import com.example.dopamine.wardrobe.Utils.Utils;

/**
 * Created by Dopamine on 12/30/2015.
 */
public class AppDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "wardrobe.db";
    public static final String TOP_TABLE_NAME = "top";
    public static final String TOP_COLUMN_ID = "id";
    public static final String TOP_COLUMN_IMAGE_PATH = "path";
    public static final String BOTTOM_TABLE_NAME = "bottom";
    public static final String BOTTOM_COLUMN_ID = "id";
    public static final String BOTTOM_COLUMN_IMAGE_PATH = "path";
    public static final String FAV_TABLE_NAME = "favourite";
    public static final String FAV_COLUMN_ID = "id";
    public static final String FAV_COLUMN_TOP_ID = "top_id";
    public static final String FAV_COLUMN_BOTTOM_ID = "bottom_id";
    private static final String CREATE_TOP_TABLE = "create table "
            + TOP_TABLE_NAME + " (" + TOP_COLUMN_ID
            + " integer primary key , " + TOP_COLUMN_IMAGE_PATH
            + " text not null);";
    private static final String CREATE_BOTTOM_TABLE = "create table "
            + BOTTOM_TABLE_NAME + " (" + BOTTOM_COLUMN_ID
            + " integer primary key , " + BOTTOM_COLUMN_IMAGE_PATH
            + " text not null);";
    private static final String CREATE_FAV_TABLE = "create table "
            + FAV_TABLE_NAME + " (" + FAV_COLUMN_ID
            + " integer primary key , " + FAV_COLUMN_TOP_ID
            + " integer not null , " + FAV_COLUMN_BOTTOM_ID + " integer not null , "+
            "FOREIGN KEY(" + FAV_COLUMN_TOP_ID +") REFERENCES " +  TOP_TABLE_NAME + "(" + TOP_COLUMN_ID + ")," +
            "FOREIGN KEY(" + FAV_COLUMN_BOTTOM_ID +") REFERENCES " +  BOTTOM_TABLE_NAME + "(" + BOTTOM_COLUMN_ID + "));";
    Context mContext;

    public AppDB(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TOP_TABLE);
        db.execSQL(CREATE_BOTTOM_TABLE);
        db.execSQL(CREATE_FAV_TABLE);

        /*
        ContentValues values = new ContentValues();
        values.put(TOP_COLUMN_IMAGE_PATH, Utils.bitmapToByteArray(Utils.decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.cba, 100, 100)));
        db.insert(TOP_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TOP_COLUMN_IMAGE, Utils.bitmapToByteArray(Utils.decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.cba, 100, 100)));
        db.insert(TOP_TABLE_NAME, null, values);


        ContentValues values = new ContentValues();
        values.put(BOTTOM_COLUMN_IMAGE, Utils.bitmapToByteArray(Utils.decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.cba, 100, 100)));
        db.insert(BOTTOM_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(BOTTOM_COLUMN_IMAGE, Utils.bitmapToByteArray(Utils.decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.cba, 100, 100)));
        db.insert(BOTTOM_TABLE_NAME, null, values);
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TOP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOTTOM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FAV_TABLE_NAME);
        onCreate(db);
    }

    /*
    public void insertTop(Top top) {
        ContentValues cv = new ContentValues();
        cv.put(EMP_PHOTO, Utility.getBytes(employee.getBitmap()));
        cv.put(EMP_NAME, employee.getName());
        cv.put(EMP_AGE, employee.getAge());
        mDb.insert(EMPLOYEES_TABLE, null, cv);
    }

    public Employee retriveEmpDetails() throws SQLException {
        Cursor cur = mDb.query(true, EMPLOYEES_TABLE, new String[] { EMP_PHOTO,
                EMP_NAME, EMP_AGE }, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            byte[] blob = cur.getBlob(cur.getColumnIndex(EMP_PHOTO));
            String name = cur.getString(cur.getColumnIndex(EMP_NAME));
            int age = cur.getInt(cur.getColumnIndex(EMP_AGE));
            cur.close();
            return new Employee(Utility.getPhoto(blob), name, age);
        }
        */

    public long addTop(Top top){
        SQLiteDatabase db = this.getWritableDatabase();
        String path = Utils.saveToInternalSorageTop(mContext, top);
        ContentValues values = new ContentValues();
        values.put(TOP_COLUMN_IMAGE_PATH, top.getPath());

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(TOP_TABLE_NAME, null, values);
        return newRowId;
    }

    public Top getTop(int position){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + TOP_COLUMN_ID + "," + TOP_COLUMN_IMAGE_PATH + " FROM " + TOP_TABLE_NAME + " ORDER BY " + TOP_COLUMN_ID + " LIMIT " + Integer.toString(position) + ",1;", null);
        if( c != null && c.moveToFirst() ){
        Top t = new Top(c.getString(c.getColumnIndex(TOP_COLUMN_IMAGE_PATH)), c.getInt(c.getColumnIndex(TOP_COLUMN_ID)));
        c.close();
        return t;
        }
        else {
            Log.v("Cursor", "Cursor Null");
            return null;
        }
    }

    public int getTopCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + TOP_COLUMN_ID + " FROM " + TOP_TABLE_NAME + ";", null);
        int count=c.getCount();
        c.close();
        return count;
    }

    public long addBottom(Bottom bottom){
        SQLiteDatabase db = this.getWritableDatabase();
        String path = Utils.saveToInternalSorageBottom(mContext, bottom);
        ContentValues values = new ContentValues();
        values.put(BOTTOM_COLUMN_IMAGE_PATH, bottom.getPath());

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(BOTTOM_TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public Bottom getBottom(int position){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + BOTTOM_COLUMN_ID + "," + BOTTOM_COLUMN_IMAGE_PATH + " FROM " + BOTTOM_TABLE_NAME + " ORDER BY " + BOTTOM_COLUMN_ID + " LIMIT " + Integer.toString(position) + ",1;", null);
        if( c != null && c.moveToFirst() ){
            Bottom b = new Bottom(c.getString(c.getColumnIndex(BOTTOM_COLUMN_IMAGE_PATH)), c.getInt(c.getColumnIndex(BOTTOM_COLUMN_ID)));
            c.close();
            return b;
        }
        else {
            Log.v("Cursor", "Cursor Null");
            return null;
        }
    }

    public int getBottomCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + BOTTOM_COLUMN_ID + " FROM " + BOTTOM_TABLE_NAME + ";", null);
        int count=c.getCount();
        c.close();
        return count;
    }

    public long addFav(int top, int bottom){
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "SELECT " + FAV_COLUMN_ID + " FROM " + FAV_TABLE_NAME + " WHERE "+ FAV_COLUMN_TOP_ID+ "=" + Integer.toString(getTop(top).getTop_pk()) + " and " + FAV_COLUMN_BOTTOM_ID + "=" + Integer.toString(getBottom(bottom).getBottom_pk()) + ";";
        Log.v("query", q);
        Cursor c = db.rawQuery("SELECT " + FAV_COLUMN_ID + " FROM " + FAV_TABLE_NAME + " WHERE "+ FAV_COLUMN_TOP_ID+ "=" + Integer.toString(getTop(top).getTop_pk()) + " and " + FAV_COLUMN_BOTTOM_ID + "=" + Integer.toString(getBottom(bottom).getBottom_pk()) + ";", null);
        int count=c.getCount();
        c.close();
        Log.v("count", Integer.toString(count));
        if(count>0)
            return 0;
        ContentValues values = new ContentValues();
        values.put(FAV_COLUMN_TOP_ID, getTop(top).getTop_pk());
        values.put(FAV_COLUMN_BOTTOM_ID, getBottom(bottom).getBottom_pk());

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(FAV_TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }
}