package com.example.spaceinvaders.framework.impl;

import android.view.View;
import android.view.View.OnKeyListener;

import com.example.spaceinvaders.framework.Input.KeyEvent;
import com.example.spaceinvaders.framework.Pool;
import com.example.spaceinvaders.framework.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

/*
 * This is the handler used to manage keyword events. The class does not allocate a KeyEvent object
 * for each key event. It uses the Object Pool pattern to allocate 100 KeyEvent and reuse them
 * avoiding waste of memory.
 *
 * זהו ה-handler המשומש לניהול keyword events. מחלקה זו לא מקצה KeyEvent object לכל key event.
 * היא משתמשת ב-Object Pool pattern בכדיי להקצות 100 KeyEvents ולהשתמש בהם שנית בשביל להימנע מבזבוז זכרון.
 */
public class KeyboardHandler implements OnKeyListener {
    boolean[] pressedKeys = new boolean[128];
    Pool<KeyEvent> keyEventPool;
    List<KeyEvent> keyEventsBuffer = new ArrayList<>();
    List<KeyEvent> keyEvents = new ArrayList<>();

    public KeyboardHandler(View view) {
        PoolObjectFactory<KeyEvent> factory = new PoolObjectFactory<KeyEvent>() {
            @Override
            public KeyEvent createObject() {
                return new KeyEvent();
            }
        };
        keyEventPool = new Pool<>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override
    public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
        if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
            return false;

        synchronized (this) {
            KeyEvent keyEvent = keyEventPool.newObject();
            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = (char) event.getUnicodeChar();
            if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                keyEvent.type = KeyEvent.KEY_DOWN;
                if(keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = true;
            }
            if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
                keyEvent.type = KeyEvent.KEY_UP;
                if(keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = false;
            }
            keyEventsBuffer.add(keyEvent);
        }
        return false;
    }

    public boolean isKeyPressed(int keyCode) {
        return !(keyCode < 0 || keyCode > 127) && pressedKeys[keyCode];
    }

    public List<KeyEvent> getKeyEvents() {
        synchronized (this) {
            int len = keyEvents.size();
            for (int i = 0; i < len; i++)
                keyEventPool.free(keyEvents.get(i));
            keyEvents.clear();
            keyEvents.addAll(keyEventsBuffer);
            keyEventsBuffer.clear();
            return keyEvents;
        }
    }
}
