package com.example.tanya.testtaskstackoverflow.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.activity.FirstActivity;
import com.example.tanya.testtaskstackoverflow.activity.ThirdActivity;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Tanya on 09.05.2017.
 * Item для списков в FirstActivity и ThirdActivity
 */

class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView tvVotesCount;
    private TextView tvQTitle;
    private TextView tvQBody;
    private TextView tvDateUser;
    private TextView tvTags;

    private Activity mActivity;
    private FirstActivity firstActivity;
    private ThirdActivity thirdActivity;
    private ArrayList<AnswerStackOverflow> mAnswers;

    private SimpleDateFormat sdf;

    ItemViewHolder(View itemView, boolean isToolbar, Activity activity) {
        super(itemView);

        sdf = new SimpleDateFormat("MMM dd", Locale.US);

        tvVotesCount = (TextView) itemView.findViewById(R.id.tvVotesCount);
        tvQTitle = (TextView) itemView.findViewById(R.id.tvQ);
        tvQBody = (TextView) itemView.findViewById(R.id.tvAnswerPreview);
        tvDateUser = (TextView) itemView.findViewById(R.id.tvDateUser);
        tvTags = (TextView) itemView.findViewById(R.id.tvTags);
        RelativeLayout llRow = (RelativeLayout) itemView.findViewById(R.id.row);

        mActivity = activity;
        if (mActivity instanceof FirstActivity) firstActivity = (FirstActivity) mActivity;
        else if (mActivity instanceof ThirdActivity) thirdActivity = (ThirdActivity) mActivity;

        if (isToolbar) {
            Toolbar toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            toolbar.inflateMenu(R.menu.menu_bookmark);

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.del_bookmark) {
                        int pos = getAdapterPosition();
                        if (thirdActivity != null) {
                            mAnswers = thirdActivity.getAnswers();
                            thirdActivity.deleteFromBookmarks(mAnswers.get(pos));
                        }
                    }
                    return true;
                }
            });
        }

        llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                if (thirdActivity != null) {
                    mAnswers = thirdActivity.getAnswers();
                    thirdActivity.goToAnswer(mAnswers.get(pos));
                } else if (firstActivity != null) {
                    mAnswers = firstActivity.getAnswers();
                    firstActivity.goToAnswer(mAnswers.get(pos));
                }
            }
        });
    }

    void setData(AnswerStackOverflow query) {
        setTitle(query.getTitle());
        setBody(query.getBody());
        setVotesCount(String.valueOf(query.getScore()));
        setTags(query.getTags());
        setDateUser(query.getCreationDate(), query.getDisplayNameUser());
    }

    private void setTitle(String queryTitle) {
        String title = String.format(mActivity.getString(R.string.title_Q), stringFromHtml(queryTitle).toString());
        tvQTitle.setText(title);
    }

    private void setBody(String body) {
        tvQBody.setText(stringFromHtml(body).toString());
    }

    private void setVotesCount(String votesCount) {
        tvVotesCount.setText(votesCount);
    }

    private void setTags(ArrayList<String> tags) {
        StringBuilder tag = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            tag.append("  ").append(tags.get(i));
        }
        String tagStr = tag.toString();
        if (!tagStr.isEmpty()) tagStr = mActivity.getString(R.string.tags)+ " "+ tagStr;
        tvTags.setText(tagStr);
    }

    private void setDateUser(long creationDate, String displayNameUSer) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(creationDate);
        String askedUser = String.format(mActivity.getString(R.string.asked_by_user),
                sdf.format(calendar.getTime()), displayNameUSer);
        tvDateUser.setText(askedUser);
    }

    @SuppressWarnings("deprecation")
    private Spanned stringFromHtml(String text) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(text,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(text);
        }

        return result;
    }
}
