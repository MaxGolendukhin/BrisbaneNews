package com.golendukhin.brisbanenews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

class NewsLoader extends AsyncTaskLoader<List<New>> {
    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<New> loadInBackground() {
        if (url == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List <New> news = QueryUtils.fetchNews(url);
        return news;
    }
}