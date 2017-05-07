package com.example.tanya.testtaskstackoverflow.models;

import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 */

public class AnswerByApi {

    private ArrayList<AnswerStackOverflow> mQueries;
    private boolean mIsHasMore;
    private int mQuotaMax;
    private int mQuotaRemaining;

    public AnswerByApi() {
        mQueries = new ArrayList<>();
        mIsHasMore = false;
    }

    public ArrayList<AnswerStackOverflow> getQueries() {
        return mQueries;
    }

    public void setQueries(ArrayList<AnswerStackOverflow> mQuery) {
        this.mQueries = mQuery;
    }

    public boolean isHasMore() {
        return mIsHasMore;
    }

    public void setHasMore(boolean mIsHasMore) {
        this.mIsHasMore = mIsHasMore;
    }

    public int getQuotaMax() {
        return mQuotaMax;
    }

    public void setQuotaMax(int mQuotaMax) {
        this.mQuotaMax = mQuotaMax;
    }

    public int getmQuotaRemaining() {
        return mQuotaRemaining;
    }

    public void setQuotaRemaining(int mQuotaRemaining) {
        this.mQuotaRemaining = mQuotaRemaining;
    }
}
