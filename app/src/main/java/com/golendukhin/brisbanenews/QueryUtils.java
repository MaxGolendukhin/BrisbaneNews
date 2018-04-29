package com.golendukhin.brisbanenews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String response = "response";
    private static final String results = "results";
    private static final String date = "webPublicationDate";
    private static final String dateSplitter = "T";
    private static final String title = "webTitle";
    private static final String url = "webUrl";
    private static final String tags = "tags";
    private static final String name = "sectionName";

    private QueryUtils() {
        throw new AssertionError(R.string.util_exception_instantiation);
    }

    /**
     * Main method of QueryUtils class
     * Creates url
     * Makes http request in order to get JSON response
     * Extracts list from previously fetched JSON response
     *
     * @param requestUrl defined url string to fetch from Guardian
     * @return List of news to populate adapter
     */
    public static List<BrisbaneNew> fetchBrisbaneNews(String requestUrl) {
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Parses JSON into string to create new object
     *
     * @param newsJSON being parsed
     * @return List<BrisbaneNew> containing all information to populate adapter
     */
    private static List<BrisbaneNew> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<BrisbaneNew> brisbaneNews = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject(response);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of features (or brisbaneNews).
            JSONArray newsArray = responseJSONObject.getJSONArray(results);

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNew = newsArray.getJSONObject(i);

                String webPublicationDate = currentNew.getString(date);
                webPublicationDate = webPublicationDate.split(dateSplitter)[0];

                String webTitle = currentNew.getString(title);
                String apiUrl = currentNew.getString(url);
                String sectionName = currentNew.getString(name);

                JSONArray tagArray = currentNew.getJSONArray(tags);
                JSONObject tagJSONObject = tagArray.getJSONObject(0);
                String author = tagJSONObject.getString(title);

                brisbaneNews.add(new BrisbaneNew(webPublicationDate, webTitle, apiUrl, author, sectionName));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the brisbaneNews JSON results", e);
        }
        return brisbaneNews;
    }
}