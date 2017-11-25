package com.text.volley.danmudemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


/**
 * Created by yuely198 on 2016/12/5.
 */

public class BarrageView extends RelativeLayout {
    private Context mContext;
    private Random random = new Random(System.currentTimeMillis());
    private int maxSpeed = 10000;//速度，ms
    private int minSpeed = 5000;//速度，ms
    private int maxSize = 30;//文字大小，dp
    private int minSize = 15;//文字大小，dp

    private int totalHeight = 0;//行高
    private int lineHeight = 0;//每一行弹幕的高度
    private int totalLine = 0;//弹幕的行数
    private RelativeLayout relativeLayout;
    private TextView textView;

    private static int SCREEN_WIDTH;
    private int moveSpeed;
    private int verticalPos;//垂直方向显示的位置
    public BarrageView(Context context) {
        this(context, null);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        totalHeight = getResources().getDisplayMetrics().heightPixels;//获取屏幕高度
        SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;//获取屏幕宽度
        lineHeight = 20;//每一行弹幕的高度
        totalLine = totalHeight / lineHeight;//弹幕的行数
    }


    public void setDatas(String msg) {
        generateItem();
        textView.setText(msg);
    }

    private void generateItem() {

        relativeLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.danmu_item, null);
        textView = relativeLayout.findViewById(R.id.tv);//文字
        int sz = (int) (minSize + (maxSize - minSize) * Math.random());//字体大小
        textView.setTextSize(sz);
        textView.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));//颜色
        moveSpeed = (int) (minSpeed + (maxSpeed - minSpeed) * Math.random());//移动速度
        if (totalLine == 0) {
            totalHeight = 150;
            lineHeight = 20;
            totalLine = totalHeight / lineHeight;
        }
        verticalPos = random.nextInt(totalLine) * lineHeight;
        showBarrageItem();
    }

    private void showBarrageItem() {

//        int leftMargin = this.getRight() - this.getLeft() - this.getPaddingLeft();
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.topMargin = verticalPos;
        this.addView(relativeLayout, params);
        Animation anim = generateTranslateAnim(relativeLayout, SCREEN_WIDTH);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        relativeLayout.startAnimation(anim);
    }

    private TranslateAnimation generateTranslateAnim(RelativeLayout item, int leftMargin) {

        TranslateAnimation anim = new TranslateAnimation(SCREEN_WIDTH, -SCREEN_WIDTH, 0, 0);
        anim.setDuration(moveSpeed);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setFillAfter(true);
        return anim;
    }

    /**
     * 计算TextView中字符串的长度
     *
     * @param text 要计算的字符串
     * @param Size 字体大小
     * @return TextView中字符串的长度
     */
    public float getTextWidth(BarrageItem item, String text, float Size) {
        Rect bounds = new Rect();
        TextPaint paint;
        paint = item.textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    /**
     * 获得每一行弹幕的最大高度
     *
     * @return
     */
    private int getLineHeight() {
        BarrageItem item = new BarrageItem();
        String tx = textView.getText().toString();
        item.textView = new TextView(mContext);
        item.textView.setText(tx);
        item.textView.setTextSize(maxSize);

        Rect bounds = new Rect();
        TextPaint paint;
        paint = item.textView.getPaint();
        paint.getTextBounds(tx, 0, tx.length(), bounds);
        return bounds.height();
    }

}