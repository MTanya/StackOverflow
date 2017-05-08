package com.example.tanya.testtaskstackoverflow.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.activity.ThirdActivity;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Tanya on 07.05.2017.
 */

public class BookmarksListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<AnswerStackOverflow> mAnswerStackOverflowmms;
    private ThirdActivity mActivity;

    public BookmarksListAdapter(ThirdActivity activity) {
        mActivity = activity;
        mAnswerStackOverflowmms = new ArrayList<>();
    }

    public void updateList(ArrayList<AnswerStackOverflow> answers) {
        if (answers != null ) {
            mAnswerStackOverflowmms = answers;
        } else {
            mAnswerStackOverflowmms.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookmarksItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookmarksItemViewHolder) {
            AnswerStackOverflow answerStackOverflow = mAnswerStackOverflowmms.get(position);
            ((BookmarksItemViewHolder) holder).setData(answerStackOverflow);
        }
    }

    @Override
    public int getItemCount() {
        return mAnswerStackOverflowmms.size();
    }

    private class BookmarksItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvVotesCount;
        private TextView tvQTitle;
        private TextView tvQBody;
        private TextView tvDateUser;
        private TextView tvTags;
        private RelativeLayout llRow;
        private Toolbar toolbar;

        BookmarksItemViewHolder(final View itemView) {
            super(itemView);

            tvVotesCount = (TextView) itemView.findViewById(R.id.tvVotesCount);
            tvQTitle = (TextView) itemView.findViewById(R.id.tvQ);
            tvQBody = (TextView) itemView.findViewById(R.id.tvAnswerPreview);
            tvDateUser = (TextView) itemView.findViewById(R.id.tvDateUser);
            tvTags = (TextView) itemView.findViewById(R.id.tvTags);
            llRow = (RelativeLayout) itemView.findViewById(R.id.row);
            toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            toolbar.inflateMenu(R.menu.menu_bookmark);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.del_bookmark) {
                        int pos = getAdapterPosition();
                        mActivity.deleteFromBookmarks(mAnswerStackOverflowmms.get(pos));
                    }
                    return true;
                }
            });

            llRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    mActivity.goToAnswer(mAnswerStackOverflowmms.get(pos));
                }
            });
        }

        private void setData(AnswerStackOverflow query) {
            tvQTitle.setText("Q: ".concat(Html.fromHtml(query.getTitle()).toString()));
            String body = Html.fromHtml(query.getBody()).toString();
            tvQBody.setText(body);
            tvVotesCount.setText(String.valueOf(query.getScore()));

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

    public void search(String text) {
        ArrayList<AnswerStackOverflow> newList = new ArrayList<>();
        for (AnswerStackOverflow answer : mAnswerStackOverflowmms) {
            if (answer.getTitle().toLowerCase().contains(text)) {
                newList.add(answer);
            }
        }
        mAnswerStackOverflowmms = newList;
        notifyDataSetChanged();
    }
}
