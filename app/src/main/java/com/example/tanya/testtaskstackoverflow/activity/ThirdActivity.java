package com.example.tanya.testtaskstackoverflow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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

import com.example.tanya.testtaskstackoverflow.R;
import com.example.tanya.testtaskstackoverflow.adapters.BookmarksListAdapter;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;
import com.example.tanya.testtaskstackoverflow.sqlite.AnswerAdapter;

import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 */

public class ThirdActivity extends AppCompatActivity {

    private ArrayList<AnswerStackOverflow> mAnswers;
    private BookmarksListAdapter bookmarksListAdapter;
    private  TextInputEditText textInputEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        bookmarksListAdapter = new BookmarksListAdapter(this);

        mAnswers = new ArrayList<>();
        AnswerAdapter answerAdapter = new AnswerAdapter();
        mAnswers = answerAdapter.getAnswers();
        bookmarksListAdapter.updateList(mAnswers);

        RecyclerView searchList = (RecyclerView) findViewById(R.id.rvSearchList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        searchList.setLayoutManager(mLayoutManager);
        searchList.setAdapter(bookmarksListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        searchList.addItemDecoration(dividerItemDecoration);

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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

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
}
