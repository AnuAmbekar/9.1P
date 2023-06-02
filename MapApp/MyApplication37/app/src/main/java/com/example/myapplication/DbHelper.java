package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Util.TABLE_NAME, null, Util.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Util.TABLE_NAME + "(" + Util.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.NAME + " VARCHAR(255), " + Util.PHONE + " VARCHAR(255), " + Util.DESCRIPTION + " VARCHAR(255), "
                + Util.DATE + " VARCHAR(255), " + Util.LOCATION + " VARCHAR(255)," + Util.STATUS + " VARCHAR(255),"
                + Util.LATITUDE + " VARCHAR(255), " + Util.LONGTITUDE + " VARCHAR(255) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + Util.TABLE_NAME );
    }

    public long insertData(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NAME, user.getName());
        contentValues.put(Util.PHONE, user.getPhone());
        contentValues.put(Util.DESCRIPTION, user.getDescription());
        contentValues.put(Util.DATE, user.getDate());
        contentValues.put(Util.LOCATION, user.getLocation());
        contentValues.put(Util.STATUS, user.getStatus());
        contentValues.put(Util.LATITUDE, user.getLatitude());
        contentValues.put(Util.LONGTITUDE, user.getLongtitude());

        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + Util.TABLE_NAME + " where " + Util.ID + " = " + id);
    }

    public boolean getUser(String name, String description, String phone, String date, String location, String status, String latitude, String longtitude) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.ID}, Util.NAME + "=? and "
                        + Util.PHONE + "=? and " + Util.DESCRIPTION + "=? and " + Util.STATUS + "=> and " + Util.DATE + "=? and " + Util.LOCATION + "=? and " + Util.LATITUDE + "=? and " + Util.LONGTITUDE + "=?",
                new String[]{name, phone, description, date, location, status, latitude, longtitude}, null, null, null, null);
        int numRows = cursor.getCount();
        db.close();

        if (numRows > 0) {
            return true;
        }

        else {
            return false;
        }

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Util.TABLE_NAME, null);
        return cursor;
    }
}
