package com.example.androidcodefinder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class UserDataHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "users";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "account";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String createTableSql = "CREATE TABLE account (" +
            "id integer primary key autoincrement," +
            "email text," +
            "password text)";


    public UserDataHelper(Context context) {
        super((Context) context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tao bang
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoa bang user neu da ton tai va tao lai
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void query(String sql){
         SQLiteDatabase database = getWritableDatabase();
         database.execSQL(sql);
    }
    public Cursor query2(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql , null);
    }
    public void addUser(String email , String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO account VALUES (null, '" + email + "','" + password + "')";
        db.execSQL(sql);
    }
    public boolean getUserByEmail(String email) {
        Cursor cursor = query2("SELECT * FROM account WHERE email like '" + email + "'");
        if (cursor.moveToFirst())
            return true;
        return false;
    }
    public boolean getUserByPassword(String password) {
        Cursor cursor = query2("SELECT * FROM account WHERE password like '" + password + "'");
        if (cursor.moveToFirst())
            return true;
        return false;
    }
}
