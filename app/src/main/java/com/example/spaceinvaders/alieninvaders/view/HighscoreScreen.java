package com.example.spaceinvaders.alieninvaders.view;

import com.example.spaceinvaders.alieninvaders.model.Settings;
import com.example.spaceinvaders.framework.Gdx;
import com.example.spaceinvaders.framework.Graphics;
import com.example.spaceinvaders.framework.Input.TouchEvent;
import com.example.spaceinvaders.framework.Rectangle;
import com.example.spaceinvaders.framework.Screen;

import java.util.List;

/*
 * This class represents the highscores screen. The screen show the top five scores achieved by the
 * user.
 *
 * המחלקה הזו מייצגת ה-high scores. המסך יציג את חמשת התוצאות הגבוהות ביותר של המשתמש.
 */
public class HighscoreScreen implements Screen {
    private Rectangle backgroundBounds;
    private Rectangle backButtonBounds;

    String lines[] = new String[5];

    /*
     * Initialize the screen with the following scores: 100, 80, 50, 30, 10.
     *
     * מתחיל את המסך עם התוצאות הבאות: 100, 80, 50, 30, 10.
     */
    public HighscoreScreen() {
        for (int i = 0; i < 5; i++) {
            lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
        }

        backgroundBounds=new Rectangle(0, 0, 320, 480);
        backButtonBounds=new Rectangle(32, 370, 50, 50);
    }

    /*
     * Check the user input and if he press the back button go back to the start screen.
     *
     * בודק אם המשתמש לחץ על הכפתור של החזרה ואז שולח אותו למסך הבית.
     */
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = Gdx.input.getTouchEvents();
        Gdx.input.getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (backButtonBounds.contains(event.x, event.y)) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    Gdx.game.setScreen(new StartScreen());
                    return;
                }
            }
        }
    }

    /*
     * Draw the highscores screen.
     *
     * מצייר את המסך high scores.
     */
    public void draw(float deltaTime) {
        Graphics g = Gdx.graphics;

        // draw the background.
        //מצייר את הרקע
        g.drawPixmap(Assets.highscoresscreen, backgroundBounds.getX(), backgroundBounds.getY());

        int y = 100;
        for (int i = 0; i < 5; i++) {
            drawText(g, lines[i], 20, y);
            y += 50;
        }
        // draw the back button.
        // מצייר את כפתו החזרה.
        g.drawPixmap(Assets.buttons, backButtonBounds.getX(), backButtonBounds.getY(), 50, 50,
                backButtonBounds.getWidth()+1, backButtonBounds.getHeight()+1);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
    
            if (character == ' ') {
                x += 20;
                continue;
            }
    
            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }
    
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    /*
     * The screen is paused.
     *המסך מושעה.
     */
    public void pause() {

    }

    /*
     * The screen is resumed.
     * המסך מתחיל שוב.
     */
    public void resume() {

    }

    /*
     * The screen is disposed.
     * המסך סולק מן המערכת.
     */
    public void dispose() {

    }
}
