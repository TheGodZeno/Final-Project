package com.example.spaceinvaders.framework.impl;

import android.content.res.AssetManager;
import android.os.Environment;

import com.example.spaceinvaders.framework.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * This class implements the FileIO interface used to manage assets and files. The implementation is
 * based on Java classes FileInputStream and FileOutputStream and Android class AssetManager.
 *
 *מחלקה זו מיישמת אץ ה-FileIO interface המשומשת לניהול ה-assets וה-files.
 * היישום מבוסס על מחלקות Java :
 * FileInputStream
 * FileOutputStream
 * Android class AssetsManager
 */
public class AndroidFileIO implements FileIO {
    AssetManager assets;
    String externalStoragePath;

    public AndroidFileIO(AssetManager assets) {
        this.assets = assets;
        this.externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
    }

    @Override
    public InputStream readAsset(String fileName) throws IOException {
        return assets.open(fileName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(externalStoragePath + fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(externalStoragePath + fileName);
    }
}
