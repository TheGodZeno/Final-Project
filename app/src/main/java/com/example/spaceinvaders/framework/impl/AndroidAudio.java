package com.example.spaceinvaders.framework.impl;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.spaceinvaders.framework.Audio;
import com.example.spaceinvaders.framework.Music;
import com.example.spaceinvaders.framework.Sound;

import java.io.IOException;

/*
 * Implement the Audio interface for Android. The class create a SoundPool to manage brief Sound
 * file like explosion, beep, etc. Usually these file are completely uploaded in memory so make sure
 * they are small. It will use MediaPlayer class to play music stream.
 *
 * מיישם את ה-Audio interface בשביל האנדרואיד. המחלקה יוצרת SoundPool בכדיי לנהל Sound file זמני כמו explosion, beep ועוד.
 * בדרך כלל סוגי תיקיות כאלה מועלות לגמריי בזכרון אז כדיי לוודא שהם קטנים. הוא יישתמש במחלקת MediaPlayer בכדיי לנגן music stream.
 *
 */
public class AndroidAudio implements Audio {
    AssetManager assets;
    SoundPool soundPool;

    public AndroidAudio(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    /*
     * Implementation of the factory method used to create a Music file. A Music file is implemented
     * by the class AndroidMusic that delegate the behaviour to Android MediaPlayer class.
     *
     * יישום של factory method המשומשת ליצירה של Music file.
     *  ה-Music file  מוטמעת על ידי מחלקת AndroidMusic אשר מציגה את ההתנהגות שלה למחלקת Android MediaPlayer
     */
    public Music newMusic(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            return new AndroidMusic(assetDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load music '" + filename + "'");
        }
    }

    /*
     * Implementation of the factory method used to create a Sound file. A Sound file is implemented
     * by the class AndroidSound that delegate the behaviour to Android SoundPool class.
     *
     * יישום של ה-factory method אשר משומשת ליצירת Sound file.
     * ה-Sound file מיושם על ידי מחלקת AndroidSound אשר מציגה את התנהגותה למחלקת Android SoundPool
     *
     */
    public Sound newSound(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetDescriptor, 0);
            return new AndroidSound(soundPool, soundId);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound '" + filename + "'");
        }
    }
}
