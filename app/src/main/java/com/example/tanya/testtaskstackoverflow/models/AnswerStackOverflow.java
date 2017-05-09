package com.example.tanya.testtaskstackoverflow.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 * Модель - Ответ на вопрос заданный в поисковой строке найденный на сайте stackoverflow.com
 */

public class AnswerStackOverflow implements Serializable{

    private long mAnswerId;
    private ArrayList<String> mTags;
    private int mScore;
    private long mCreationDate;
    private long mQuestionId;
    private String mLink;
    private String mTitle;
    private String mBody;
    private String mDisplayNameUser;

    public AnswerStackOverflow() {
        mTags = new ArrayList<>();
        mLink = "";
        mTitle = "";
        mBody = "";
        mDisplayNameUser = "";
    }

    public long getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(long answerId) {
        mAnswerId = answerId;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
    }

    public String getDisplayNameUser() {
        return mDisplayNameUser;
    }

    public void setDisplayNameUser(String displayNameUser) {
        mDisplayNameUser = displayNameUser;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(long creationDate) {
        mCreationDate = creationDate;
    }

    public long getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(long questionId) {
        mQuestionId = questionId;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }
}
