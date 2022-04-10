package com.example.tutorialapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TutorSQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tutorial.db";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "tutors";
    public static final String KEY_NAME = "name";
    public static final String KEY_ZOOM = "zoomLink";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ID = "id integer primary key autoincrement";
    public static final String CREATE_TABLE = "CREATE TABLE tutors ("
            + KEY_ID + "," + KEY_NAME + " text," + KEY_ZOOM + " text,"
            + KEY_ADDRESS + " text," + KEY_PHONE + " text, " + KEY_EMAIL + " text);";

    private ContentValues values;
    private ArrayList<Tutor> tutorList;
    private Cursor cursor;

    public TutorSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //called to create table
    //NB: this is not a lifecycle method because this class is not an Activity
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = CREATE_TABLE;
        Log.d("SQLiteDemo", "onCreate: " + sql);
        db.execSQL(CREATE_TABLE);
    }

    //called when database version mismatch
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion) return;

        Log.d("SQLiteDemo", "onUpgrade: Version = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);   //not calling a lifecycle method
    }

    //drop table
    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);   //not calling a lifecycle method
    }

    //add tutor to database
    public void addTutor(Tutor item) {
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_ZOOM, item.getZoomLink();
        values.put(KEY_ADDRESS, item.getAddress());
        values.put(KEY_PHONE, item.getPhone());
        values.put(KEY_EMAIL, item.getEmail());
        db.insert(TABLE_NAME, null, values);
        Log.d("SQLiteDemo", item.getName() + " added");
        db.close();
    }

    //update tutor name in database
    public void updateTutor(Tutor item, Tutor newItem){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_NAME, newItem.getName());
        db.update(TABLE_NAME, values, KEY_NAME + "=?", new String[] {item.getName()});
        Log.d("SQLiteDemo", item.getName() + " updated");
        db.close();
    }

    //delete tutor from database
    public void deleteTutor(Tutor item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + "=?", new String[] {item.getName()});
        Log.d("SQLiteDemo", item.getName() + " deleted");
        db.close();
    }

    public Tutor getTutor(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = KEY_NAME + " = ?";
        String[] selectionArgs = { name };
        cursor = db.query(TABLE_NAME,
                new String[] {KEY_NAME, KEY_ZOOM, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS},
                selection, selectionArgs, null, null, null);

        //write contents of Cursor to list
        tutorList = new ArrayList<Tutor>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String zoom = cursor.getString(cursor.getColumnIndex(KEY_ZOOM));
            String add = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
            String phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
            String email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));

            tutorList.add(new Tutor(name, zoom, add, phone, email));
        };
        db.close();
        return tutorList.get(0);
    }

    //query database and return ArrayList of all animals
    public ArrayList<Tutor> getTutorList () {

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.query(TABLE_NAME,
                new String[] {KEY_NAME, KEY_ZOOM, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS},
                null, null, null, null, null);

        //write contents of Cursor to list
        tutorList = new ArrayList<Tutor>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String zoom = cursor.getString(cursor.getColumnIndex(KEY_ZOOM));
            String add = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
            String phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
            String email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));

            tutorList.add(new Tutor(name, zoom, add, phone, email));
        };
        db.close();
        return tutorList;

    }



}
