package com.example.whowroteit;

import android.content.Context;
import android.os.AsyncTask;

import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    //variable that stores the search string.

    private String mQueryString;

    //constructor providing a refrence to the search term

    public BookLoader(Context context, String queryString){
        super(context);
        mQueryString = queryString;
    }
    // method is invoked by the loaderManager whenever the loader is started.

    @Override
    protected void onStartLoading(){
        forceLoad(); //Strats the loadInBackground method

    }
    // connects to the network and makes the book API request on a background thread.
    // it reterns the raw JSON response from the API Call.

    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);

    }
}
