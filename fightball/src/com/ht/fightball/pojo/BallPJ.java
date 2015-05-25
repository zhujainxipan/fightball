package com.ht.fightball.pojo;

import java.util.List;

/**
 * Created by annuo on 2015/5/24.
 * 使用面对对象的思想，把气球对象封装起来
 */
public class BallPJ {
    private float x;
    private float y;
    private float r;
    private int[] color;

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }
}
