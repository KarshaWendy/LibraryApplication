package com.example.whowroteit;


import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask<String,Void,String> {

//variables for the search input field and the results TextViews

    EditText mBookInput;
    TextView mTitleText;
    TextView mAuthorText;

    //class name for log tag

    private static final String LOG_TAG =FetchBook.class.getSimpleName();

    //constructor providing a refrence to the view in MainActivity

    public FetchBook(TextView titleText, TextView authorText, EditText bookInput){
        this.mTitleText = titleText;
        this.mAuthorText = authorText;
        this.mBookInput = bookInput;

    }

    @Override
    protected String doInBackground(String...params){
        return NetworkUtils.getBookInfo(params[0]);

    }
    @Override
    protected void  onPostExecute(String s){
        super.onPostExecute(s);

        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject book  = itemsArray.getJSONObject(i);
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }
                if (title != null && authors != null){
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);
                    return;
                }
            }
            mTitleText.setText("No Results Found");
            mAuthorText.setText("");
        } catch (Exception e){
            mTitleText.setText("No Results Found");
            mAuthorText.setText("");
            e.printStackTrace();
        }
            }
        }
