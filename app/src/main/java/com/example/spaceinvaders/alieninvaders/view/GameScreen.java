package com.example.spaceinvaders.alieninvaders.view;

import android.content.Context;
import android.util.Log;

import com.example.spaceinvaders.alieninvaders.model.SpaceInvadersWorld;
import com.example.spaceinvaders.alieninvaders.model.Settings;
import com.example.spaceinvaders.framework.Gdx;
import com.example.spaceinvaders.framework.Graphics;
import com.example.spaceinvaders.framework.Input.TouchEvent;
import com.example.spaceinvaders.framework.Rectangle;
import com.example.spaceinvaders.framework.Screen;
import com.example.spaceinvaders.framework.TextStyle;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/*
 * This class represents the game screen. The processing and rendering depends on the game state managed
 * by State pattern. The update and draw method are delegated to:
 *    GamePause.update, GamePause.draw
 *    GameReady.update, GameReady.draw
 *    GameRunning.update, GameRunning.draw
 *    GameOver.update, GameOver.draw
 *
 * depending on the status of the game.
 */


/*
* המחלקה הזו מייצגת את ה-game screen. ה-processing וה-rendering תלויים בסטטוס של המשחק על ידי ה-State pattern.
* המתודות של ה-update וה-draw ניתנות ל:
 *    GamePause.update, GamePause.draw
 *    GameReady.update, GameReady.draw
 *    GameRunning.update, GameRunning.draw
 *    GameOver.update, GameOver.draw
 * תלוי מה סטטוס המחשק.
 */
public class GameScreen implements Screen {


    private static final String LOG_TAG = "Alien.GameScreen";

    private Map<SpaceInvadersWorld.GameState, GameState> states = new EnumMap<SpaceInvadersWorld.GameState, GameState>(SpaceInvadersWorld.GameState.class);
    private boolean isShipMovingLeft=false;
    private boolean isShipMovingRight=false;
    private int shipMovingLeftPointer=-1;
    private int shipMovingRightPointer=-1;

    private Rectangle gameoverScreenBounds;
    private Rectangle gameScreenBounds;
    private Rectangle pauseButtonBounds;
    private Rectangle leftButtonBounds;
    private Rectangle rightButtonBounds;
    private Rectangle shootButtonBounds;
    private Rectangle xButtonBounds;
    private Rectangle pauseMenuBounds;
    private Rectangle readyMenuBounds;
    private Rectangle homeMenuBounds;

    private SpaceInvadersWorldRenderer renderer;

    private SpaceInvadersWorld.WorldListener worldListener;

    public GameScreen() {
        Log.i(LOG_TAG, "constructor -- begin");
        states.put(SpaceInvadersWorld.GameState.Paused, new GamePaused());
        states.put(SpaceInvadersWorld.GameState.Ready, new GameReady());
        states.put(SpaceInvadersWorld.GameState.Running, new GameRunning());
        states.put(SpaceInvadersWorld.GameState.GameOver, new GameOver());

        gameoverScreenBounds=new Rectangle(0, 0, 320, 480);
        gameScreenBounds=new Rectangle(0, 0, 320, 480);
        pauseButtonBounds=new Rectangle(5, 20, 50, 50);
        leftButtonBounds=new Rectangle(30, 425, 50, 50);
        rightButtonBounds=new Rectangle(100, 425, 50, 50);
        shootButtonBounds=new Rectangle(240, 425, 50, 50);
        pauseMenuBounds=new Rectangle(100, 100, 160, 48);
        readyMenuBounds=new Rectangle(65, 60, 188, 70);
        homeMenuBounds=new Rectangle(80, 148, 160, 48);
        xButtonBounds=new Rectangle(128, 200, 50, 50);

        renderer = new SpaceInvadersWorldRenderer();

        worldListener=new SpaceInvadersWorld.WorldListener() {
            public void explosion() {
                if(Settings.soundEnabled)
                    Assets.explosion.play(1);
            }
            public void laserClash() {
                if(Settings.soundEnabled)
                    Assets.laserClash.play(1);
            }
            public void shieldImpact() {
                if(Settings.soundEnabled)
                    Assets.shieldImpact.play(1);
            }
        };
        SpaceInvadersWorld.getInstance().setWorldListener(worldListener);
    }

    /*
     * The update method is delegated to:
     *    GamePause.update
     *    GameReady.update
     *    GameRunning.update
     *    GameOver.update
     *
     * depending on the status of the game.
     */

