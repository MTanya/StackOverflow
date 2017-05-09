package com.example.tanya.testtaskstackoverflow.sqlite;

/**
 * Created by Tanya on 07.05.2017.
 * Класс с описаниями таблиц
 */

class Tables {

    static final String TABLE_ANSWER = "Answer";
    //columns
    static final String KEY_AnswerId = "AnswerId";
    static final String KEY_Score = "Score";
    static final String Key_CreationDate = "CreationDate";
    static final String KEY_QuestionId = "QuestionId";
    static final String KEY_Link = "Link";
    static final String KEY_Title = "Title";
    static final String KEY_Body = "Body";
    static final String KEY_DisplayNameUser = "DisplayNameUser";

    static final String TABLE_TAG = "Tag";
    //columns
    static final String KEY_TagId = "TagId";
    static final String KEY_Tag = "Tag";
}
