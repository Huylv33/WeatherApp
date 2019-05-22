package com.project.mobile.weatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.mobile.weatherapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeatherDB extends SQLiteOpenHelper {
    private static final String TAG = CurrentWeatherDB.class.getSimpleName();
    private static final String TABLE_NAME = CurrentWeatherDB.class.getName();
    public CurrentWeatherDB(Context context) {
        super(context, Constants.WeatherDatabaseTable.DB_NAME, null, Constants.WeatherDatabaseTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.WeatherDatabaseTable.CREATE_TABLE_QUERY);
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(Constants.WeatherDatabaseTable.DROP_QUERY);
        this.onCreate(db);
        db.close();

    }

    public void deleteProducts() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.WeatherDatabaseTable.TABLE_NAME, null, null);
        db.close();

    }

    public void addDataInDB(String s, String s0, String s1, String s2, String s3, String s4,
                            String s5, String s6, String s7, String s8, String s9, String s10) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.WeatherDatabaseTable.MAIN, s);
        values.put(Constants.WeatherDatabaseTable.DESCRIPTION, s0);
        values.put(Constants.WeatherDatabaseTable.ICON, s1);
        values.put(Constants.WeatherDatabaseTable.TEMP, s2);
        values.put(Constants.WeatherDatabaseTable.HUMIDITY, s3);
        values.put(Constants.WeatherDatabaseTable.TEMP_MIN, s4);
        values.put(Constants.WeatherDatabaseTable.TEMP_MAX, s5);
        values.put(Constants.WeatherDatabaseTable.SPEED, s6);
        values.put(Constants.WeatherDatabaseTable.COUNTRY, s7);
        values.put(Constants.WeatherDatabaseTable.SUNRISE, s8);
        values.put(Constants.WeatherDatabaseTable.SUNSET, s9);
        values.put(Constants.WeatherDatabaseTable.NAME, s10);

        try {

            db.insert(Constants.WeatherDatabaseTable.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        db.close();

    }

    public List<DbCurrentWeatherModel> getAllData() {
        List<DbCurrentWeatherModel> contactList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Constants.WeatherDatabaseTable.GET_PRODUCTS_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                DbCurrentWeatherModel contact = new DbCurrentWeatherModel();
                contact.setMain(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.MAIN)));
                contact.setDescription(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.DESCRIPTION)));
                contact.setIcon(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.ICON)));
                contact.setTemp(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.TEMP)));
                contact.setHumidity(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.HUMIDITY)));
                contact.setTemp_min(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.TEMP_MIN)));
                contact.setTemp_max(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.TEMP_MAX)));
                contact.setSpeed(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.SPEED)));
                contact.setCountry(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.COUNTRY)));
                contact.setSunrise(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.SUNRISE)));
                contact.setSunset(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.SUNSET)));
                contact.setName(cursor.getString(cursor.getColumnIndex(Constants.WeatherDatabaseTable.NAME)));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    public void updateValues(String x, String s, String s0, String s1, String s2, String s3, String s4, String s5,
                             String s6, String s7, String s8, String s9, String s10) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.WeatherDatabaseTable.MAIN, s);
        values.put(Constants.WeatherDatabaseTable.DESCRIPTION, s0);
        values.put(Constants.WeatherDatabaseTable.ICON, s1);
        values.put(Constants.WeatherDatabaseTable.TEMP, s2);
        values.put(Constants.WeatherDatabaseTable.HUMIDITY, s3);
        values.put(Constants.WeatherDatabaseTable.TEMP_MIN, s4);
        values.put(Constants.WeatherDatabaseTable.TEMP_MAX, s5);
        values.put(Constants.WeatherDatabaseTable.SPEED, s6);
        values.put(Constants.WeatherDatabaseTable.COUNTRY, s7);
        values.put(Constants.WeatherDatabaseTable.SUNRISE, s8);
        values.put(Constants.WeatherDatabaseTable.SUNSET, s9);
        values.put(Constants.WeatherDatabaseTable.NAME, s10);

        // updating row
        db.update(Constants.WeatherDatabaseTable.TABLE_NAME, values, Constants.WeatherDatabaseTable.KEY_ID + " = ?",
                new String[]{String.valueOf(x)});

        db.close();

    }

    public boolean TableNotEmpty() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + Constants.WeatherDatabaseTable.TABLE_NAME, null);
        Boolean rowExists;

        if (mCursor.moveToFirst()) {
            // DO SOMETHING WITH CURSOR
            mCursor.close();
            rowExists = true;
            db.close();

        } else {
            // I AM EMPTY
            mCursor.close();
            rowExists = false;
            db.close();
        }
        db.close();
        return rowExists;
    }
}

