package com.ht.fightball.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.ht.fightball.pojo.BallPJ;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by annuo on 2015/5/24.
 */
public class Ball extends View {

    private Paint paint;
    //放所有的气球的集合
    private List<BallPJ> points;


    public Ball(Context context) {
        super(context);
        init(context, null);
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 自定义view初始化方法
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        points = new ArrayList<BallPJ>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //进行气球的初始化
        if (points.size() == 0) {
            Random random = new Random();
            //为了保证初始气球的高低错落
            float pady = 20;
            for (int i = 0; i < 20; i++) {
                BallPJ ballPJ = new BallPJ();
                int r1 = random.nextInt(255);
                int g1 = random.nextInt(255);
                int b1 = random.nextInt(255);
                ballPJ.setColor(new int[]{0x99, r1, g1, b1});
                float cx = random.nextInt(getWidth());
                ballPJ.setX(cx);
                float cy = getHeight();
                ballPJ.setY(cy - pady);
                pady = pady + 20;
                float rad = random.nextInt(100);
                ballPJ.setR(rad);
                points.add(ballPJ);
            }

        }
        //遍历气球的集合，画气球
        for (BallPJ ballPJ : points) {
            paint.setARGB(ballPJ.getColor()[0]
                    , ballPJ.getColor()[1]
                    , ballPJ.getColor()[2]
                    , ballPJ.getColor()[3]);
            canvas.drawCircle(ballPJ.getX(), ballPJ.getY(), ballPJ.getR(), paint);
        }
    }

    /**
     * 自定义View的onTouchEvent方法，监听手势事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean ret = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                ret = true;
                float eventY = event.getY();
                float eventX = event.getX();
                int index = isInCircle(eventX, eventY);
                if (index != -1) {
                    //表明点中了某个圆，调整这个圆的y，让他到屏幕的最下方去，这样实现气球的复用，使得屏幕中
                    //永远20个气球，或者也可以从气球集合中把该气球移除
                    points.get(index).setY(getHeight() + 100);
                    invalidate();
                }
                break;
        }
        return ret;
    }

    /**
     * 判断点击的坐标是否在圆上,并返回具体在第几个圆上,如果没找到返回-1
     *
     * @param x
     * @param y
     * @return
     */
    private int isInCircle(float x, float y) {
        for (int i = 0; i < points.size(); i++) {
            if (x >= points.get(i).getX() - 50 && x <= points.get(i).getX() + 50 && y >= points.get(i).getY() - 50 && y <= points.get(i).getY() + 50) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 进行界面刷新的线程类，保证球徐徐上升
     */
    public class UpdateLoc implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    //让线程休息200毫秒，这样使得球升的慢一点
                    Thread.sleep(200);
                    for (BallPJ ballPJ : points) {
                        float lastY = ballPJ.getY();
                        if (lastY > 0) {
                            ballPJ.setY(lastY - 5);
                            //让系统调用onDraw方法刷新自定义View
                            postInvalidate();
                        } else {
                            //一旦气球到达顶部，让他移动到屏幕的最下面去
                            ballPJ.setY(getHeight() + 50);
                            postInvalidate();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
