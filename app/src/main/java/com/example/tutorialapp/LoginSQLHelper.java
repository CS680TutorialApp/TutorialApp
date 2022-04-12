package com.example.tutorialapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LoginSQLHelper extends SQLiteOpenHelper {
    public static final String DbName = "Login.db";

    public LoginSQLHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Login.db", null, 1);
    }

    public LoginSQLHelper(Signup context) {
        super(context,"Signup.class",null,1);
    }

    public LoginSQLHelper(LoginActivity loginActivity) {
        super(loginActivity,"LoginActivity.class",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase LoginDb) {
        LoginDb.execSQL("create Table users(username TEXT primary key, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase LoginDb, int i, int i1) {
        LoginDb.execSQL("drop Table if exists users");
    }

    public boolean insertData(String username, String password){
        SQLiteDatabase LoginDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = LoginDb.insert("users", null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean checkUserName(String username){

        SQLiteDatabase LoginDb = this.getWritableDatabase();
        Cursor cursor = LoginDb.rawQuery("select * from users where username = ?", new String[] {username});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }


    }
    public boolean checkUsernameAndPassword(String username, String password){
        SQLiteDatabase LoginDb = this.getWritableDatabase();
        Cursor cursor = LoginDb.rawQuery("select * from users where username = ? and password = ?", new String[] {username, password});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }


    }
}
