package com.example.finalwork;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by cocczh on 2019/10/26.
 */

public class my_date_utils {
    private static String TAG = "my_date_utils";
    public static long get_Millis(int YEAR, int MONTH, int DAY, int HOUR, int MINUTE, int SECOND) {
        String mSECOND = SECOND + "";
        String mMINUTE = MINUTE + "";

        if (MINUTE < 10) {
            mMINUTE = "0" + MINUTE;
        }

        if (SECOND < 10) {
             mSECOND = "0" + SECOND;
        }


        String t = String.format("%s-%s-%s %s:%s:%s", YEAR, MONTH, DAY, HOUR, mMINUTE, mSECOND);
        System.out.println(t);
        Log.i(TAG, "get_Millis: 输出结果"+com.blankj.utilcode.util.TimeUtils.string2Millis(t));
        return com.blankj.utilcode.util.TimeUtils.string2Millis(t);

    }



    public static void get_date(get_date_listener listener) {
        Calendar now = Calendar.getInstance();

        listener.get_date(

                now.get(Calendar.YEAR),
                (now.get(Calendar.MONTH) + 1),
                now.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.SECOND),
                now.get(Calendar.SECOND)

        );

    }

    public interface get_date_listener {
        void get_date(
                int YEAR,
                int MONTH,
                int DAY,
                int HOUR,
                int MINUTE,
                int SECOND
        );
    }

}
