package com.example.fbsearch.searchonfb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class noDataAdapter extends ArrayAdapter<String> {

    Context context;
    int layoutResourceId;
    List<String> data = null;

    public noDataAdapter(Context context, int layoutResourceId, List<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        rowDataHolder holder = null;
        final String rowData = data.get(position);


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new rowDataHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.title);
            holder.txtTitle.setText("No Data");
        }
        return row;
    }

    class rowDataHolder
    {
        TextView txtTitle;
    }
}