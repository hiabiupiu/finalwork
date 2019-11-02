package com.example.finalwork.alarm;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.finalwork.R;

public class AlarmActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mediaPlayer=MediaPlayer.create(this,R.raw.alarm);
        mediaPlayer.start();
        new AlertDialog.Builder(AlarmActivity.this).setTitle("闹钟").setMessage("张三起床了")
                .setPositiveButton("关闭闹钟", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mediaPlayer.stop();
                        AlarmActivity.this.finish();
                    }
                }).show();
    }
}//闹钟组件的创建
