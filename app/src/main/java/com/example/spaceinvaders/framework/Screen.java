package com.example.spaceinvaders.framework;

/**
 * Represents one of many application screens, such as a main menu, a settings menu, the game screen
 * and so on.
 *
 * Note that {@link #dispose()} is not called automatically.
 *
 * @see Game
 */
public interface Screen {
    /**
     * Called when the screen should update itself.
     * @param deltaTime The time in seconds since the last update.
     */
    void update(float deltaTime);

    /**
     * Called when the screen should render itself.
     * @param deltaTime The time in seconds since the last render.
     */
    void draw(float deltaTime);

    /**
     * Called when the screen is paused.
     */
    void pause();

    /**
     * Called when the screen is resumed.
     */
    void resume();

    /**
     * Called when the screen is disposed.
     */
    void dispose();
}
