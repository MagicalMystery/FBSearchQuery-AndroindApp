package com.example.fbsearch.searchonfb;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.fbsearch.searchonfb.R.id.datalist;
import static com.example.fbsearch.searchonfb.R.id.parent;

public class GroupsTab extends Fragment {

    private static String response;
    static ListView datalist;
    static View rootView;
    static List<rowStructure> row_data = new ArrayList<rowStructure>();
    static Context c;
    static String prev_link;
    static String next_link;
    static Button nxt_butt;
    static Button prev_butt;
    static rowAdapter Useradapter;

    static Map<String, Object> favUid_row = new HashMap<String, Object>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.grouptab, container, false);


        c=getContext();
        nxt_butt= (Button) rootView.findViewById(R.id.butt_next);
        prev_butt= (Button) rootView.findViewById(R.id.butt_prev);


        datalist= (ListView) rootView.findViewById(R.id.datalist);


        nxt_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (next_link != null) {
                    try {
                        new getJson("group").execute("http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=group&pageurl="+ URLEncoder.encode(next_link, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        prev_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (prev_link != null) {
                    Log.d(TAG, "nxt link: " + prev_link);

                    try {
                        new getJson("group").execute("http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=group&pageurl=" + URLEncoder.encode(prev_link, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        return rootView;

    }



    public static void returnResult(String result,int revisit) throws UnsupportedEncodingException {

        if(revisit==0) {
            response = result;
        }

        JSONArray jArray = null;

//        Log.d(TAG, "The response is: " +response);

        JSONObject jObject = null;
        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jArray = jObject.getJSONArray("data");

            next_link = jObject.getJSONObject("paging").getString("next");
            prev_link = jObject.getJSONObject("paging").getString("previous");

            Log.d(TAG, "nxt link: " + next_link);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        prev_butt.setEnabled(false);
        nxt_butt.setEnabled(false);

        if (next_link != null && !next_link.isEmpty()) {

            nxt_butt.setEnabled(true);
        }

        if (prev_link != null && !prev_link.isEmpty()) {

            prev_butt.setEnabled(true);
        }

        row_data.clear();


        int dataLength = jArray.length();
//        Log.d(TAG, "The response is: " +response);

        for (int i = 0; i < jArray.length(); i++) {

            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                String name = oneObject.getString("name");
                String id = oneObject.getString("id");

                String dpimg = oneObject.getJSONObject("picture").getJSONObject("data").getString("url");
                String starState;
                if (favUid_row.containsKey(id)){
                    starState = "fav_on";
                }
                else{
                    starState = "fav_off";
                }
                String details_id = id;
                row_data.add(new rowStructure(i,"group", dpimg, name, starState, details_id));
                Log.d(TAG, "The response is: " + name);

            } catch (JSONException e) {
                // Oops
            }

        }


        Log.d(TAG, "The response is: " + row_data);

        Useradapter = new rowAdapter(c, R.layout.listrow, row_data);


        datalist = (ListView)rootView.findViewById(R.id.datalist);
        datalist.setAdapter(Useradapter);




    }
}