    /*
    * המתודה update ניתנה ל:
     *    GamePause.update
     *    GameReady.update
     *    GameRunning.update
     *    GameOver.update
     * תלוי מה סטטוס המשחק.
    * */
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        List<TouchEvent> touchEvents = Gdx.input.getTouchEvents();
        Gdx.input.getKeyEvents();
        states.get(SpaceInvadersWorld.getInstance().getState()).update(touchEvents, deltaTime);
    }

    /*
     * The draw method is delegated to:
     *    GamePause.draw
     *    GameReady.draw
     *    GameRunning.draw
     *    GameOver.draw
     *
     * depending on the status of the game.
     */

    /*
    * המתודה draw ניתנה ל:
     *    GamePause.update
     *    GameReady.update
     *    GameRunning.update
     *    GameOver.update
     * תלוי מה סטטוס האפליקצייה.
     */
    public void draw(float deltaTime) {
        Log.i(LOG_TAG, "draw -- begin");

        // draw the background
        // מצייר את רקע.
        Gdx.graphics.drawPixmap(Assets.gamescreen, gameScreenBounds.getX(), gameScreenBounds.getY());
        // render the game world.
        // "מציג" את המשחק.
        renderer.draw();
        // draw buttons
        // מצייר את הכפתורים.
        Gdx.graphics.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 50,
                leftButtonBounds.getWidth() + 1, leftButtonBounds.getHeight() + 1); // left button
        Gdx.graphics.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 0, 50,
                rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button
        Gdx.graphics.drawPixmap(Assets.buttons, shootButtonBounds.getX(), shootButtonBounds.getY(), 0, 200,
                shootButtonBounds.getWidth() + 1, shootButtonBounds.getHeight() + 1); // down button



        states.get(SpaceInvadersWorld.getInstance().getState()).draw();
    }

    /*
     * Draw text on the screen in the (x, y) position.
     * מצייר טקסט על המסך ב-(X, Y) פוזיציה.
     */
    public void drawText(String text, int x, int y) {
        Log.i(LOG_TAG, "drawText -- begin");
        int len = text.length();
        for (int i = 0; i < len; i++) {
            char character = text.charAt(i);

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

            Gdx.graphics.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    /*
     * The screen is paused.
     * המסך מושעה.
     */
    public void pause() {
        Log.i(LOG_TAG, "pause -- begin");

        if(SpaceInvadersWorld.getInstance().getState() == SpaceInvadersWorld.GameState.Running)
            SpaceInvadersWorld.getInstance().setState(SpaceInvadersWorld.GameState.Paused);

        if(SpaceInvadersWorld.getInstance().getState() == SpaceInvadersWorld.GameState.GameOver) {
            Settings.addScore(SpaceInvadersWorld.getInstance().getScore());
            Settings.save(Gdx.fileIO);
        }
    }



    /*
     * The abstract class representing a generic State. Used to implement the State pattern.
     * המחלקה המופשטת המייצגת מצב כללי. משומשת ליישום ה-State pattern
     */
    abstract class GameState {
        abstract void update(List<TouchEvent> touchEvents, float deltaTime);
        abstract void draw();
    }

    /*
     * This class represents the game screen in running state. It will be responsible to update and
     * draw when the game is running.
     *
     * מחלקה זו מייגצת את ה-game screen  במצצב שבו הוא רץ (running state). היא תהיה אחראית לשימוש ב-update ו-draw כאשר המשחק רץ
     */
    class GameRunning extends GameState {
        /*
         * Update the game when it is in running state. The method catch the user input and,
         * depending on it will update the world.
         *
         * מעדכן את המשחק כאשר הוא במצב ריצה (running state). המתודה קולטת את הקלט של המשתמש ותלוי במה הוא עשה היא מעדכן את העולם
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameRunning.update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                switch(event.type) {
                    case TouchEvent.TOUCH_UP:
                        // Finish move on left
                        // מסיים תזוזה שמאלה
                        if (shipMovingLeftPointer==event.pointer) {
                            isShipMovingLeft=false;
                            shipMovingLeftPointer=-1;
                        }
                        // Finish move on right
                        // מסיים תזוזה ימינה
                        if (shipMovingRightPointer==event.pointer) {
                            isShipMovingRight=false;
                            shipMovingRightPointer=-1;
                        }
                        break;
                    case TouchEvent.TOUCH_DRAGGED:
                        // Finish move on left
                        // מסיים תזוזה שמאלה
                        if (shipMovingLeftPointer==event.pointer) {
                            if(!leftButtonBounds.contains(event.x, event.y)) {
                                isShipMovingLeft=false;
                                shipMovingLeftPointer=-1;
                            }
                        }
                        // Finish move on right
                        // מסיים תזוזה ימינה
                        if (shipMovingRightPointer==event.pointer) {
                            if(!rightButtonBounds.contains(event.x, event.y)) {
                                isShipMovingRight=false;
                                shipMovingRightPointer=-1;
                            }
                        }
                        break;
                    case TouchEvent.TOUCH_DOWN:
                        if(pauseButtonBounds.contains(event.x, event.y)) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            SpaceInvadersWorld.getInstance().setState(SpaceInvadersWorld.GameState.Paused);
                            return;
                        }
                        // Move ship on the left
                        // מזיז את ה-ship שמאלה
                        if(leftButtonBounds.contains(event.x, event.y)) {
                            isShipMovingLeft=true;
                            shipMovingLeftPointer=event.pointer;
                        }
                        // Move ship on the right
                        // מזיז את ה-ship ימינה
                        if(rightButtonBounds.contains(event.x, event.y)) {
                            isShipMovingRight=true;
                            shipMovingRightPointer=event.pointer;
                        }
                        // Shoot the aliens
                        // יורה ב-aliens
                        if(shootButtonBounds.contains(event.x, event.y)) {
                            SpaceInvadersWorld.getInstance().getShip().shoot();
                            if(Settings.soundEnabled)
                                Assets.laserCanon.play(1);
                        }
                        break;
                }
            }

            if (isShipMovingLeft) {
                SpaceInvadersWorld.getInstance().getShip().moveLeft();
            }
            if (isShipMovingRight) {
                SpaceInvadersWorld.getInstance().getShip().moveRight();
            }

            SpaceInvadersWorld.getInstance().update(deltaTime);
            if (SpaceInvadersWorld.getInstance().getState() == SpaceInvadersWorld.GameState.GameOver) {
                if(Settings.soundEnabled)
                    Assets.bitten.play(1);
            }

            if (!SpaceInvadersWorld.getInstance().getShip().isAlive()) {
                SpaceInvadersWorld.getInstance().setState(SpaceInvadersWorld.GameState.GameOver);
            }

            if(Settings.soundEnabled)
                if (!Assets.musicInvaders.isPlaying()) {
                    Assets.musicInvaders.setLooping(true);
                    Assets.musicInvaders.play();
                }
        }

        /*
         * Draw the game in running state.
         * מצייר את המשחק במצב ריצה (running state).
         */
        void draw() {
            Log.i(LOG_TAG, "GameRunning.draw -- begin");
            // draw pause button
            // מצייר את כפתור השעייה
            Gdx.graphics.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
            // draw scores
            // מצייר תוצאות
            TextStyle style = new TextStyle();
            style.setColor(0xffffffff);
            style.setTextSize(14);
            style.setStyle(TextStyle.Style.BOLD);
            style.setAlign(TextStyle.Align.CENTER);
            Gdx.graphics.drawText("Score:", 100, 40, style);
            Gdx.graphics.drawText("" + SpaceInvadersWorld.getInstance().getScore(), 100, 60, style);
            Gdx.graphics.drawText("Highscore:", 200, 40, style);
            Gdx.graphics.drawText("" + Settings.highscores[0], 200, 60, style);
            Gdx.graphics.drawText("Level:", 300, 40, style);
            Gdx.graphics.drawText("" + SpaceInvadersWorld.getInstance().getLevel(), 300, 60, style);
        }
    }

    /*
     * This class represents the game screen in pause state. It will be responsible to update and
     * draw when the game is paused.
     *
   * מחלקה זו מייצגת את ה-game screen כאשר הוא בהשעייה(pause). הוא יהיה אחראי לעשות update ו-draw מתי שהמשחק בהשעייה.
     */

    Context context;
    public GameScreen(Context context) {
        this.context=context;
    }

    class GamePaused extends GameState  {
        /*
         * Update the game when it is in paused state. The method catch the user input and
         * depending on it will resume the game or return to the start screen.
         *
         * מעדכן את המשחק כאשר הוא במצב השעייה (paused state). המתודה קולטת את הקלט של המשתמש והיא תחזור למסך הבית או לתמשיך תלוי בבחירה שלו.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GamePaused.update -- begin");

            // Check if user asked to resume the game or come back to the start screen.
            // בודק אם המשתמש ביקש להמשיך את המשחק או לחזור למסך הבית.
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if (pauseMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        SpaceInvadersWorld.getInstance().setState(SpaceInvadersWorld.GameState.Running);
                        return;
                    }
                    if(homeMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        Gdx.game.setScreen(new StartScreen());
                        return;
                    }
                }
            }
            if(Settings.soundEnabled)
                if (Assets.musicInvaders.isPlaying())
                    Assets.musicInvaders.pause();
        }

        /*
         * Draw the game in paused state.
         * מצייר את המשחק במצב השעייה(paused state).
         */
        void draw() {
            Log.i(LOG_TAG, "GamePaused.draw -- begin");
            // draw the pause menu
            // מצייר את מסך ההשעייה
            Gdx.graphics.drawPixmap(Assets.pausemenu, pauseMenuBounds.getX(), pauseMenuBounds.getY());
        }
    }

    /*
     * This class represents the game screen in ready state. It will be responsible to update and
     * draw when the game is ready.
     *
     * מחלקה זו מיייצגת את ה-game screen במצה מוכן (ready state). הוא יהיה אחראי לעשות update ו-draw כאשר המשפק מוכן.
     */
    class GameReady extends GameState {
        /*
         * Update the game when it is in ready state. The method catch the user input and
         * resume the game.
         *
         * מעדכן את המשחק במצב מוכן (ready state). המתודה קולטת את הקלט של המשתמש וממשיכה את המשחק.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameReady.update -- begin");
            if(touchEvents.size() > 0)
                SpaceInvadersWorld.getInstance().setState(SpaceInvadersWorld.GameState.Running);
        }

        /*
         * Draw the game in ready state.
         * מצייר את המשחק במצב מוכן.
         */
        void draw() {
            Log.i(LOG_TAG, "GameReady.draw -- begin");
            // draw ready menu
            // מצייר את התפריט המוכן (ready menu).
            Gdx.graphics.drawPixmap(Assets.readymenu, readyMenuBounds.getX(), readyMenuBounds.getY());
        }
    }

    /*
     * This class represents the game screen when it is over. It will be responsible to update and
     * draw when the game is over.
     *
     * מחלקה זו מייצגת את ה-game screen כאשר המשחק נגמר. הוא יהיה אחראי לעשות update ו-draw כאשר המחשק נגמר.
     */
    class GameOver extends GameState {
        /*
         * Update the game when it is over. The method catch the user input and return to the
         * start screen.
         *
         * מעכן את המשחק כשהוא נגמר. המתודה קולטת את הקלט של השחקן ומחזירה אותו למסך הבית.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameOver.update -- begin");
            // check if the x button is pressed.
            // בודק אם הכפתור איקס נלחץ
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if (xButtonBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        Gdx.game.setScreen(new StartScreen());
                        SpaceInvadersWorld.getInstance().clear();
                        return;
                    }
                }
            }
            if(Settings.soundEnabled)
                if (Assets.musicInvaders.isPlaying())
                    Assets.musicInvaders.stop();
        }

        /*
         * Draw the game when it is over.
         *
         * מצייר את המשחק כשהוא נגמר.
         */
        void draw() {
            Log.i(LOG_TAG, "GameOver.draw -- begin");
            Graphics g = Gdx.graphics;

            // draw scores
            // מצייר תוצאות.
            TextStyle style = new TextStyle();
            style.setColor(0xffffffff);
            style.setTextSize(14);
            style.setStyle(TextStyle.Style.BOLD);
            style.setAlign(TextStyle.Align.CENTER);
            Gdx.graphics.drawText("Score:", 100, 40, style);
            Gdx.graphics.drawText("" + SpaceInvadersWorld.getInstance().getScore(), 100, 60, style);
            Gdx.graphics.drawText("Highscore:", 200, 40, style);
            Gdx.graphics.drawText("" + Settings.highscores[0], 200, 60, style);
            Gdx.graphics.drawText("Level:", 300, 40, style);
            Gdx.graphics.drawText("" + SpaceInvadersWorld.getInstance().getLevel(), 300, 60, style);

            // draw pause button
            // מצייר כפתורים של השעייה
            Gdx.graphics.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button

            // draw back transparent background
            // מצייר רקע
            g.drawPixmap(Assets.gameoverscreen, gameoverScreenBounds.getX(), gameoverScreenBounds.getY());
            // draw X button
            // מצייר את הכפתור X
            g.drawPixmap(Assets.buttons, xButtonBounds.getX(), xButtonBounds.getY(), 0, 100,
                    xButtonBounds.getWidth()+1, xButtonBounds.getHeight()+1);
            // draw final score
            // מצייר תוצאה סופית
            drawText(""+ SpaceInvadersWorld.getInstance().getScore(), 180, 280);
        }
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



