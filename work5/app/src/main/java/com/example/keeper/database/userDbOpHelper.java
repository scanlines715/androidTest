package com.example.keeper.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.keeper.bean.User;

import java.util.ArrayList;

public class userDbOpHelper extends SQLiteOpenHelper {
    public userDbOpHelper(Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void add(String name,String password){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public void delete(String name,String password){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void updata(String password){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }

    @SuppressLint("Range")
    public ArrayList<User> getAllData(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("user",null,null,null,null,null,"name DESC");
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(name,password));
        }
        return list;
    }
}
