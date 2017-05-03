package com.deng.recipes.repositry;

import android.os.AsyncTask;

import com.deng.recipes.entity.RecipeEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.deng.recipes.ui.adapter.RecipeItemAdapter;

/**
 * Created by hcdeng on 2017/5/2.
 */

public class LoadFeedData extends AsyncTask<Void, Integer, List<RecipeEntity>> {

    private final String mUrl = "http://192.168.125.1:8080/recipes?page_index=1&page_size=5";

    private final RecipeItemAdapter mAdapter;

    public LoadFeedData(RecipeItemAdapter adapter) {
        mAdapter = adapter;
    }

    private InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url);

        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpGet);
            HttpEntity getResponseEntity = httpResponse.getEntity();
            return getResponseEntity.getContent();
        } catch (IOException e) {
            httpGet.abort();
        }
        return null;
    }

    @Override
    protected List<RecipeEntity> doInBackground(Void... params) {
        InputStream source = retrieveStream(mUrl);
        Reader reader = null;
        try {
            reader = new InputStreamReader(source);
        } catch (Exception e) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(reader,
                new TypeToken<List<RecipeEntity>>() {}.getType());
    }

    protected void onPostExecute(List<RecipeEntity> entries) {
        mAdapter.updateItems(entries, true);
    }
}