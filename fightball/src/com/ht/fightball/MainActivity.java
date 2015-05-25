package com.ht.fightball;

import android.app.Activity;
import android.os.Bundle;
import com.ht.fightball.widget.Ball;

public class MainActivity extends Activity {

    private Ball ball;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ball = (Ball) findViewById(R.id.ball);
        //启动内部线程
        Ball.UpdateLoc updateLoc = ball.new UpdateLoc();
        Thread thread = new Thread(updateLoc);
        thread.start();
    }
}
