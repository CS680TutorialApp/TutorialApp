package com.example.tutorialapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SubjectSQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tutorial.db";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "subjects";
    public static final String KEY_NAME = "name";
    public static final String KEY_TUTOR = "tutor";
    public static final String KEY_REF = "referenceLink";
    public static final String KEY_ID = "id integer primary key autoincrement";
    public static final String CREATE_TABLE = "CREATE TABLE subjects ("
            + KEY_ID + "," + KEY_NAME + " text," + KEY_TUTOR + " text,"
            + KEY_REF + " text);";

    private ContentValues values;
    private ArrayList<Subject> subjectList;
    private Cursor cursor;

    public SubjectSQLHelper(Context context) {
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

    //add subject to database
    public void addSubject(Subject item) {
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_REF, item.getReferenceLink());
        values.put(KEY_TUTOR, item.getTutor());
        db.insert(TABLE_NAME, null, values);
        Log.d("SQLiteDemo", item.getName() + " added");
        db.close();
    }

    //update subject name in database
    public void updateSubject(Subject item, Subject newItem){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_NAME, newItem.getName());
        db.update(TABLE_NAME, values, KEY_NAME + "=?", new String[] {item.getName()});
        Log.d("SQLiteDemo", item.getName() + " updated");
        db.close();
    }

    //delete subject from database
    public void deleteSubject(Subject item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + "=?", new String[] {item.getName()});
        Log.d("SQLiteDemo", item.getName() + " deleted");
        db.close();
    }

    //query database and return ArrayList of all subjects
    public ArrayList<Subject> getSubjectList () {

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.query(TABLE_NAME,
                new String[] {KEY_NAME, KEY_TUTOR, KEY_REF},
                null, null, null, null, null);

        //write contents of Cursor to list
        subjectList = new ArrayList<Subject>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            @SuppressLint("Range") String tutor = cursor.getString(cursor.getColumnIndex(KEY_TUTOR));
            @SuppressLint("Range") String ref = cursor.getString(cursor.getColumnIndex(KEY_REF));
            subjectList.add(new Subject(name, tutor, ref));
        };
        db.close();
        return subjectList;

    }



}
