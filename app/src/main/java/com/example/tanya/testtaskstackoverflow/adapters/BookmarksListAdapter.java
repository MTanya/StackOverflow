package com.example.tanya.testtaskstackoverflow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.activity.ThirdActivity;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;

import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 * Адаптер для RecyclerView в экране ThirdActivity
 */

public class BookmarksListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<AnswerStackOverflow> mAnswerStackOverflows;
    private ThirdActivity mActivity;

    public BookmarksListAdapter(ThirdActivity activity) {
        mActivity = activity;
        mAnswerStackOverflows = new ArrayList<>();
    }

    public void updateList(ArrayList<AnswerStackOverflow> answers) {
        if (answers != null ) {
            mAnswerStackOverflows = answers;
        } else {
            mAnswerStackOverflows.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false),
                true, mActivity);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            AnswerStackOverflow answerStackOverflow = mAnswerStackOverflows.get(position);
            ((ItemViewHolder) holder).setData(answerStackOverflow);
        }
    }

    @Override
    public int getItemCount() {
        return mAnswerStackOverflows.size();
    }


    public void search(String text) {
        ArrayList<AnswerStackOverflow> newList = new ArrayList<>();
        for (AnswerStackOverflow answer : mAnswerStackOverflows) {
            if (answer.getTitle().toLowerCase().contains(text)) {
                newList.add(answer);
            }
        }
        mAnswerStackOverflows = newList;
        notifyDataSetChanged();
    }
}
