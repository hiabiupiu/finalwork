package com.example.finalwork;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.finalwork.alarm.AlarmActivity;
import com.example.finalwork.mgroup.groupActivity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MemoSqlDataManage mSqlDataManage;
    private Button add, qurey, SelectDate;
    private EditText date, subject, body;
    private int hour;
    private int minute;
    private EditText group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 实例化MemoSqlDataManage数据库管理类
        mSqlDataManage = MemoSqlDataManage.GetInstance(getApplicationContext());

        subject = (EditText) findViewById(R.id.subject);
        body = (EditText) findViewById(R.id.body);
        date = (EditText) findViewById(R.id.date);
        group = (EditText) findViewById(R.id.group);

        add = (Button) findViewById(R.id.add);
        qurey = (Button) findViewById(R.id.qurey);


        Button viewgroup = (Button) findViewById(R.id.viewgroup);
        viewgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, groupActivity.class);
                startActivity(intent);
            }
        });



        SelectDate = (Button) findViewById(R.id.select_date);

        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(MainActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {


                        date.setText(i + ":" + i1);

                        hour = i;
                        minute = i1;


                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();


//                // 生成日历选择
//                Calendar c = Calendar.getInstance();
//                new DatePickerDialog(MainActivity.this, 0,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int month, int day) {
//                                date.setText(year + "-" + (month + 1) + "-"
//                                        + day);
//                            }
//                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
//                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        MyListener myListener = new MyListener();
        add.setOnClickListener(myListener);
        qurey.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add:
                    boolean bsuc = mSqlDataManage.addSqlData(subject.getText().toString(),
                            body.getText().toString(), date.getText().toString(),group.getText().toString());


//                    Calendar c=Calendar.getInstance();
//                    c.setTimeInMillis(System.currentTimeMillis());
//                    c.set(Calendar.HOUR,hour);
//                    c.set(Calendar.MINUTE,minute);
//
//                    final AlarmManager am=(AlarmManager) getSystemService(ALARM_SERVICE);
//                    Intent intent=new Intent(MainActivity.this,AlarmActivity.class);
//                    final PendingIntent pi=PendingIntent.getActivity(MainActivity.this,0,intent,0);
//                    am.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);
//
//                    Log.e("0", "MyListener onClick " + String.valueOf(
//                            hour + ":" + minute
//                    ));


                    if (bsuc) {
                        subject.setText("");
                        body.setText("");
                        date.setText("");


                        my_date_utils.get_date(new my_date_utils.get_date_listener() {
                            @Override
                            public void get_date(int YEAR, int MONTH, int DAY, int HOUR, int MINUTE, int SECOND) {


                                Log.e("0", "MyListener get_date " + String.valueOf(
                                        hour
                                ));

                                Log.e("0", "MyListener get_date " + String.valueOf(
                                        minute
                                ));


                                long reminder_time = my_date_utils.get_Millis(YEAR, MONTH, DAY, hour, minute, 0);

                                long now = System.currentTimeMillis();

                                long interval = reminder_time - now;


                                Log.e("0", "MyListener get_date interval " + String.valueOf(
                                        interval/1000+"秒"
                                ));


                                if (reminder_time <= now) {
                                    return;
                                }





                                Timer mTimer2 ;
                                TimerTask mTask2;
                                    mTimer2 = new Timer();
                                    mTask2 = new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent=new Intent(MainActivity.this,AlarmActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    };
                                mTimer2.schedule(mTask2, interval);
                            }
                        });


                    }


                    break;
                case R.id.qurey:
                    Intent intent2 = new Intent();
                    intent2.setClass(MainActivity.this, QueryActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "设置");
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "缓存");
        menu.add(Menu.NONE, Menu.FIRST + 3, 3, "退出");
        return true;
    }


}
