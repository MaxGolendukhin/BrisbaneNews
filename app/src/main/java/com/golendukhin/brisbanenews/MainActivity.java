package com.golendukhin.brisbanenews;

import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?from-date=2018-01-01&use-date=published&page-size=30&q=Brisbane&api-key=3a64c26a-9ce1-402a-8472-7d0a7d749b3c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public Loader<List<New>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
    }
}
