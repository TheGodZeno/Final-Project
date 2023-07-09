package com.example.spaceinvaders.framework.impl;

import android.view.View.OnTouchListener;

import com.example.spaceinvaders.framework.Input;
import com.example.spaceinvaders.framework.Input.TouchEvent;

import java.util.List;

/*
 * This is the handler used to manage touch events. This class is specialized by SingleTouchHandler
 * or MultiTouchHandler depending on Android version.
 *
 * זהו ה-handler המשומש לניהול touch events.
 * מחלקה זו מתמצה על ידי SingleTouchHandler או MultiTouchHandler תלוי מה גרסת ה-Android.
 */
public interface TouchHandler extends OnTouchListener {
    boolean isTouchDown(int pointer);
    int getTouchX(int pointer);
    int getTouchY(int pointer);
    List<Input.TouchEvent> getTouchEvents();
}
