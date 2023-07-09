package com.example.spaceinvaders.alieninvaders.view;

import com.example.spaceinvaders.framework.Music;
import com.example.spaceinvaders.framework.Pixmap;
import com.example.spaceinvaders.framework.Sound;

/*
 This class contains the global references to all the assets used in AlienInvadersGame game.
 */

/*
* במחלקה זו נמאים כל ההפניות הגלובליות לכל ה-assets המשושמים במשחק.
* */
public class Assets {
    // the logo asset
    // ה-asset של הלוגו
    public static Pixmap logo;

    // the screen used in AlienInvadersWorld game
    //ה-מסכים המשומשים במשחק
    public static Pixmap gamescreen;
    public static Pixmap startscreen;
    public static Pixmap highscoresscreen;
    public static Pixmap gameoverscreen;

    // the menu used in AlienInvadersWorld game
    // התפריט המשומש במשחק
    public static Pixmap mainmenu;
    public static Pixmap pausemenu;
    public static Pixmap readymenu;

    // buttons and numbers
    // כפתורים ו-מספרים
    public static Pixmap buttons;
    public static Pixmap numbers;

    // the Alien bitmaps
    // ה-bitmaps של ה-Alien
    public static Pixmap enemyShipBad1;
    public static Pixmap enemyShipBad2;
    public static Pixmap enemyShipUgly1;
    public static Pixmap enemyShipUgly2;
    public static Pixmap enemyShipGood1;
    public static Pixmap enemyShipGood2;

    // the Ship bitmaps
    // ה-bitmaps של ה-Ship
    public static Pixmap ship;
    public static Pixmap shipLife;

    // the Shield bitmaps
    // ה-bitmaps של ה-Shield
    public static Pixmap shieldLarge;
    public static Pixmap shieldMedium;
    public static Pixmap shieldSmall;

    // the Shield bitmaps
    // ה-bitmaps של ה-projectile
    public static Pixmap shipProjectile;
    public static Pixmap enemyShipProjectile;

    // sounds
    // סאונדים
    public static Sound explosion;
    public static Sound laserCanon;
    public static Sound laserClash;
    public static Sound shieldImpact;
    public static Sound click;
    public static Sound bitten;

    // music
    // מוזיקה
    public static Music musicInvaders;
}
