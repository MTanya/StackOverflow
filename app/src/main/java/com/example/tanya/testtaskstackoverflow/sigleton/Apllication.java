package com.example.tanya.testtaskstackoverflow.sigleton;

import android.app.Application;
import android.content.Context;

import com.example.tanya.testtaskstackoverflow.sqlite.DatabaseManager;
import com.example.tanya.testtaskstackoverflow.sqlite.DbHelper;

/**
 * Created by Tanya on 07.05.2017.
 */

public class Apllication extends Application {
    private static DbHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DbHelper(this);
        DatabaseManager.initializeInstance(dbHelper);
    }
}
