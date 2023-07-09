package com.example.spaceinvaders.alieninvaders.view;

import com.example.spaceinvaders.alieninvaders.model.Settings;
import com.example.spaceinvaders.framework.Gdx;
import com.example.spaceinvaders.framework.Graphics;
import com.example.spaceinvaders.framework.Input;
import com.example.spaceinvaders.framework.Rectangle;
import com.example.spaceinvaders.framework.Screen;

import java.util.List;

/*
 * This class represents the start screen. It contains the logo and the main menu with three
 * options:
 *     Play
 *     Highscores
 *     Quit
 *
 *  It also has a button to activate/deactivate sound.
 */

/*
* מחלקה זו מייצגת את ה-start screen. היא מכילה את הלוגו ואת התפריט אם שלושה אפשריות:
* Play
* High-scores
* Quit
*
* היא מכילה גם כפתור להדלה/כיבוי הצלילים באפליקצייה
*/
public class StartScreen implements Screen {
    private Rectangle backgroundBounds;
    private Rectangle logoBounds;
    private Rectangle soundButtonBounds;
    private Rectangle mainMenuBounds;
    private Rectangle playMenuBounds;
    private Rectangle highscoresMenuBounds;
    private Rectangle quitMenuBounds;

  public StartScreen() {
       backgroundBounds=new Rectangle(0, 0, 320, 480);
        logoBounds=new Rectangle(32, 20, 257, 136);
        soundButtonBounds=new Rectangle(32, 370, 50, 50);
        mainMenuBounds=new Rectangle(84, 220, 153, 124);
        playMenuBounds=new Rectangle(64, 220, 192, 42);
        highscoresMenuBounds=new Rectangle(64, 220 + 42, 192, 42);
        quitMenuBounds=new Rectangle(64, 220 + 84, 192, 42);

    }

    /*
     * Check the user input and if one the the folloing things could occurs:
     *     - Play the game
     *     - See Highscores
     *     - Quit game
     *     - Activate/deactivate sound
     */

    /*
    *בודק את קלט המשתמש ואם אחד הדברים הבאים יכול לקרות:
    * - Play the game
    * - See the High-scores
    * - Quit the game
    * - Activate/deactivate sound
    */
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = Gdx.input.getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                // activate/deactivate sound
                // הדלקה/כיבוי צלילים
                if(soundButtonBounds.contains(event.x, event.y)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                // play the game
                // לשחק המשחק
                if(playMenuBounds.contains(event.x, event.y)) {
                    Gdx.game.setScreen(new GameScreen());
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                // see highscores.
                // לראות טבלת תוצאות
                if(highscoresMenuBounds.contains(event.x, event.y)) {
                    Gdx.game.setScreen(new HighscoreScreen());
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                // quit the game.
                // לצאת מהמשחק
                if(quitMenuBounds.contains(event.x, event.y)) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    System.exit(1);
                    return;
                }
            }
        }
    }


    /*
     * Draw the start screen.
     * מצייר את ה-start screen
     */
   public void draw(float deltaTime) {
        Graphics g = Gdx.graphics;

        // draw the background
        // מצייר את הרקע
        g.drawPixmap(Assets.startscreen, backgroundBounds.getX(), backgroundBounds.getY());
        // draw the logo
        // מצייר את הלוגו
        g.drawPixmap(Assets.logo, logoBounds.getX(), logoBounds.getY());
        // draw the main menu
        // מצייר את התפריט הראשי
        g.drawPixmap(Assets.mainmenu, mainMenuBounds.getX(), mainMenuBounds.getY());
        // draw the sound button depending on sound status.
        // מצייר את הכפתור של הצליל תלוי מה המצב שלו
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, soundButtonBounds.getX(), soundButtonBounds.getY(), 0, 0,
                    soundButtonBounds.getWidth()+1, soundButtonBounds.getHeight()+1);
        else
            g.drawPixmap(Assets.buttons, soundButtonBounds.getX(), soundButtonBounds.getY(), 50, 0,
                    soundButtonBounds.getWidth()+1, soundButtonBounds.getHeight()+1);
    }

    /*
     * The screen is paused.
     *המסך מושעה.
     */
    public void pause() {
        Settings.save(Gdx.fileIO);
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
