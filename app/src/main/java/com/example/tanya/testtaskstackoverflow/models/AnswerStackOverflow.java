package com.example.tanya.testtaskstackoverflow.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
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

    public void setAnswerId(long mAnswerId) {
        this.mAnswerId = mAnswerId;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<String> mTags) {
        this.mTags = mTags;
    }

    public String getDisplayNameUser() {
        return mDisplayNameUser;
    }

    public void setDisplayNameUser(String mDisplayNameUser) {
        this.mDisplayNameUser = mDisplayNameUser;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int mScore) {
        this.mScore = mScore;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(long mCreationDate) {
        this.mCreationDate = mCreationDate;
    }

    public long getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(long mQuestionId) {
        this.mQuestionId = mQuestionId;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }
}
