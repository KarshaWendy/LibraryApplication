package com.example.whowroteit;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    //base URL for the books API
    //where you make the HTTP request
    private static final String QUERY_PARAM = "q"; // Parameter for the search string
    private static final String MAX_RESULTS = "maxResults"; // Parameter that limits search results
    private static final String PRINT_TYPE = "printType"; // Parameter to filter by print type


    static String getBookInfo(String queryString) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;



//the limit for the number and types of results returned to increase query speed

        try {
            //Code that builds the URI and issues the query
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();


            URL requestURL = new URL(builtURI.toString());

            //open connection

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Get the InputStream.

            InputStream inputStream = urlConnection.getInputStream();

            //read the response String into the stringBuilder

            StringBuilder builder = new StringBuilder();

            if (inputStream == null) {
// Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
/* Since it's JSON, adding a newline isn't necessary (it won't affect
parsing) but it does make debugging a *lot* easier if you print out the
completed buffer for debugging. */
                builder.append(line + "\n");
            }
            if (builder.length() == 0) {
// Stream was empty. No point in parsing.
                return null;
            }
            bookJSONString = builder.toString();

//catch errors
        } catch (IOException e) {
            e.printStackTrace();
            return null;
            //handles any problem in making the HTTP request

            //close the connection
        } finally {
            //close the network connection
            //after receiving the JSON data and returning the result.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
        //return the raw response
        return bookJSONString;
    }
}




