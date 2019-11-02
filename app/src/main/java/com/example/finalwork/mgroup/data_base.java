package com.example.finalwork.mgroup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by cocczh on 2019/10/26.
 */

public class data_base extends SQLiteOpenHelper {


    public data_base(Context context) {
        super(context, "app.db", null, 1);
    }


    public static final String create_table_memorandum_group_sql =
            "create table memorandum_group(" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT" +
                    ");";




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                create_table_memorandum_group_sql
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static SQLiteDatabase mSQLiteDatabase;


    public static SQLiteDatabase open(Context context) {
        if (mSQLiteDatabase == null) {
            data_base dataBaseHelper = new data_base(context);
            mSQLiteDatabase = dataBaseHelper.getWritableDatabase();
        }
        return mSQLiteDatabase;
    }


    public static void add_memorandum_group(memorandum_group memorandum_group) {
        ContentValues content = new ContentValues();
        content.put("name", memorandum_group.name);
        Log.e("0", "data_base add_memorandum_group " + String.valueOf(
                memorandum_group.name
        ));


        mSQLiteDatabase.insert("memorandum_group", null, content);

    }


    public static int del_memorandum_group(memorandum_group memorandum_group) {

        return mSQLiteDatabase.delete("memorandum_group", String.format("id=%s", memorandum_group.id), null);

    }

    public static ArrayList<memorandum_group> search_memorandum_group() {

        Cursor cursor = mSQLiteDatabase.query(
                "memorandum_group",
                null, null,
                null, null, null, null);

        ArrayList<memorandum_group> memorandum_groupList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            memorandum_group memorandum_group = new memorandum_group();
            memorandum_group.id =  cursor.getInt(cursor.getColumnIndex("id"));
            memorandum_group.name = cursor.getString(cursor.getColumnIndex("name"));
            memorandum_groupList.add(memorandum_group);
            cursor.moveToNext();
        }

        return memorandum_groupList;

    }

    public static void update_memorandum_group(memorandum_group memorandum_group) {
        ContentValues content = new ContentValues();
        content.put("name", memorandum_group.name);
        mSQLiteDatabase.update("memorandum_group", content, "id=?", new String[]{memorandum_group.id + ""});
    }


}
