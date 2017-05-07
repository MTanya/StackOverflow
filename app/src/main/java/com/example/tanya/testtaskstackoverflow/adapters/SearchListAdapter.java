package com.example.tanya.testtaskstackoverflow.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.activity.FirstActivity;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Tanya on 07.05.2017.
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<AnswerStackOverflow> mAnswerStackOverflowmms;
    private boolean mIsMore;
    private FirstActivity mActivity;

    private static final int ITEM = 0;
    private static final int MORE = 1;

    public SearchListAdapter(FirstActivity activity) {
        mActivity = activity;
        mAnswerStackOverflowmms = new ArrayList<>();
    }

    public void updateList(ArrayList<AnswerStackOverflow> answers, boolean isMore) {
        mIsMore = isMore;
        if (answers != null ) {
            if (mActivity.mPage == 1) {
                mAnswerStackOverflowmms = answers;
                notifyDataSetChanged();
                mActivity.mLayoutManager.scrollToPosition(0);
            } else  {
                mAnswerStackOverflowmms.addAll(answers);
                notifyDataSetChanged();
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MORE) {
            return new MoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.more_item, parent, false));
        } else {
            return new SearchItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchItemViewHolder) {
            AnswerStackOverflow answerStackOverflow = mAnswerStackOverflowmms.get(position);
            ((SearchItemViewHolder) holder).setData(answerStackOverflow);
        }
    }

    @Override
    public int getItemCount() {
        return mAnswerStackOverflowmms.size() + (mIsMore ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsMore && position == mAnswerStackOverflowmms.size()) {
            return MORE;
        } else {
            return ITEM;
        }
    }

    private class SearchItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvVotesCount;
        private TextView tvQTitle;
        private TextView tvQBody;
        private TextView tvDateUser;
        private TextView tvTags;
        private LinearLayout llRow;

        SearchItemViewHolder(final View itemView) {
            super(itemView);

            tvVotesCount = (TextView) itemView.findViewById(R.id.tvVotesCount);
            tvQTitle = (TextView) itemView.findViewById(R.id.tvQ);
            tvQBody = (TextView) itemView.findViewById(R.id.tvAnswerPreview);
            tvDateUser = (TextView) itemView.findViewById(R.id.tvDateUser);
            tvTags = (TextView) itemView.findViewById(R.id.tvTags);
            llRow = (LinearLayout) itemView.findViewById(R.id.row);

            llRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    mActivity.goToAnswer(mAnswerStackOverflowmms.get(pos));
                }
            });
        }

        private void setData(AnswerStackOverflow query) {
            tvQTitle.setText("Q: " + Html.fromHtml(query.getTitle()).toString());
            String body = Html.fromHtml(query.getBody()).toString();
            tvQBody.setText(body);
            tvVotesCount.setText(query.getScore()+"");

            StringBuilder tag = new StringBuilder();
            ArrayList<String> tags = query.getTags();
            for (int i = 0; i < tags.size(); i++) {
                tag.append("  ").append(tags.get(i));
            }

            String tstr = tag.toString();
            if (!tstr.isEmpty()) tstr = "Tags: "+ tstr;

            tvTags.setText(tstr);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd ", Locale.US);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(query.getCreationDate());

            String user = query.getDisplayNameUser();
            tvDateUser.setText(sdf.format(calendar.getTime()) + " by " + user);
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
