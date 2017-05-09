package com.example.tanya.testtaskstackoverflow.sigleton;

import android.app.Application;
import android.content.Context;

import com.example.tanya.testtaskstackoverflow.sqlite.DatabaseManager;
import com.example.tanya.testtaskstackoverflow.sqlite.DbHelper;

/**
 * Created by Tanya on 07.05.2017.
 * Инициализируем БД для локального хранения закладок с ответами
 */

public class Apllication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper dbHelper = new DbHelper(this);
        DatabaseManager.initializeInstance(dbHelper);
    }
}
