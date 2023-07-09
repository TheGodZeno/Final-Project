package com.example.spaceinvaders.framework;

/**
 * A Sound is a short audio clip that can be played numerous times in parallel. It's completely
 * loaded into memory so only load small audio files. Call the {@link #dispose()} method when
 * you're done using the Sound.
 *
 * Sound instances are created via a call to {@link Audio#newSound(String)}.
 */
public interface Sound {
    /**
     * Plays the sound. If the sound is already playing, it will be played again, concurrently.
     * @param volume the volume in the range [0,1]
     * @return the id of the sound instance if successful, or -1 on failure.
     */
    void play(float volume);

    /**
     * Releases all the resources.
     */
    void dispose();
}
