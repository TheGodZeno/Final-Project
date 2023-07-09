package com.example.spaceinvaders.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provides standard access to the filesystem, classpath, Android SD card, and Android assets
 * directory.
 */
public interface FileIO {
    /**
     * Read the asset specified by fileName and return the input stream.
     * directory.
     */
    InputStream readAsset(String fileName) throws IOException;

    /**
     * Read the file, on external storage, specified by fileName and return the input stream.
     * directory.
     */
    InputStream readFile(String fileName) throws IOException;

    /**
     * Write the file, on external storage, specified by fileName and return the input stream.
     * directory.
     */
    OutputStream writeFile(String fileName) throws IOException;
}
