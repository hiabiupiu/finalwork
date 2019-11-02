package com.example.finalwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//�������ݿ���
public class MySQLiteUtil extends SQLiteOpenHelper {
	public MySQLiteUtil(Context context, String name, CursorFactory factory,
                        int version) {
		super(context, name, factory, version);
	}

	String CREATE_TABLE_SQL = "create table user_tb(_id integer primary key autoincrement,subject,body,date,_group)";

	// �̳�SQLiteOpenHelper�࣬Ҫʵ�ֵķ�������һ�ΰ�װ��ִ��
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL); // ��������ݿ⣬����һ�ű�
		// ����������һֱ������
	}

	// �̳�SQLiteOpenHelper�࣬Ҫʵ�ֵķ�������һ�ΰ�װ��ִ��
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		String oldVersion = null;
		System.out.print("-----" + oldVersion + "-----");
	}

}
