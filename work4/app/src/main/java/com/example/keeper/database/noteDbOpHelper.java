package com.example.keeper.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.keeper.bean.Note;

import java.util.ArrayList;
import java.util.List;

public class noteDbOpHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "noteSQLite.db";
    private static final String TABLE_NAME_NOTE = "note";
    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement, title text, content text, create_time text)";

    public noteDbOpHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    // 向数据库中插入一条Note数据，返回插入的行数
    public long insertData(Note note){
        // 获取可写的数据库对象
        SQLiteDatabase db = getWritableDatabase();
        // 创建一个ContentValues对象，用于存放键值对
        ContentValues values = new ContentValues();
        // 将Note对象的属性作为键值对放入ContentValues对象中
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());
        // 调用数据库对象的插入方法，返回插入的行数
        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    // 从数据库中查询所有的Note数据，返回一个Note列表
    @SuppressLint("Range")
    public List<Note> queryAllFromDb() {
        // 获取可写的数据库对象
        SQLiteDatabase db = getWritableDatabase();
        // 创建一个Note列表，用于存放查询结果
        List<Note> noteList = new ArrayList<>();

        // 调用数据库对象的查询方法，返回一个Cursor对象，用于遍历查询结果
        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null);
        // 判断Cursor对象是否为空，如果不为空，遍历Cursor对象，获取每一条Note数据，并添加到Note列表中
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 从Cursor对象中获取Note数据的各个属性
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String createTime = cursor.getString(cursor.getColumnIndex("create_time"));

                // 创建一个Note对象，设置其属性
                Note note = new Note();
                note.setId(id);
                note.setTitle(title);
                note.setContent(content);
                note.setCreatedTime(createTime);

                // 将Note对象添加到Note列表中
                noteList.add(note);
            }
            // 关闭Cursor对象，释放资源
            cursor.close();
        }
        // 返回Note列表
        return noteList;
    }

    // 根据Note对象的id更新数据库中对应的数据，返回更新的行数
    public int updateData(Note note) {
        // 获取可写的数据库对象
        SQLiteDatabase db = getWritableDatabase();

        // 创建一个ContentValues对象，用于存放键值对
        ContentValues values = new ContentValues();
        // 将Note对象的属性作为键值对放入ContentValues对象中
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());

        // 调用数据库对象的更新方法，根据Note对象的id作为条件，返回更新的行数
        return db.update(TABLE_NAME_NOTE, values, "id like ?", new String[]{note.getId()});
    }

    // 根据id从数据库中删除一条Note数据，返回删除的行数
    public int deleteFromDbById(String id) {
        // 获取可写的数据库对象
        SQLiteDatabase db = getWritableDatabase();
        // 调用数据库对象的删除方法，根据id作为条件，返回删除的行数
        return db.delete(TABLE_NAME_NOTE, "id like ?", new String[]{id});
    }

    // 根据标题从数据库中模糊查询Note数据，返回一个Note列表
    @SuppressLint("Range")
    public List<Note> queryFromDbByTitle(String title) {
        // 判断标题是否为空，如果为空，直接调用查询所有数据的方法，并返回结果
        if (TextUtils.isEmpty(title)) {
            return queryAllFromDb();
        }

        // 获取可写的数据库对象
        SQLiteDatabase db = getWritableDatabase();
        // 创建一个Note列表，用于存放查询结果
        List<Note> noteList = new ArrayList<>();

        // 调用数据库对象的查询方法，根据标题作为模糊条件，返回一个Cursor对象，用于遍历查询结果
        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "title like ?", new String[]{"%"+title+"%"}, null, null, null);

        // 判断Cursor对象是否为空，如果不为空，遍历Cursor对象，获取每一条Note数据，并添加到Note列表中
        if (cursor != null) {

            while (cursor.moveToNext()) {
                // 从Cursor对象中获取Note数据的各个属性
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title2 = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String createTime = cursor.getString(cursor.getColumnIndex("create_time"));

                // 创建一个Note对象，设置其属性
                Note note = new Note();
                note.setId(id);
                note.setTitle(title2);
                note.setContent(content);
                note.setCreatedTime(createTime);
                // 将Note对象添加到Note列表中
                noteList.add(note);
            }
            // 关闭Cursor对象，释放资源
            cursor.close();
        }
        // 返回Note列表
        return noteList;
    }

}
