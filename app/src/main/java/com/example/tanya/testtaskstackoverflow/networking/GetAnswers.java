package com.example.tanya.testtaskstackoverflow.networking;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.tanya.testtaskstackoverflow.activity.FirstActivity;
import com.example.tanya.testtaskstackoverflow.models.AnswerByApi;
import com.example.tanya.testtaskstackoverflow.models.AnswerStackOverflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Tanya on 07.05.2017.
 */

public class GetAnswers extends AsyncTask<String, Void, AnswerByApi> {

    private static final String TAG = GetAnswers.class.getSimpleName();
    private FirstActivity mActivity;
    private ProgressBar mProgressBar;
    private Button mMoreButton;

    public GetAnswers(FirstActivity activity, ProgressBar progressBar, Button moreButton) {
        mActivity = activity;
        mProgressBar = progressBar;
        mMoreButton = moreButton;
    }

    @Override
    protected void onPreExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected AnswerByApi doInBackground(String... params) {
        String searchText = params[0];
        String urlStr = "https://api.stackexchange.com/2.2/answers?order=desc&sort=votes&q="
                +searchText+"&site=stackoverflow&page="+mActivity.mPage+"&pagesize=30&filter=!b0OfNb36brYWw1";

        String responseStr = "";

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Log.d(TAG, "doInBackground: " + url);

            int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            responseStr = response.toString();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnswerByApi answerByApi = new AnswerByApi();

        try {
            JSONObject jsonArray = new JSONObject(responseStr);
            ArrayList<AnswerStackOverflow> answerStackOverflows = new ArrayList<>();

            try {
                JSONArray items = jsonArray.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject jsonObject = items.getJSONObject(i);
                    AnswerStackOverflow answerStackOverflow = new AnswerStackOverflow();
                    answerStackOverflow.setAnswerId(jsonObject.getLong("answer_id"));
                    answerStackOverflow.setBody(jsonObject.getString("body"));
                    answerStackOverflow.setTitle(jsonObject.getString("title"));
                    answerStackOverflow.setQuestionId(jsonObject.getLong("question_id"));
                    answerStackOverflow.setCreationDate(jsonObject.getLong("creation_date"));
                    answerStackOverflow.setScore(jsonObject.getInt("score"));
                    answerStackOverflow.setLink(jsonObject.getString("link"));

                    try{
                        JSONObject ownerObj = jsonObject.getJSONObject("owner");
                        answerStackOverflow.setDisplayNameUser(ownerObj.getString("display_name"));
                    } catch (JSONException js1) {
                        js1.printStackTrace();
                    }

                    ArrayList<String> tags = new ArrayList<>();
                    try{
                        JSONArray tagsobj = jsonObject.getJSONArray("tags");
                        for (int j = 0; j < tagsobj.length(); j++) {
                            String tag = tagsobj.get(j).toString();
                            if (!tags.contains(tag)) tags.add(tag);
                        }
                    } catch (JSONException js2) {
                        js2.printStackTrace();
                    }
                    answerStackOverflow.setTags(tags);
                    answerStackOverflows.add(answerStackOverflow);
                }

            } catch (JSONException je) {
                je.printStackTrace();
            }

            answerByApi.setHasMore(jsonArray.getBoolean("has_more"));
            answerByApi.setQueries(answerStackOverflows);
            answerByApi.setQuotaMax(jsonArray.getInt("quota_max"));
            answerByApi.setQuotaRemaining(jsonArray.getInt("quota_remaining"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return answerByApi;
    }

    @Override
    protected void onPostExecute(AnswerByApi answerByApi) {
        mProgressBar.setVisibility(View.GONE);
        if (mMoreButton != null) mMoreButton.setVisibility(View.VISIBLE);
        mActivity.updateList(answerByApi);

    }
}
