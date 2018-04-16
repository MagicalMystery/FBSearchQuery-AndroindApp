package com.example.fbsearch.searchonfb;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class PostsTab extends Fragment {

    private static String response;
    static ListView datalist;
    static View rootView;
    static List<postsRowStructure> row_data = new ArrayList<postsRowStructure>();
    static Context c;
    static postsRowAdapter Postsadapter;


    static Map<String, Object> favUid_row = new HashMap<String, Object>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.postsalbumtab, container, false);

        c = getContext();
        return rootView;

    }


    public static void getPosts(String result) {

        response=result;
        JSONObject jObject = null;
        JSONArray jArray = null;


        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            TextView postError = (TextView) rootView.findViewById(R.id.post_error);
            postError.setVisibility(View.VISIBLE);
            return;
        }

        if (jObject.has("posts")) {

            try {
                jArray = (JSONArray) jObject.getJSONObject("posts").get("data");
                String name = jObject.getString("name");
                String dpimg = jObject.getJSONObject("picture").getJSONObject("data").getString("url");


                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    String message = oneObject.getString("message");
                    String created_time = oneObject.getString("created_time");
                    created_time = created_time.replaceAll("[a-zA-Z]+", " ");
                    created_time = created_time.replaceAll("\\+.*", "");
                    row_data.add(new postsRowStructure(dpimg, name, created_time, message));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            postsRowAdapter postsAdap = new postsRowAdapter(c, R.layout.postsrow, row_data);
            datalist = (ListView) rootView.findViewById(R.id.postslist);
            datalist.setAdapter(postsAdap);
        }

        else{
            TextView postError = (TextView) rootView.findViewById(R.id.post_error);
            postError.setVisibility(View.VISIBLE);

        }
    }
}
