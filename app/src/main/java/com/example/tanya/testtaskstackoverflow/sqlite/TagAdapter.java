package com.example.tanya.testtaskstackoverflow.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Tanya on 07.05.2017.
 */

public class TagAdapter {

    private static final String TAG = TagAdapter.class.getSimpleName();

    public static String createTable() {
        return "CREATE TABLE " + Tables.TABLE_TAG  + "("
                + Tables.KEY_TagId  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + Tables.KEY_AnswerId + " INTEGER, "
                + Tables.KEY_Tag + " TEXT"
                +" )";
    }

    public int insert(SQLiteDatabase db, long answerId, ArrayList<String> tags) {

        int tagId = 0;
        for (String tag : tags) {
            ContentValues values = new ContentValues();
            values.put(Tables.KEY_AnswerId, answerId);
            values.put(Tables.KEY_Tag, tag);
            tagId = (int) db.insert(Tables.TABLE_TAG, null, values);
        }


        return tagId;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Tables.TABLE_TAG,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public ArrayList<String> getTags(long answerId) {
        ArrayList<String> tags = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT *"
                + " FROM " + Tables.TABLE_TAG
                + " WHERE " + Tables.KEY_AnswerId + " = " + answerId
                ;

        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String tag = cursor.getString(cursor.getColumnIndex(Tables.KEY_Tag));

                if (tag != null) tags.add(tag);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return tags;
    }

}
