package com.example.spaceinvaders.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.example.spaceinvaders.framework.Audio;
import com.example.spaceinvaders.framework.FileIO;
import com.example.spaceinvaders.framework.Game;
import com.example.spaceinvaders.framework.Gdx;
import com.example.spaceinvaders.framework.Graphics;
import com.example.spaceinvaders.framework.Input;
import com.example.spaceinvaders.framework.Screen;

/*
 * On Android a Game interface is implemented by an Activity. It will manage the game lifecycle and
 * also it will manage the subsystem of the game library (Graphics, FileIO, Audio and Input).
 * It will create the frame buffer.
 *
 * מחלקה זו מייושמת על ידיד Activity. זה ינהל את ה-lifecycle של המשחק וינהל את ה-subsystem של ה-game library (Graphics, FileIO, Audio, Input).
 * הוא יצור את ה-frame buffer.
 */
public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;

    /*
     * Initialize the Game subsystems and frame buffer. It will create also the first screen of
     * the game: the start screen.
     *
     * מאתחל את ה-subsystem וה-frame buffer של המשחק.
     * הוא גם יצור את המסך הראשון של המשחק: StartScreen.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);

        Gdx.game = this;
        Gdx.graphics = graphics;
        Gdx.fileIO = fileIO;
        Gdx.audio = audio;
        Gdx.input = input;

        screen = getStartScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    /*
     * Called when game is resumed. The screen is locked, the current screen will be resumed and
     * also the render surface.
     *
     * מזומן כאשר המחשק ממשיך. המסך נעול, המסך הנוחכי ימשיך וגם ה-render surface.
     */
    public void onResume() {
        super.onResume();
        //wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    /*
     * Called when game is paused. The screen is unlocked, the current screen will be paused and
     * also the render surface.
     *
     *  מזומן כאשר המשחק מושעה. המסך פתוח, המסך הנוכחי יהיה מושעה וגם ה-render surface.
     */
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    /*
     * Returns the Input subsystem.
     *
      * מחזיר את ה-Input subsystem.
     */
    public Input getInput() {
        return input;
    }

    /*
     * Returns the FileIO subsystem.
     *
     * מחזיר את ה-FileIO subsystem.
     */
    public FileIO getFileIO() {
        return fileIO;
    }

    /*
     * Returns the Graphics subsystem.
     *
     * מחזירה את ה-Graphics subsystem.
     */
    public Graphics getGraphics() {
        return graphics;
    }

    /*
     * Returns the Audio subsystem.
     *
     * מחזירה את ה-Audio subsystem.
     */
    public Audio getAudio() {
        return audio;
    }

    /*
     * Sets the current screen.
     *
     * מגדיר את המסך הנוכחי
     */
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        // current screen is paused and disposed
        // המסך הנוחכי מושעה ומוסר
        this.screen.pause();
        this.screen.dispose();
        // input screen is resumed and update and it will be the new current screen.
        // ה-input screen ממשיך ומעודכן והוא יהיה המסך הנוחכי החדש.
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    /*
     * Returns the current screen.
     *
     * מחזיר את המסך הנוכחי.
     */
    public Screen getCurrentScreen() {
        return screen;
    }
}