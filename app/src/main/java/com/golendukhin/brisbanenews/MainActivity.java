package com.golendukhin.brisbanenews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<New>> {
    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?order-by=newest&" +
                    "show-tags=contributor&from-date=2018-01-01&use-date=published&page-size=30&" +
                    "q=Brisbane&api-key=3a64c26a-9ce1-402a-8472-7d0a7d749b3c";
    private static final int NEW_LOADER_ID = 0;

    private TextView emptyStateTextView;
    private View loadingIndicator;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        ListView listView = findViewById(R.id.list_view);
        listViewAdapter = new ListViewAdapter(this, new ArrayList<New>());
        listView.setAdapter(listViewAdapter);

        loadingIndicator = findViewById(R.id.loading_indicator);
        emptyStateTextView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyStateTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                New mNew = listViewAdapter.getItem(position);
                String url = mNew.getApiUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEW_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<New>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<New>> loader, List<New> news) {
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        listViewAdapter.clear();
        if (news != null && !news.isEmpty()) {
            listViewAdapter.addAll(news);
        } else {
            emptyStateTextView.setText(R.string.no_news_found);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<New>> loader) {
        // Loader reset, so we can clear out our existing data.
        listViewAdapter.clear();
    }
}