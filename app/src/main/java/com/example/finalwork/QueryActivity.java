package com.example.finalwork;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class QueryActivity extends Activity {
    private MemoSqlDataManage mSqlDataManage;
    private ListView mlistView;
    private SimpleCursorAdapter mSimpleCursorAdapter;
    private Cursor mcursor;
    String group;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);


       group = getIntent().getStringExtra("group");

        Log.e("0", "QueryActivity onCreate " + String.valueOf(
                group
        ));




        // 实例化MemoSqlDataManage数据库管理类
        mSqlDataManage = MemoSqlDataManage.GetInstance(getApplicationContext());

        mlistView = (ListView) findViewById(R.id.listView1);

        setAdapter();
        mlistView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                mcursor.moveToPosition(arg2);
                int nid = mcursor.getInt(mcursor.getColumnIndex("_id"));
                String strtheme = mcursor.getString(mcursor
                        .getColumnIndex("subject"));
                String strbody = mcursor.getString(mcursor
                        .getColumnIndex("body"));
                String strdate = mcursor.getString(mcursor
                        .getColumnIndex("date"));
                Intent intent = new Intent();
                intent.setClass(QueryActivity.this, UpdateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", nid);
                bundle.putString("theme", strtheme);
                bundle.putString("body", strbody);
                bundle.putString("date", strdate);
                intent.putExtras(bundle);
                startActivityForResult(intent, 4);
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setAdapter() {
        // SimpleCursorAdapter适配器是用于数据库

        if (group == null) {

            mcursor = mSqlDataManage.querySqlData();

        } else {
            mcursor = mSqlDataManage.querySqlDataByGroup(group);
        }


        if (mcursor.getCount() > 0) {
            mcursor.moveToFirst();
            mSimpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item,
                    mcursor, new String[] { "_id", "subject", "body", "date" },
                    new int[] { R.id.memento_id, R.id.memento_subject,
                            R.id.memento_body, R.id.memento_date });
            mlistView.setAdapter(mSimpleCursorAdapter);
        }
    }

    // 返回activity页面刷新
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            setAdapter();
        }
    }
}
