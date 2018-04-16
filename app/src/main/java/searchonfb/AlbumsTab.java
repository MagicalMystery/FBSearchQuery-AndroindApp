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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AlbumsTab extends Fragment {

    private static String response;
    static ListView datalist;
    static View rootView;
    static List<rowStructure> row_data = new ArrayList<rowStructure>();
    static Context c;
    static String prev_link;
    static String next_link;
    static Button nxt_butt;
    static ExpandableListView album_list;

    static String detailsID;
    static String detailsPic;
    static Map<String, Object> favUid_row = new HashMap<String, Object>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.detailsalbumtab, container, false);


        c = getContext();

        datalist = (ListView) rootView.findViewById(R.id.albumsexpand);

        datalist.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
//                        String selectedSweet = datalist.getItemAtPosition(position).toString();
//
//
//                            String star_state = String.valueOf(view.getTag());
//                            if (star_state == "favOff") {
//                                view.setTag("favOn");
//                                ImageView imageView = (ImageView)view.findViewById(R.id.star);
//                                imageView.setImageResource(R.drawable.fav_on);
//                            }
//                        if(position==2){
//                            ImageView imageView = view.getChildAt(2);
//                            imageView.setImageResource(R.drawable.fav_off);
//                        }

//                        RelativeLayout parentRow = (RelativeLayout) view.getParent();
//                        ImageView star = (ImageView)parentRow.getChildAt(2);
//                        String star_state = String.valueOf(star.getTag());
//                        Log.d(TAG, "Selected item: " + star_state + " - " + position);

//                        TextView child = (TextView)parentRow.getChildAt(0);
//                        Button btnChild = (Button)parentRow.getChildAt(1);
//                        btnChild.setText(child.getText());
//                        btnChild.setText("I've been clicked!");

//                        int c = Color.CYAN;

//                        vwParentRow.setBackgroundColor(c);
//                        vwParentRow.refreshDrawableState();

                    }
                }
        );


        return rootView;

    }



    public static void returnResult(String result, int revisit) throws UnsupportedEncodingException {

//        if(revisit==0) {
        response = result;
//        }
        Log.d(TAG, "The response is: " + result);

        JSONObject jObject = null;
        JSONArray jArray = null;




            try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
                PostsTab.getPosts(response);
                TextView albumError = (TextView) rootView.findViewById(R.id.album_error);
                albumError.setVisibility(View.VISIBLE);
                return;
        }
        if (jObject.has("albums")) {



        try {
            jArray = (JSONArray) jObject.getJSONObject("albums").get("data");

            detailsID = jObject.getString("name").toString();
            detailsPic = jObject.getJSONObject("picture").getJSONObject("data").get("url").toString();

            Log.d("ID is ", detailsID);
            Log.d("pic is ", detailsPic);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        int dataLength = jArray.length();
        Log.d(TAG, "The response is: " + jArray);


        List<String> headingTitles = new ArrayList<String>();
        HashMap<String, List<String>> headingTitle_imgurls = new HashMap<String, List<String>>();
        for (int i = 0; i < jArray.length(); i++) {
            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                String name = oneObject.getString("name");

                List<String> img_urls = new ArrayList<String>();
                JSONArray img_data_array = (JSONArray) oneObject.getJSONObject("photos").get("data");
                Log.d(TAG, "The response is: " + name);
                headingTitles.add(name);
                for (int j = 0; j < img_data_array.length(); j++) {
                    JSONObject img_data = img_data_array.getJSONObject(j);
                    String img_url = img_data.getString("id");
                    img_url = img_data.getString("picture");
                    // img_url = "http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=image&photo_id=" + img_url;
                    Log.d(TAG, "The response is: " + img_url);
                img_urls.add(img_url);
            }
                headingTitle_imgurls.put(name, img_urls);


            } catch (JSONException e) {
                // Oops
            }

        }
        for (String key : headingTitle_imgurls.keySet()) {
            Log.d(TAG, "The response is: " + key + " " + headingTitle_imgurls.get(key));
        }
        AlbumAdapter Aadp = new AlbumAdapter(c, headingTitles, headingTitle_imgurls);

        album_list = (ExpandableListView) rootView.findViewById(R.id.albumsexpand);
        album_list.setAdapter(Aadp);
        PostsTab.getPosts(response);
        }
        else
        {
            PostsTab.getPosts(response);
            TextView albumError = (TextView) rootView.findViewById(R.id.album_error);
            albumError.setVisibility(View.VISIBLE);

//            List<String> one_string = new ArrayList<String>();
//            one_string.add("");
//            noDataAdapter noData=new noDataAdapter(c, R.layout.listrow, one_string);
//            album_list = (ExpandableListView) rootView.findViewById(R.id.albumsexpand);
//            album_list.setAdapter(noData);
//
////            noDataAdapter noData = new noDataAdapter(c, R.layout.postsrow, one_string);
////            datalist = (ListView) rootView.findViewById(R.id.postslist);
////            datalist.setAdapter(postsAdap);

        }

    }
}
