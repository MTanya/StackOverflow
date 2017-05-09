package com.example.tanya.testtaskstackoverflow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.adapters.BookmarksListAdapter;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;
import com.example.tanya.testtaskstackoverflow.sqlite.AnswerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 * 3 экран список закладок
 */

public class ThirdActivity extends AppCompatActivity {

    private ArrayList<AnswerStackOverflow> mAnswers;
    private BookmarksListAdapter bookmarksListAdapter;

    private TextInputEditText textInputEditText;

    private Handler mH;
    private Handler mHUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initFields();
        initViews();
        initHandlers();

        getAnswersFromDb();
    }

    private void initFields() {
        bookmarksListAdapter = new BookmarksListAdapter(this);
        mAnswers = new ArrayList<>();
    }

    private void initViews() {
        initToolbar();
        initBookmarksList();

        textInputEditText = (TextInputEditText) findViewById(R.id.tilSearchText);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0) {
                    search(str);
                } else {
                    clearSearch();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ImageButton btnClear = (ImageButton) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSearch();
                textInputEditText.clearFocus();
                hideKeyBoard(v);
            }
        });
    }

    private void hideKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initBookmarksList() {
        RecyclerView searchList = (RecyclerView) findViewById(R.id.rvSearchList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        searchList.setLayoutManager(mLayoutManager);
        searchList.setAdapter(bookmarksListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        searchList.addItemDecoration(dividerItemDecoration);
    }

    private void initHandlers() {
        mH = new MyHandler(this, false);
        mHUpdate = new MyHandler(this, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAnswer(AnswerStackOverflow answer) {
        if (answer != null) {
            Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
            intent.putExtra("Answer", answer);
            startActivity(intent);
        }
    }

    private void search(String text) {
        bookmarksListAdapter.search(text);
    }

    private void clearSearch() {
        textInputEditText.getText().clear();
        bookmarksListAdapter.updateList(mAnswers);
    }

    public void deleteFromBookmarks(final AnswerStackOverflow answer) {
        if (answer != null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    AnswerAdapter answerAdapter = new AnswerAdapter();
                    int ans = answerAdapter.deleteAnswer(answer);
                    mAnswers = answerAdapter.getAnswers();
                    mH.sendEmptyMessage(ans);
                }
            });

            thread.start();
        }
    }

    public void updateList() {
        bookmarksListAdapter.updateList(mAnswers);
    }

    private void getAnswersFromDb() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AnswerAdapter answerAdapter = new AnswerAdapter();
                mAnswers = answerAdapter.getAnswers();
                if (mHUpdate != null) mHUpdate.sendEmptyMessage(0);
            }
        });

        thread.start();
    }

    public ArrayList<AnswerStackOverflow> getAnswers() {
        return mAnswers;
    }

    private void showMsg(Message msg) {
        if (msg.what > 0) {
            Toast.makeText(this,getString(R.string.remove_bm),Toast.LENGTH_SHORT).show();
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<ThirdActivity> wrActivity;
        private boolean mIsUpdate;
        MyHandler(ThirdActivity activity, boolean isUpdate) {
            wrActivity = new WeakReference<>(activity);
            mIsUpdate = isUpdate;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ThirdActivity activity = wrActivity.get();
            if (activity != null) {
                if (mIsUpdate) {
                    activity.updateList();
                } else {
                    activity.showMsg(msg);
                    activity.updateList();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        if (mH != null) {
            mH.removeCallbacksAndMessages(null);
        }
        if (mHUpdate != null) {
            mHUpdate.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
