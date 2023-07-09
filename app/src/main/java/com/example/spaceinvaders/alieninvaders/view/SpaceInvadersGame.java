package com.example.spaceinvaders.alieninvaders.view;

import com.example.spaceinvaders.framework.Screen;
import com.example.spaceinvaders.framework.impl.AndroidGame;


//This class represents the main activity of the Alien Invaders game.
// מחלקה זו מייצגת את ה-main activity של המשחק

public class SpaceInvadersGame extends AndroidGame {
    /*
     * The first screen of the Alien Invaders game is the LoadingScreen used to load all the assets in memory.
     * Usually these screen have a progress bar that represents the percentace of work done. In our
     * case we avoided this complication because the assets are loaded very quickly.
     */

    /*
    * המסך הראשון של המשחק הוא ה-LoadingScreen המשמש לטעינת כל ה-assets בזכרון.
    * ברוב הפעמים המסך הזה יציג progress bar כדיי להציג את האחוז של העבודה שנשעה. במקרה שלנו נמנענו מזה כי ה-assets נטענים מהר מאוד.
   */
    public Screen getStartScreen() {
        return new LoadingScreen();
    }
}