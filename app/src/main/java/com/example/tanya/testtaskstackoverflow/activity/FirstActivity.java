package com.example.tanya.testtaskstackoverflow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.adapters.SearchListAdapter;
import com.example.tanya.testtaskstackoverflow.models.AnswerByApi;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;
import com.example.tanya.testtaskstackoverflow.networking.GetAnswers;
import com.example.tanya.testtaskstackoverflow.sqlite.AnswerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 */

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = FirstActivity.class.getSimpleName();

    private AsyncTask mQuerySearch;
    private SearchListAdapter mAdapter;
    public ProgressBar mProgressBar;
    public int mPage = 1;
    private FirstActivity mActivity;
    private TextInputEditText searchText;
    public LinearLayoutManager mLayoutManager;
    private TextView tvCountBookmarks;
    private Handler mH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActivity = this;
        searchText = (TextInputEditText) findViewById(R.id.tilSearchText);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        RecyclerView searchList = (RecyclerView) findViewById(R.id.rvSearchList);
        mLayoutManager = new LinearLayoutManager(mActivity);
        searchList.setLayoutManager(mLayoutManager);
        mAdapter = new SearchListAdapter(mActivity);
        searchList.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mActivity, mLayoutManager.getOrientation());
        searchList.addItemDecoration(dividerItemDecoration);

        ImageButton imageButton = (ImageButton) findViewById(R.id.btnSearch);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPage = 1;
                getData(mProgressBar,null);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        LinearLayout bookmarks = (LinearLayout) findViewById(R.id.bookmarks);
        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBookmarks();
            }
        });

        tvCountBookmarks = (TextView) findViewById(R.id.countBookmarks);

        mH = new MyHandler(mActivity);

    }

    public void updateList(AnswerByApi answerByApi) {
        ArrayList<AnswerStackOverflow> answerStackOverflows = answerByApi.getQueries();
        mAdapter.updateList(answerStackOverflows, answerByApi.isHasMore());
    }

    public void getData(ProgressBar progressBar, Button moreButton) {
        mQuerySearch = new GetAnswers(mActivity,progressBar,moreButton).execute(searchText.getText().toString());
    }

    public void goToAnswer(AnswerStackOverflow answer) {
        if (answer != null) {
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            intent.putExtra("Answer", answer);
            startActivity(intent);
        }
    }

    private void goToBookmarks() {
        Intent intent = new Intent(FirstActivity.this, ThirdActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mQuerySearch != null && !mQuerySearch.isCancelled()) {
            mQuerySearch.cancel(false);
            mQuerySearch = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCountAnswers();
    }

    private void updateCounter(int cc) {
        tvCountBookmarks.setText("(" + cc +")");
    }

    private void getCountAnswers() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AnswerAdapter answerAdapter = new AnswerAdapter();
                int cc = answerAdapter.getCountAnswers();
                mH.sendEmptyMessage(cc);
            }
        });

        thread.start();
    }

    @Override
    protected void onDestroy() {
        if (mQuerySearch != null && !mQuerySearch.isCancelled()) {
            mQuerySearch.cancel(false);
            mQuerySearch = null;
        }
        if (mH != null) {
            mH.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private static class MyHandler extends Handler {

        private WeakReference<FirstActivity> wrActivity;

        MyHandler(FirstActivity activity) {
            wrActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FirstActivity firstActivity = wrActivity.get();
            if (firstActivity != null) {
                firstActivity.updateCounter(msg.what);
            }
        }
    }
}
