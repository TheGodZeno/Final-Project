package com.example.spaceinvaders.alieninvaders.view;

import android.util.Log;

import com.example.spaceinvaders.alieninvaders.model.Settings;
import com.example.spaceinvaders.framework.Gdx;
import com.example.spaceinvaders.framework.Graphics;
import com.example.spaceinvaders.framework.Graphics.PixmapFormat;
import com.example.spaceinvaders.framework.Screen;

/*
 * This class represents the loading screen. It load in memory all the assets used by the game.
 * Usually games show a progress bar in this screen. To simplify the code and since the assets are
 * loaded very quickly I avoided this complication.
 */

/*
* המחלקה הזו מייצגת את ה-loading screen. זה טוען מהזכרון את כל ה-assets המשושמים במחשק.
* בדרך כלל משחקים מראים כאן progress bar. בישביל לעשות את הקוד יותר פשוט ובגלל שה-assets נטענים מהר מאוד נמנענו משימוש בזה.
* */
public class LoadingScreen implements Screen {
    private static final String LOG_TAG = "Invaders.LoadingScreen";

    @Override
    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        Graphics g = Gdx.graphics;

        Assets.gamescreen = g.newPixmap("gamescreen.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.RGB565);

        // Screens
        // מסכים
        Assets.startscreen = g.newPixmap("startscreen.png", PixmapFormat.RGB565);
        Assets.highscoresscreen = Assets.startscreen;
        Assets.gameoverscreen = g.newPixmap("gameover.png", PixmapFormat.RGB565);

        // Menus
        // תפריטים
        Assets.mainmenu = g.newPixmap("mainmenu.png", PixmapFormat.RGB565);
        Assets.pausemenu = g.newPixmap("pausemenu.png", PixmapFormat.RGB565);
        Assets.readymenu = g.newPixmap("ready.png", PixmapFormat.ARGB4444);

        // Aliens
        // ה-Aliens
        Assets.enemyShipUgly1 = g.newPixmap("enemy-ship-ugly1.png", PixmapFormat.RGB565);
        Assets.enemyShipUgly2 = g.newPixmap("enemy-ship-ugly2.png", PixmapFormat.RGB565);
        Assets.enemyShipBad1 = g.newPixmap("enemy-ship-bad1.png", PixmapFormat.RGB565);
        Assets.enemyShipBad2 = g.newPixmap("enemy-ship-bad2.png", PixmapFormat.RGB565);
        Assets.enemyShipGood1 = g.newPixmap("enemy-ship-good1.png", PixmapFormat.RGB565);
        Assets.enemyShipGood2 = g.newPixmap("enemy-ship-good2.png", PixmapFormat.RGB565);

        // Ship
        // ה-Ship
        Assets.ship = g.newPixmap("ship.png", PixmapFormat.RGB565);
        Assets.shipLife = g.newPixmap("ship-life.png", PixmapFormat.RGB565);

        // Shields
        // ה-Shields
        Assets.shieldLarge = g.newPixmap("shield-large.png", PixmapFormat.RGB565);
        Assets.shieldMedium = g.newPixmap("shield-medium.png", PixmapFormat.RGB565);
        Assets.shieldSmall = g.newPixmap("shield-small.png", PixmapFormat.RGB565);

        // Projectiles
        // ה-Projectiles
        Assets.shipProjectile = g.newPixmap("projectile-ship.png", PixmapFormat.RGB565);
        Assets.enemyShipProjectile = g.newPixmap("projectile-enemy-ship.png", PixmapFormat.RGB565);

        // buttons and numbers
        // ה-כפתורים וה-מספרים
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.RGB565);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);

        // Audio effects
        // ה-Audio אפקטים
        Assets.explosion = Gdx.audio.newSound("explosion.wav");
        Assets.laserCanon = Gdx.audio.newSound("lasercanon.wav");
        Assets.laserClash = Gdx.audio.newSound("laserclash.wav");
        Assets.shieldImpact = Gdx.audio.newSound("shieldimpact.wav");
        Assets.click = Gdx.audio.newSound("click.ogg");
        Assets.bitten = Gdx.audio.newSound("bitten.ogg");

        // Music
        // מוזיקה
        Assets.musicInvaders = Gdx.audio.newMusic("invaders.wav");

        Settings.load(Gdx.fileIO);
        Gdx.game.setScreen(new StartScreen());
    }

    /*
     * Draw nothing.
     * לא מצייר כלום.
     */
    public void draw(float deltaTime) {
        Log.i(LOG_TAG, "draw -- begin");
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