package com.example.spaceinvaders.framework;

/**
 * An <code>Game</code> is the main entry point of your project. It sets up a window and rendering
 * surface and manages the different aspects of your application, namely {@link Graphics},
 * {@link Audio}, {@link Input} and {@link FileIO}. Think of a Game being equivalent to Swing's
 * <code>JFrame</code> or Android's <code>Activity</code>.
 *
 * The Application interface provides you with a set of modules for graphics, audio, input and file i/o.
 *
 * {@link Graphics} offers you various methods to output visuals to the screen.
 *
 * {@link Audio} offers you various methods to output and record sound and music. This is achieved
 * via the Android media framework.
 *
 * {@link Input} offers you various methods to poll user input from the keyboard, touch screen,
 * mouse and accelerometer.
 *
 * {@link FileIO} offers you various methods to access internal and external files. An internal file
 * is a file that is stored near your application. On Android internal files are equivalent to
 * assets.On Android external files reside on the SD-card. If you know what you are doing you can
 * also specify absolute file names. Absolute filenames are not portable, so take great care when
 * using this feature.
 *
 * The <code>Game</code> also has a set of methods that you can use to query specific information
 * such as the operating system the application is currently running on and so forth. This allows
 * you to have operating system dependent code paths. It is however not recommended to use this
 * facilities.
 */
public interface Game {
    /**
     * @return the {@link Input} instance
     */
    Input getInput();

    /**
     * @return the {@link FileIO} instance
     */
    FileIO getFileIO();

    /**
     * @return the {@link Graphics} instance
     */
    Graphics getGraphics();

    /**
     * @return the {@link Audio} instance
     */
    Audio getAudio();

    /**
     * Dispose the current screen and set the input screen as new current screen.
     */
    void setScreen(Screen screen);

    /**
     * @return the current screen.
     */
    Screen getCurrentScreen();

    /**
     * Set the first screen of the game. This is usually implemented by the source code of the game.
     */
    Screen getStartScreen();
}