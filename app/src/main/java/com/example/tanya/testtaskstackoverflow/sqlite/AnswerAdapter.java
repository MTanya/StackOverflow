package com.example.tanya.testtaskstackoverflow.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;

import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 */

public class AnswerAdapter {

    private static final String TAG = AnswerAdapter.class.getSimpleName();

    public AnswerAdapter() {
    }

    static String createTable() {
        return "CREATE TABLE " + Tables.TABLE_ANSWER  + "("
                + Tables.KEY_AnswerId  + " PRIMARY KEY ,"
                + Tables.KEY_Score + " INTEGER, "
                + Tables.Key_CreationDate + " INTEGER, "
                + Tables.KEY_QuestionId + " INTEGER, "
                + Tables.KEY_Link + " TEXT, "
                + Tables.KEY_Title + " TEXT, "
                + Tables.KEY_Body + " TEXT, "
                + Tables.KEY_DisplayNameUser + " TEXT"
                +" )";
    }

    public int insert(AnswerStackOverflow answer) {
        int answerId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Tables.KEY_AnswerId, answer.getAnswerId());
        values.put(Tables.KEY_Score, answer.getScore());
        values.put(Tables.Key_CreationDate, answer.getCreationDate());
        values.put(Tables.KEY_QuestionId, answer.getQuestionId());
        values.put(Tables.KEY_Link, answer.getLink());
        values.put(Tables.KEY_Title, answer.getTitle());
        values.put(Tables.KEY_Body, answer.getBody());
        values.put(Tables.KEY_DisplayNameUser, answer.getDisplayNameUser());

        // Inserting Row
        answerId=(int)db.insert(Tables.TABLE_ANSWER, null, values);

        if (answerId > 0) {
            TagAdapter tagAdapter = new TagAdapter();
            tagAdapter.insert(db, answer.getAnswerId(), answer.getTags());
        }

        DatabaseManager.getInstance().closeDatabase();

        return answerId;
    }


    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Tables.TABLE_ANSWER,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public int getCountAnswers() {
        int count = 0;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT COUNT(*)"
                + " FROM " + Tables.TABLE_ANSWER
                ;

        Cursor cursor = db.query(Tables.TABLE_ANSWER,
                new String[] {"COUNT("+ Tables.KEY_AnswerId +") AS counter"},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                count = cursor.getInt(cursor.getColumnIndex("counter"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return count;
    }


    public ArrayList<AnswerStackOverflow> getAnswers(){
        ArrayList<AnswerStackOverflow> answers = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT *"
                + " FROM " + Tables.TABLE_ANSWER
                ;

        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                AnswerStackOverflow answerStackOverflow = new AnswerStackOverflow();
                answerStackOverflow.setAnswerId(cursor.getLong(cursor.getColumnIndex(Tables.KEY_AnswerId)));
                answerStackOverflow.setScore(cursor.getInt(cursor.getColumnIndex(Tables.KEY_Score)));
                answerStackOverflow.setCreationDate(cursor.getLong(cursor.getColumnIndex(Tables.Key_CreationDate)));
                answerStackOverflow.setQuestionId(cursor.getLong(cursor.getColumnIndex(Tables.KEY_QuestionId)));
                answerStackOverflow.setLink(cursor.getString(cursor.getColumnIndex(Tables.KEY_Link)));
                answerStackOverflow.setTitle(cursor.getString(cursor.getColumnIndex(Tables.KEY_Title)));
                answerStackOverflow.setBody(cursor.getString(cursor.getColumnIndex(Tables.KEY_Body)));
                answerStackOverflow.setDisplayNameUser(cursor.getString(cursor.getColumnIndex(Tables.KEY_DisplayNameUser)));

                answers.add(answerStackOverflow);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        for (AnswerStackOverflow answerStackOverflow : answers) {
            TagAdapter tagAdapter = new TagAdapter();
            answerStackOverflow.setTags(tagAdapter.getTags(answerStackOverflow.getAnswerId()));
        }


        return answers;

    }
}
