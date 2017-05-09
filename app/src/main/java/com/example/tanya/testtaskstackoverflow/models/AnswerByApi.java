package com.example.tanya.testtaskstackoverflow.models;

import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 * Модель Ответ от сервера stackoverflow.com
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

    public void setQueries(ArrayList<AnswerStackOverflow> query) {
        mQueries = query;
    }

    public boolean isHasMore() {
        return mIsHasMore;
    }

    public void setHasMore(boolean isHasMore) {
        mIsHasMore = isHasMore;
    }

    public int getQuotaMax() {
        return mQuotaMax;
    }

    public void setQuotaMax(int quotaMax) {
        mQuotaMax = quotaMax;
    }

    public int getQuotaRemaining() {
        return mQuotaRemaining;
    }

    public void setQuotaRemaining(int quotaRemaining) {
        mQuotaRemaining = quotaRemaining;
    }
}
