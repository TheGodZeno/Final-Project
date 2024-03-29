package com.example.spaceinvaders.framework;

import java.util.List;

/**
 * Interface to the input facilities. This allows polling the state of the keyboard, the touch
 * screen and the accelerometer.
 */
public interface Input {
    /**
     * This class represents a event from keyboard. Each event from keyboard could be of two type:
     * KEY_DOWN or KEY_UP; depending on the pressing of releasing of a key. Whenever a keyboard
     * event is generated the relative key code (keyCode) and its human readable representation
     * (keyChar) is associated to the event.
     */
    class KeyEvent {
        public static final int KEY_DOWN = 0;
        public static final int KEY_UP = 1;

        public int type;
        public int keyCode;
        public char keyChar;

        /**
         * Returns an human readable representation of the key event.
         * @return the human readable representation of the key event.
         */
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (type == KEY_DOWN)
                builder.append("key down, ");
            else
                builder.append("key up, ");
            builder.append(keyCode);
            builder.append(",");
            builder.append(keyChar);
            return builder.toString();
        }
    }

    /**
     * This class represents a event generated when user touch the video. A finger can touch
     * (TOUCH_DOWN), release (TOUCH_UP) or drag (TOUCH_DRAGGED) on the screen.
     * TWhen th event is generated it will be associated to the event also the (x, y) position
     * touched by the finger. When N fingers touch the video, N event will be generated and they
     * differ by the pointer value. For the first finger point=1, the second pointer=2 and so on.
     *
     * @author mzechner
     */
    class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;

        public int type;
        public int x, y;
        public int pointer;

        /**
         * Returns an human readable representation of the touch event.
         * @return the human readable representation of the touch event.
         */
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (type == TOUCH_DOWN)
                builder.append("touch down, ");
            else if (type == TOUCH_DRAGGED)
                builder.append("touch dragged, ");
            else
                builder.append("touch up, ");
            builder.append(pointer);
            builder.append(",");
            builder.append(x);
            builder.append(",");
            builder.append(y);
            return builder.toString();
        }
    }

    /**
     * Returns whether the key is pressed.
     *
     * @param keyCode The key code.
     * @return true if a key has been pressed.
     */
    boolean isKeyPressed(int keyCode);

    /**
     * Whether the screen is currently touched by the pointer with the given index. Pointers are
     * indexed from 0 to n. The pointer id identifies the order in which the fingers went down on
     * the screen, e.g. 0 is the first finger, 1 is the second and so on. When two fingers are
     * touched down and the first one is lifted the second one keeps its index. If another finger
     * is placed on the touch screen the first free index will be used.
     *
     * @param pointer the pointer
     * @return whether the screen is touched by the pointer
     */
    boolean isTouchDown(int pointer);

    /**
     * Returns the x coordinate in screen coordinates touched by the finger identified by the input
     * pointer. Pointers are indexed from 0 to n. The pointer id identifies the order in which the
     * fingers went down on the screen, e.g. 0 is the first finger, 1 is the second and so on.
     * When two fingers are touched down and the first one is lifted the second one keeps its index.
     * If another finger is placed on the touch screen the first free index will be used.
     *
     * @param pointer the pointer id.
     * @return the x coordinate
     */
    int getTouchX(int pointer);

    /**
     * Returns the y coordinate in screen coordinates touched by the finger identified by the input
     * pointer. Pointers are indexed from 0 to n. The pointer id identifies the order in which the
     * fingers went down on the screen, e.g. 0 is the first finger, 1 is the second and so on.
     * When two fingers are touched down and the first one is lifted the second one keeps its index.
     * If another finger is placed on the touch screen the first free index will be used.
     *
     * @param pointer the pointer id.
     * @return the y coordinate
     */
    int getTouchY(int pointer);

    /**
     * @return the rate of rotation around the x axis. (rad/s)
     */
    float getAccelX();

    /**
     * @return the rate of rotation around the y axis. (rad/s)
     */
    float getAccelY();

    /**
     * @return the rate of rotation around the z axis. (rad/s)
     */
    float getAccelZ();

    /**
     * @return the list of key events on the event queue.
     */
    List<KeyEvent> getKeyEvents();

    /**
     * @return the list of touch events on the event queue.
     */
    List<TouchEvent> getTouchEvents();
}
