package com.example.spaceinvaders.framework.impl;

import android.media.SoundPool;

import com.example.spaceinvaders.framework.Sound;

/*
 * This class represents a brief sound like beep, explosion, etc. A sound file can be played at
 * a specific volume. The Android SoundPool class will be used to manage these sounds.
 * מחלקה זו מייצגת סאונדים זמניים כמו beep, explosion ועוד.
 * תיקיית סאונדיכולה להתנגן בעוצמת קול ספציפית ומחלקת Android SoundPool תשומש בכדיי לנהל את אותם סאונדים.
 */
public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    /*
     * Plays the sound file at the specified volume.
     * מנגן את תיקיית המוזיקה בעוצמת קול ספציפית.
     */
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    /*
     * Disposes a sound file.
     * מסירה תיקיית מוזיקה
     */
    public void dispose() {
        soundPool.unload(soundId);
    }
}
