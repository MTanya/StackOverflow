package com.example.tanya.testtaskstackoverflow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.activity.FirstActivity;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;

import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 * Адаптер для RecyclerView на экране FirstActivity
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<AnswerStackOverflow> mAnswerStackOverflows;
    private boolean mIsMore;
    private FirstActivity mActivity;

    private static final int ITEM = 0;
    private static final int MORE = 1;

    public SearchListAdapter(FirstActivity activity) {
        mActivity = activity;
        mAnswerStackOverflows = new ArrayList<>();
    }

    public void updateList(ArrayList<AnswerStackOverflow> answers, boolean isMore) {
        mIsMore = isMore;
        if (answers != null ) {
            if (mActivity.mPage == 1) {
                mAnswerStackOverflows = answers;
                notifyDataSetChanged();
                mActivity.mLayoutManager.scrollToPosition(0);
            } else  {
                mAnswerStackOverflows.addAll(answers);
                notifyDataSetChanged();
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MORE) {
            return new MoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.more_item, parent, false));
        } else {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false),
                    false, mActivity, mAnswerStackOverflows);
        }
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
        return mAnswerStackOverflows.size() + (mIsMore ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsMore && position == mAnswerStackOverflows.size()) {
            return MORE;
        } else {
            return ITEM;
        }
    }

    private class MoreViewHolder extends RecyclerView.ViewHolder {

        private Button mButtonMore;
        private ProgressBar mProgressBar;

        MoreViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            mButtonMore = (Button) itemView.findViewById(R.id.btnMore);
            mButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.mPage++;
                    mActivity.getData(mProgressBar, mButtonMore);
                    v.setVisibility(View.GONE);
                }
            });
        }
    }
}
