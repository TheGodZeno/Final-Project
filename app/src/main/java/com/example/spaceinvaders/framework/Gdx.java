package com.example.spaceinvaders.framework;

/**
 * Environment class holding references to the {@link Game}, {@link Graphics}, {@link Audio},
 * {@link FileIO} and {@link Input} instances. The references are held in public static fields which
 * allows static access to all sub systems. Do not use Graphics in a thread that is not the
 * rendering thread.
 *
 * This is normally a design faux pas but in this case is better than the alternatives.
 */
public class Gdx {
    public static Game game;
    public static Graphics graphics;
    public static Audio audio;
    public static Input input;
    public static FileIO fileIO;
}
