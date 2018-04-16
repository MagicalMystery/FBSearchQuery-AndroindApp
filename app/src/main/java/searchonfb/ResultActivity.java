package com.example.fbsearch.searchonfb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ResultActivity extends AppCompatActivity {

    private static String response;
    ListView datalist;
    List<rowStructure> row_data = new ArrayList<rowStructure>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        JSONArray jsonArray = null;
        Log.d(TAG, "The response is: " + response);


        JSONObject jObject = null;
        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jArray = null;
        try {
            jArray = jObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int dataLength=jArray.length();
        Log.d(TAG, "The response is: " +response);

        for (int i=0; i < jArray.length(); i++)
        {

            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                String name = oneObject.getString("name");
                String dpimg = oneObject.getJSONObject("picture").getJSONObject("data").getString("url");
                String starState = "On";
                String details = "On";
                row_data.add(new rowStructure(i,"event",dpimg, name,starState,details));
                Log.d(TAG, "The response is: " +dpimg);

            } catch (JSONException e) {
                // Oops
            }

        }




            rowAdapter adapter = new rowAdapter(this,
            R.layout.listrow, row_data);


        datalist = (ListView)findViewById(R.id.datalist);

//        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
//        listView1.addHeaderView(header);
//
        datalist.setAdapter(adapter);


    }

    public static void returnResult(String result) {

        response = result;
        Log.d(TAG, "The response is: " +result);


    }
}
