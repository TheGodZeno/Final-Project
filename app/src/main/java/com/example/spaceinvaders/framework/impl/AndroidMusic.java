package com.example.spaceinvaders.framework.impl;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.example.spaceinvaders.framework.Music;

/*
 * This class represents a stream of music to play. A music file can be played, stopped, paused.
 * It can be played in loop and its volume can be regulated. A music file is managed by
 * Android class MediaPlayer.
 *
 * מחלקה זו מייצגת stream של music לניגון. תיקייה של מוזיקה יכולה לנגן, להעצר ולהשעות. היא יכולה להיות מנוגנת ב-loop והעוצמת הקול יכולה להסתדר.
 * תקייה של מוזיקה מנוהלת על ידי מחלקת Android MediaPlayer.
 */
public class AndroidMusic implements Music, OnCompletionListener {
    MediaPlayer mediaPlayer;
    boolean isPrepared = false;

    /*
     * Initializes a music file to play.
     *
     * מאתחל תיקייה מוזיקה לניגון.
     */
    public AndroidMusic(AssetFileDescriptor assetDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    /*
     * Disposes a music file.
     *
     * מסירה תיקיית מוזיקה.
     */
    public void dispose() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    /*
     * Checks if music file is played in loop.Disposes a music file.
     *
     * בודקת אם תיקיית מוזיקה מנוגנת ב-loop
     */
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    /*
     * Checks if music file is playing.
     *
     * בודקת אם תיקיית מוזיקה מנוגנת.
     */
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    /*
     * Checks if music file is stopped.
     *
     * בודקת אם תיקיית מוזיקה נעצרה.
     */
    public boolean isStopped() {
        return !isPrepared;
    }

    /*
     * Pauses the music file.
     *
     * משעה את תיקיית המוזיקה.
     */
    public void pause() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    /*
     * Plays the music file.
     *
     * מנגנת את תיקיית המוזיקה.
     */
    public void play() {
        if (mediaPlayer.isPlaying())
            return;

        try {
            synchronized (this) {
                if (!isPrepared)
                    mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Sets the music file to be played in loop.
     *
     * מגדירה את תיקיית המוזיקה להיות מנוגנת ב-loop
     */
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    /*
     * Sets the volume of the music file.
     *
     * מגדירה את עוצמת הקול של תיקיית המוזיקה.
     */
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    /*
     * Stops the music file.
     *
     * עוצרת את תיקיית המוזיקה.
     */
    public void stop() {
        mediaPlayer.stop();
        synchronized (this) {
            isPrepared = false;
        }
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            isPrepared = false;
        }
    }
}
