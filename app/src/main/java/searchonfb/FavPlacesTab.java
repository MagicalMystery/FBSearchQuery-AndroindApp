package com.example.fbsearch.searchonfb;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavPlacesTab extends Fragment {

    private static String response;
    static ListView datalist;
    static View rootView;
    static View favView;
    static Context c;
    static favRowAdapter favadap;

    static Map<String, Object> favUid_row = new HashMap<String, Object>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fav_type, container, false);
        c=getContext();

        List<rowStructure> fav_row_data = new ArrayList<rowStructure>();
        for (Map.Entry<String, Object> entry : PlacesTab.favUid_row.entrySet()) {
//            System.out.println(entry.getKey() + "/" + entry.getValue());
//            Log.d(TAG, "The eeeeeeeeeeeeeeeee is: " +entry.getValue());
            rowStructure test=(rowStructure) entry.getValue();
//            Log.d(TAG, "The eeeeeeeeeeeeeeeee is: " +test.title+test.type+test.starState+test.details_id+test.imgurl+test.position);

            fav_row_data.add(test);
        }
//        Log.d(TAG, "The eeeeeeeeeeeeeeeee is: " +R.layout.listrow);


        favadap = new favRowAdapter(c, R.layout.listrow, fav_row_data);
        datalist = (ListView)rootView.findViewById(R.id.favdatalist);
        datalist.setAdapter(favadap);
        return rootView;
    }
}
