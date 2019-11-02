package com.example.finalwork;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {
    private MemoSqlDataManage mSqlDataManage;
    private TextView mtheme, mdate;
    private EditText mbody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mSqlDataManage = MemoSqlDataManage.GetInstance(getBaseContext());

        mtheme = (TextView) findViewById(R.id.updatetheme);
        mdate = (TextView) findViewById(R.id.updatedate);
        mbody = (EditText) findViewById(R.id.updateboby);

        Bundle bundle = getIntent().getExtras();
        mtheme.setText("主题：" + bundle.getString("theme"));
        mbody.setText(bundle.getString("body"));
        mdate.setText("日期：" + bundle.getString("date"));

    }

    // 按键删除监听
    public void delete(View v) {
        boolean ndelete = false;
        Bundle bundle = getIntent().getExtras();
        ndelete = mSqlDataManage.deleteSqlData(bundle.getInt("id")); // 调用删除数据语句
        if (ndelete) {
            mtheme.setText("主题：");
            mdate.setText("日期：");
            mbody.setText("");
        }
    }

    // 按键保存修改监听
    public void save(View v) {
        Bundle bundle = getIntent().getExtras();
        mSqlDataManage.update(bundle.getInt("id"), mbody.getText().toString());
    }

    // 返回按键调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK, (new Intent()).setAction(""));
            finish();
        }
        return true;
    }
}