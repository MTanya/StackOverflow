package com.example.tanya.testtaskstackoverflow.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;
import com.example.tanya.testtaskstackoverflow.sqlite.AnswerAdapter;
import com.example.tanya.testtaskstackoverflow.sqlite.TagAdapter;

import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 */

public class SecondActivity extends AppCompatActivity {

    private AnswerStackOverflow mAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        WebView webView = (WebView) findViewById(R.id.webView);

        AnswerStackOverflow answer = (AnswerStackOverflow) getIntent().getSerializableExtra("Answer");
        if (answer != null) {
            mAnswer = answer;
            String link = answer.getLink();
            webView.loadUrl(link);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        } else if (id == R.id.bookmark) {

            if (mAnswer != null) {
                AnswerAdapter answerAdapter = new AnswerAdapter();
                int ans = answerAdapter.insert(mAnswer);
                if (ans > 0) {
                    Toast.makeText(this,"Добавлено в избранное",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this,"Уже в избранном",Toast.LENGTH_SHORT).show();
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
