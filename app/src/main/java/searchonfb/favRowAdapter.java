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

public class favRowAdapter extends ArrayAdapter<rowStructure> {

    Context context;
    int layoutResourceId;
    List<rowStructure> data;
    String type;

    public favRowAdapter(Context context, int layoutResourceId, List<rowStructure> data) {
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
                DetailsTabActivity.favtab_entry_flag=1;
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
                        UsersTab.favUid_row.remove(uid);
                        data.remove(position);
                        notifyDataSetChanged();
                }

                if(type=="group")
                {
                       GroupsTab.favUid_row.remove(uid);
                        data.remove(position);
                        notifyDataSetChanged();
                }

                if(type=="place")
                {
                        PlacesTab.favUid_row.remove(uid);
                        data.remove(position);
                        notifyDataSetChanged();
                }

                if(type=="page")
                {
                        PagesTab.favUid_row.remove(uid);
                        data.remove(position);
                        notifyDataSetChanged();
                }

                if(type=="event")
                {
                        EventsTab.favUid_row.remove(uid);
                        data.remove(position);
                        notifyDataSetChanged();
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