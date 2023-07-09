package com.example.spaceinvaders.framework.impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.example.spaceinvaders.framework.Input;

import java.util.List;

/*
 * This class implements the Input subsystem for Android. On Android inputs come from:
 *     accelerometer
 *     keyboard
 *     touch screen
 *
 * Each subsystem is managed by a specific handler (AccelerometerHandler, KeyboardHandler and
 * SingleTouchHandler/MultiTouchHandler). For touch screen events modern hardware are managed by
 * MultiTouchHandler class. Very old hardware are managed by SingleTouchHandler.
 *
 * מחלקה זו מיישמת את ה-Input subsystem בשביל Android. ה-On Android inputs מגיעים מ:
 * accelerometer
 * keyboard
 * touch screen
 *
 * כל subsystem מנוהלת על ידי handler ספציפי (AccelerometerHandler, KeyboardHandler, SingleTouchHandler/MultiTouchHandler).
 * בשביל איורעי touch screen. ה-moder hardware מנוהל על ידי מחלקת MultiTouchHandler. ו-hardware מאוד ישן מנוהל על ידי SingleTouchHandler.
 *
 */
public class AndroidInput implements Input {
    AccelerometerHandler accelHandler;
    KeyboardHandler keyHandler;
    TouchHandler touchHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        accelHandler = new AccelerometerHandler(context);
        keyHandler = new KeyboardHandler(view);               
        if(Integer.parseInt(VERSION.SDK) < 5) 
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        else
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);        
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyHandler.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return accelHandler.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelHandler.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelHandler.getAccelZ();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
    
    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyHandler.getKeyEvents();
    }
}
