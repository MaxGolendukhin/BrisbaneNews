package com.golendukhin.brisbanenews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BrisbaneNew>> {
    private static final String GUARDIAN_REQUEST_UR1 =
            "http://content.guardianapis.com/search?order-by=newest&" +
                    "show-tags=contributor&from-date=2018-01-01&use-date=published&page-size=30&" +
                    "q=Brisbane&api-key=test";

    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?q=Brisbane&show-tags=contributor&use-date=published&page-size=30&";

    private static final int NEW_LOADER_ID = 0;

    private TextView emptyStateTextView;
    private View loadingIndicator;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        ListView newsListView = findViewById(R.id.news_list_view);
        listViewAdapter = new ListViewAdapter(this, new ArrayList<BrisbaneNew>());
        newsListView.setAdapter(listViewAdapter);

        loadingIndicator = findViewById(R.id.loading_indicator);
        emptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyStateTextView);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BrisbaneNew mNew = listViewAdapter.getItem(position);
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

    /**
     * LoaderManager interface method
     *
     * @return new loader to fetch data
     */
    @Override
    public Loader<List<BrisbaneNew>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("from-date", "2018-01-01");
        uriBuilder.appendQueryParameter("api-key", "test");

        return new NewsLoader(this, uriBuilder.toString());
    }

    /**
     * LoaderManager interface method
     * Triggered when loader has finished its job.
     * Load indicator is to be no more visible from this moment
     */
    @Override
    public void onLoadFinished(Loader<List<BrisbaneNew>> loader, List<BrisbaneNew> brisbaneNews) {
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        listViewAdapter.clear();
        if (brisbaneNews != null && !brisbaneNews.isEmpty()) {
            listViewAdapter.addAll(brisbaneNews);
        } else {
            emptyStateTextView.setText(R.string.no_news_found);
        }
    }

    /**
     * LoaderManager interface method
     * Triggered when need to reset loader
     */
    @Override
    public void onLoaderReset(Loader<List<BrisbaneNew>> loader) {
        // Loader reset, so we can clear out our existing data.
        listViewAdapter.clear();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}