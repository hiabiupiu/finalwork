package com.example.finalwork;

import
		android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

//数据库管理类
public class MemoSqlDataManage {
	private static MemoSqlDataManage m_MemoSqlDataManage = null;
	private MySQLiteUtil mySQLiteUtil; // 辅助数据库
	private SQLiteDatabase db; // 数据库db
	private Context mContext;

	// 单例模式
	public static MemoSqlDataManage GetInstance(Context base) {
		if (m_MemoSqlDataManage == null) {
			m_MemoSqlDataManage = new MemoSqlDataManage(base);
		}
		return m_MemoSqlDataManage;
	}

	// 管理类初始化
	public MemoSqlDataManage(Context base) {
		// 打开或创建test.db数据库
		mySQLiteUtil = new MySQLiteUtil(base, "memento.db", null, 1);
		mContext = base;
		db = mySQLiteUtil.getReadableDatabase();
	}

	/**
	 * 往数据库添加数据
	 *
	 * @param strsubject
	 * @param strbody
	 * @param strdate
	 * @return
	 */
	public boolean addSqlData(String strsubject, String strbody, String strdate,String group) {
		if (!strsubject.equals("")) {
			db.execSQL("insert into user_tb values(null,?,?,?,?)", new String[] {
					strsubject, strbody, strdate,group });
			Toast.makeText(mContext, "添加备忘录成功", Toast.LENGTH_LONG).show();
			return true;
		} else {
			Toast.makeText(mContext, "主题不能为空！", Toast.LENGTH_LONG).show();
		}
		return false;
	}

	/**
	 * 更新数据库
	 *
	 * @param strsubject
	 * @param strbody
	 * @param strdate
	 */
	public void update(int nid, String strbody) {
		String strSQL = "update user_tb set body='" + strbody + "' where _id="
				+ nid;
		db.execSQL(strSQL);
		Toast.makeText(mContext, "更新备忘录成功", Toast.LENGTH_LONG).show();
	}

	/**
	 * 删除数据
	 *
	 * @param nid
	 * @return
	 */
	public boolean deleteSqlData(int nid) {
		boolean bdelete = false;
		if (bdelete == false) {
			String strSQL = "delete from user_tb where _id=" + nid;
			db.execSQL(strSQL);
			bdelete = true;
			Toast.makeText(mContext, "删除备忘录成功", Toast.LENGTH_LONG).show();
		}
		return true;
	}

	// 查询数据库全部数据
	public Cursor querySqlData() {
		Cursor cursor = db.rawQuery("select * from user_tb", null); // 查询全部数据
		return cursor;
	}

	// 查询数据库分组全部数据
	public Cursor querySqlDataByGroup(String group) {
		Cursor cursor = db.rawQuery(	String.format("select * from user_tb where _group='%s'",group ), null); // 查询全部数据
		return cursor;
	}

	// 根据条件查询数据库数据
	public Cursor querySqlData(int nid) {
		Cursor cursor = db.rawQuery("select * from user_tb where _id=" + nid,
				null); // 查询全部数据
		return cursor;
	}

	// 关闭数据库
	public boolean closeSql() {
		db.close();
		return false;
	}
}
