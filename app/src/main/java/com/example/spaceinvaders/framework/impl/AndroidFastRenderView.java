package com.example.spaceinvaders.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
 * This class is responsible to draw the scene on the screen. It extends the class SurfaceView that
 * provides a drawing surface where user can draw his interface in a separate thread that we call UI
 * Thread.
 *
 * מחלקה זו אחראית ללצייר את הסצינה על המסך.
 *  זה עושה extend ל- SurfaceView אשר מספקת drawing surface איפה שהמשתמש יכול לצייר את ה-interface ב-thread חיצוני שנקרא לו UI Thread
 */
public class AndroidFastRenderView extends SurfaceView implements Runnable {
    AndroidGame game;
    // The frame buffer
    // ה-frame buffer
    Bitmap framebuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;
    
    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
    }

    /*
     * Resume the UI Thread.
     *
     * תמשיך את ה-UI Thread
     */
    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();         
    }

    /*
     * This is the run method executed in the UI Thread. It contains the Game Loop.
     *
     * זה המתודה run המבוצעת ב-UI Thread. היא מכילה את ה-Game Loop
     */
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        // This is the Game Loop
        // זה הוא ה-Game Loop
        while(running) {
            // draw only if surface is valid
            // מצייר אם ה-surface תקף
            if(!holder.getSurface().isValid())
                continue;

            // calculate the time required to update/draw the previous frame.
            // לחשב את הזמן שלוקח לפריים הקודם לעשות update ו-draw
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            // update and draw the game spending for each phase a deltaTime. This is necessary to
            // avoid to have on high performance hardware a game too quick. The drawing occurs on
            // frame buffer and not drectly on screen. The reason is that if we draw directly on
            // screen the update will not be immediate
            /*
            * היא עושה update ו-draw ל-deltaTime המבוזבז על המשחק בכל שלב.
            * זה הכרחי בכדיי להימנע ממצב שבו יהיה למשחק on high performance hardware יותר מדי מהר.
            * הציור מתרחש על ה-frame buffer ולא ישירות על המסך.
            * הסיבה היא שאם נצייר ישירות על המסך ה-update לא יהיה מיידית.
            * */
            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().draw(deltaTime);
            
            // Once the frame buffer is ready the surface is locked and it will be drawn on the
            // screen. When completed the lock is release.
            // ברגע שה=-frame buffer מוכן ה-surface נעול והוא וזב ייצויר על המסך.
            //כשהיא תסתיים הנעילה משתחררת
            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(framebuffer, null, dstRect, null);                           
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /*
     * Pause the UI Thread.
     * משעה את ה-UI Thread
     */
    public void pause() {                        
        running = false;                        
        while(true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
                // תנסה שנית
            }
        }
    }        
}