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

public class postsRowAdapter extends ArrayAdapter<postsRowStructure> {

    Context context;
    int layoutResourceId;
    List<postsRowStructure> data = null;

    public postsRowAdapter(Context context, int layoutResourceId, List<postsRowStructure> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        rowDataHolder holder = null;
        final postsRowStructure rowData = data.get(position);


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new rowDataHolder();
            holder.img = (ImageView) row.findViewById(R.id.thumbImage);
            holder.txtTitle = (TextView) row.findViewById(R.id.title);
            holder.time = (TextView) row.findViewById(R.id.time);
            holder.message = (TextView) row.findViewById(R.id.message);

            row.setTag(holder);

            Picasso.with(context)
                    .load(rowData.imgurl)
                    .resize(50, 50) // here you resize your image to whatever width and height you like
                    .into(holder.img);

            holder.txtTitle.setText(rowData.title);
            holder.time.setText(rowData.time);
            holder.message.setText(rowData.message);
        }
            return row;
    }

    class rowDataHolder
    {
        ImageView img;
        TextView txtTitle;
        TextView time;
        TextView message;
    }
}