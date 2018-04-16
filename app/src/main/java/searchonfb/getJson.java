package com.example.fbsearch.searchonfb;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;


class getJson extends AsyncTask <String, Void, String>{

    String json=new String();
    Context c;
    String activeTab;

    public getJson(String activeTab) {
        this.activeTab=activeTab;
    }

//    public getJson(Context context) {
//        this.c=context;
//    }



    //        Intent i=new Intent(c, MainTabAcivity.class);
//        c.startActivity(i);
    @Override
    protected void onPostExecute(String result) {


//        ResultActivity.returnResult(result);

        if (activeTab == "detail") {
            try {
                AlbumsTab.returnResult(result,0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (activeTab == "user") {
            try {
                UsersTab.returnResult(result,0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "User");


        }
        if (activeTab == "event") {
            try {
                EventsTab.returnResult(result,0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "event");

        }
        if (activeTab == "group") {
            try {
                GroupsTab.returnResult(result,0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        if (activeTab == "page") {
            try {
                PagesTab.returnResult(result,0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        if (activeTab == "place") {
            try {
                PlacesTab.returnResult(result,0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }






    }


    protected String doInBackground(String...
                                            args) {
        try {

            InputStream is = null;
            try {
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 );
                conn.setConnectTimeout(15000 );
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "n");
                    }
                    is.close();
                    json = sb.toString();
                    return json;
                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }
            } finally {
                if (is != null) {
                    is.close();
                }
            }

        } catch (IOException e) {
            return "Unable to retrieve data. URL may be invalid.";
        }

        return json;
    }
}
