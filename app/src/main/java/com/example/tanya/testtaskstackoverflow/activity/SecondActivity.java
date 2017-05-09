package com.example.tanya.testtaskstackoverflow.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;
import com.example.tanya.testtaskstackoverflow.sqlite.AnswerAdapter;

import java.lang.ref.WeakReference;


/**
 * Created by Tanya on 07.05.2017.
 * 2 экран - выбранный Ответ из списка ответов с первого экрана
 */

public class SecondActivity extends AppCompatActivity {

    private AnswerStackOverflow mAnswer;
    private SecondActivity mActivity;

    private Handler mH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initFields();
        initViews();
        initHandlers();
    }

    private void initFields() {
        mActivity = this;

        AnswerStackOverflow answer = (AnswerStackOverflow) getIntent().getSerializableExtra("Answer");
        if (answer != null) {
            mAnswer = answer;
        }
    }

    private void initViews() {
        initToolbar();

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        String link = mAnswer.getLink();
        webView.loadUrl(link);
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

    private void initHandlers() {
        mH = new MyHandler(mActivity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        } else if (id == R.id.bookmark) {
            insertAnswer();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMsg(Message msg) {
        if (msg.what > 0) {
            Toast.makeText(mActivity,getString(R.string.add_bm),Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(mActivity,getString(R.string.exist_bm),Toast.LENGTH_SHORT).show();
        }
    }

    private void insertAnswer() {
        if (mAnswer != null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    AnswerAdapter answerAdapter = new AnswerAdapter();
                    int ans = answerAdapter.insert(mAnswer);
                    mH.sendEmptyMessage(ans);
                }
            });

            thread.start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private static class MyHandler extends Handler {
        WeakReference<SecondActivity> wrActivity;

        MyHandler(SecondActivity activity) {
            wrActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SecondActivity secondActivity = wrActivity.get();
            if (secondActivity != null) {
                secondActivity.showMsg(msg);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mH != null) {
            mH.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
