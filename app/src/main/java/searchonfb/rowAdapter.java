package com.example.fbsearch.searchonfb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.fbsearch.searchonfb.R.drawable.fav_off;
import static com.example.fbsearch.searchonfb.R.drawable.fav_on;
import static com.example.fbsearch.searchonfb.R.id.imageView;

public class rowAdapter extends ArrayAdapter<rowStructure> {

    Context context;
    int layoutResourceId;
    List<rowStructure> data;
    String type;

    public rowAdapter(Context context, int layoutResourceId, List<rowStructure> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        rowDataHolder holder = null;
        final rowStructure rowData = data.get(position);



//        if(row == null)
//        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new rowDataHolder();
            holder.img = (ImageView)row.findViewById(R.id.thumbImage);
            holder.txtTitle = (TextView)row.findViewById(R.id.title);
            holder.star = (ImageView)row.findViewById(R.id.star);
            holder.star.setTag(new pos_state_holder(rowData.position,rowData.starState));
            holder.details = (ImageView)row.findViewById(R.id.details);

            row.setTag(holder);
//        }
//        else
//        {
//            holder = (rowDataHolder)row.getTag();
//        }

        Picasso.with(context)
                .load(rowData.imgurl)
//                .resize(50, 50) // here you resize your image to whatever width and height you like
                .into(holder.img);

        holder.txtTitle.setText(rowData.title);
        holder.details.setImageResource(R.drawable.details);
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getContext(), DetailsTabActivity.class);
                getContext().startActivity(i);

                DetailsTabActivity.curUserData=rowData;
                DetailsTabActivity.favtab_entry_flag=0;
                new getJson("detail").execute("http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=details&user_id="+rowData.details_id);
            }


        });





        type=rowData.type;
        final String uid=rowData.details_id;

        if(rowData.starState=="fav_off") {
            holder.star.setImageResource(R.drawable.fav_off);
        }else
        {
            holder.star.setImageResource(R.drawable.fav_on);
        }
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
final int position = mListView.getPositionForView((View) v.getParent());
Log.v(TAG, "Title clicked, row %d", position);String star_state = String.valueOf(view.getTag());
if (star_state == "favOff") {
*/

                pos_state_holder old_tag= (pos_state_holder) view.getTag();
                    int row_position=old_tag.position;
                    String prev_star_state=old_tag.state;
                    ImageView imageView = (ImageView)view.findViewById(R.id.star);

                if(type=="user")
                {
                    if(prev_star_state=="fav_off"){

                            rowStructure old_row=UsersTab.row_data.get(position);
                            old_row.starState="fav_on";
                            UsersTab.row_data.set(position,old_row);
                            UsersTab.favUid_row.put(uid,old_row);
                        view.setTag(new pos_state_holder(row_position,"fav_on"));
                        imageView.setImageResource(R.drawable.fav_on);
                    }else
                    {
                        view.setTag(new pos_state_holder(row_position,"fav_off"));
                        imageView.setImageResource(R.drawable.fav_off);
                        rowStructure old_row=UsersTab.row_data.get(position);
                        old_row.starState="fav_off";
                        UsersTab.row_data.set(position,old_row);
                        UsersTab.favUid_row.remove(uid);
                    }
                }

                if(type=="group")
                {
                    if(prev_star_state=="fav_off"){

                        rowStructure old_row=GroupsTab.row_data.get(position);
                        old_row.starState="fav_on";
                        GroupsTab.row_data.set(position,old_row);
                        GroupsTab.favUid_row.put(uid,old_row);
                        view.setTag(new pos_state_holder(row_position,"fav_on"));
                        imageView.setImageResource(R.drawable.fav_on);
//                        notifyDataSetChanged();
                    }else
                    {
                        view.setTag(new pos_state_holder(row_position,"fav_off"));
                        imageView.setImageResource(R.drawable.fav_off);
                        rowStructure old_row=GroupsTab.row_data.get(position);
                        old_row.starState="fav_off";
                        GroupsTab.row_data.set(position,old_row);
                        GroupsTab.favUid_row.remove(uid);
                    }
                }

                if(type=="place")
                {
                    if(prev_star_state=="fav_off"){

                        rowStructure old_row=PlacesTab.row_data.get(position);
                        old_row.starState="fav_on";
                        PlacesTab.row_data.set(position,old_row);
                        PlacesTab.favUid_row.put(uid,old_row);
                        view.setTag(new pos_state_holder(row_position,"fav_on"));
                        imageView.setImageResource(R.drawable.fav_on);
                    }else
                    {
                        view.setTag(new pos_state_holder(row_position,"fav_off"));
                        imageView.setImageResource(R.drawable.fav_off);
                        rowStructure old_row=PlacesTab.row_data.get(position);
                        old_row.starState="fav_off";
                        PlacesTab.row_data.set(position,old_row);
                        PlacesTab.favUid_row.remove(uid);
                    }
                }

                if(type=="page")
                {
                    if(prev_star_state=="fav_off"){

                        rowStructure old_row=PagesTab.row_data.get(position);
                        old_row.starState="fav_on";
                        PagesTab.row_data.set(position,old_row);
                        PagesTab.favUid_row.put(uid,old_row);
                        view.setTag(new pos_state_holder(row_position,"fav_on"));
                        imageView.setImageResource(R.drawable.fav_on);
                    }else
                    {
                        view.setTag(new pos_state_holder(row_position,"fav_off"));
                        imageView.setImageResource(R.drawable.fav_off);
                        rowStructure old_row=PagesTab.row_data.get(position);
                        old_row.starState="fav_off";
                        PagesTab.row_data.set(position,old_row);
                        PagesTab.favUid_row.remove(uid);
                    }
                }

                if(type=="event")
                {
                    if(prev_star_state=="fav_off"){

                        rowStructure old_row=EventsTab.row_data.get(position);
                        old_row.starState="fav_on";
                        EventsTab.row_data.set(position,old_row);
                        EventsTab.favUid_row.put(uid,old_row);
                        view.setTag(new pos_state_holder(row_position,"fav_on"));
                        imageView.setImageResource(R.drawable.fav_on);
                    }else
                    {
                        view.setTag(new pos_state_holder(row_position,"fav_off"));
                        imageView.setImageResource(R.drawable.fav_off);
                        rowStructure old_row=EventsTab.row_data.get(position);
                        old_row.starState="fav_off";
                        EventsTab.row_data.set(position,old_row);
                        EventsTab.favUid_row.remove(uid);
                    }
                }


            }
        });
//        holder.imgIcon.setImageResource(rowData.icon);

        return row;
    }

    class rowDataHolder
    {
        ImageView img;
        TextView txtTitle;
        ImageView star;
        ImageView details;
    }
}