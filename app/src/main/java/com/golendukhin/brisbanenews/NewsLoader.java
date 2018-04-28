package com.golendukhin.brisbanenews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

class NewsLoader extends AsyncTaskLoader<List<New>> {
    private String url;

    NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Loads data in async task mode
     * @return list of news parsed from JSON fetched from Guardian
     */
    @Override
    public List<New> loadInBackground() {
        if (url == null) {
            return null;
        }
        return QueryUtils.fetchNews(url);
    }
}