package com.example.tanya.testtaskstackoverflow.sqlite;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tanya on 07.05.2017.
 * Класс для создания и обновления БД
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stckovf.db";

    public DbHelper(Application application) {
        super(application.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AnswerAdapter.createTable());
        db.execSQL(TagAdapter.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.TABLE_ANSWER);
        onCreate(db);
    }
}
