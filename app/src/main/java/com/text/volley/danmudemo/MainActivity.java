package com.text.volley.danmudemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random = new Random(System.currentTimeMillis());
    private static final long BARRAGE_GAP_MIN_DURATION = 1000;//两个弹幕的最小间隔时间
    private static final long BARRAGE_GAP_MAX_DURATION = 2000;//两个弹幕的最大间隔时间



    private Handler mViewHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                default:
                    break;
            }
        }

    };


    /*模拟弹幕   1s*/
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            barrageView.setDatas("天王盖地虎");
            //每个弹幕产生的间隔时间随机
            int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
            mViewHandler.postDelayed(this, duration);
        }
    };
    private BarrageView barrageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barrageView = (BarrageView) findViewById(R.id.barrageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewHandler.postDelayed(mRunnable, 1000 * 1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewHandler.removeCallbacksAndMessages(null);
    }
}
