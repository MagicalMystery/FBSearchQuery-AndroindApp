package com.example.fbsearch.searchonfb;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.UnsupportedEncodingException;

import static android.content.ContentValues.TAG;

public class DetailsTabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    static rowStructure curUserData;
    static int favtab_entry_flag;



    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    CallbackManager cbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tab);

        cbManager = CallbackManager.Factory.create();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        tabLayout.getTabAt(0).setIcon(R.drawable.albums);
        tabLayout.getTabAt(0).setText("Albums");

        tabLayout.getTabAt(1).setIcon(R.drawable.post);
        tabLayout.getTabAt(1).setText("Posts");



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
//                if(position==0) {
//                    new getJson("detail").execute("http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=user&keyword=usc");
//                }
//                if(position==1) {
//
//                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }



        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details_tab, menu);
        MenuItem item=menu.getItem(0);

        if(favtab_entry_flag==1) {
            item.setEnabled(false);
        }
        else
        {
            item.setEnabled(true);
        }


        if(curUserData.starState=="fav_on") {
            item.setTitle("Remove from Favourites");

        }else {
            item.setTitle("Add to Favourites");

        }
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }else
            if(id == R.id.action_fb_share){
            FacebookShare();
        }else if(id == R.id.add_fav){

            String type=curUserData.type;
            String prev_star_state=curUserData.starState;
            String uid=curUserData.details_id;
            int position=curUserData.position;
            Log.d(curUserData.title, prev_star_state);
            Log.d(curUserData.details_id, type);

            if(type=="user") {
                if (prev_star_state == "fav_off") {
                    curUserData.starState = "fav_on";
                    UsersTab.Useradapter.data.set(position,curUserData);
                    UsersTab.Useradapter.notifyDataSetChanged();
                    UsersTab.favUid_row.put(uid, curUserData);

                    Log.d("fav off", "false");
                    item.setTitle("Remove from Favourites");
                    Toast.makeText(getBaseContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                } else {
                    curUserData.starState = "fav_off";
                    UsersTab.Useradapter.data.set(position,curUserData);
                    UsersTab.Useradapter.notifyDataSetChanged();
                    Log.d("fav on", "false");
                    item.setTitle("Add to Favourites");
                    Toast.makeText(getBaseContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    UsersTab.favUid_row.remove(uid);
                }
            }

            if(type=="place") {
                if (prev_star_state == "fav_off") {
                    curUserData.starState = "fav_on";
                    PlacesTab.Useradapter.data.set(position,curUserData);
                    PlacesTab.Useradapter.notifyDataSetChanged();
                    PlacesTab.favUid_row.put(uid, curUserData);
                    Log.d("fav off", "false");
                    item.setTitle("Remove from Favourites");
                    Toast.makeText(getBaseContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();

                } else {
                    curUserData.starState = "fav_off";
                    PlacesTab.Useradapter.data.set(position,curUserData);
                    PlacesTab.Useradapter.notifyDataSetChanged();
                    Log.d("fav on", "false");
                    item.setTitle("Add to Favourites");
                    PlacesTab.favUid_row.remove(uid);
                    Toast.makeText(getBaseContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
                }
            }

            if(type=="event") {
                if (prev_star_state == "fav_off") {
                    curUserData.starState = "fav_on";
                    EventsTab.Useradapter.data.set(position,curUserData);
                    EventsTab.Useradapter.notifyDataSetChanged();
                    EventsTab.favUid_row.put(uid, curUserData);
                    Log.d("fav off", "false");
                    item.setTitle("Remove from Favourites");
                    Toast.makeText(getBaseContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                } else {
                    curUserData.starState = "fav_off";
                    EventsTab.Useradapter.data.set(position,curUserData);
                    EventsTab.Useradapter.notifyDataSetChanged();
                    Log.d("fav on", "false");
                    item.setTitle("Add to Favourites");
                    EventsTab.favUid_row.remove(uid);
                    Toast.makeText(getBaseContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
                }
            }

            if(type=="group") {
                if (prev_star_state == "fav_off") {
                    curUserData.starState = "fav_on";
                    GroupsTab.Useradapter.data.set(position,curUserData);
                    GroupsTab.Useradapter.notifyDataSetChanged();
                    GroupsTab.favUid_row.put(uid, curUserData);
                    Log.d("fav off", "false");
                    item.setTitle("Remove from Favourites");
                    Toast.makeText(getBaseContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                } else {
                    curUserData.starState = "fav_off";
                    GroupsTab.Useradapter.data.set(position,curUserData);
                    GroupsTab.Useradapter.notifyDataSetChanged();
                    Log.d("fav on", "false");
                    item.setTitle("Add to Favourites");
                    GroupsTab.favUid_row.remove(uid);
                    Toast.makeText(getBaseContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
                }
            }


                if(type=="page") {
                    if (prev_star_state == "fav_off") {
                        curUserData.starState = "fav_on";
                        PagesTab.Useradapter.data.set(position,curUserData);
                        PagesTab.Useradapter.notifyDataSetChanged();
                        PagesTab.favUid_row.put(uid, curUserData);
                        Log.d("fav off", "false");
                        item.setTitle("Remove from Favourites");
                        Toast.makeText(getBaseContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                    } else {
                        curUserData.starState = "fav_off";
                        PagesTab.Useradapter.data.set(position,curUserData);
                        PagesTab.Useradapter.notifyDataSetChanged();
                        Log.d("fav on", "false");
                        item.setTitle("Add to Favourites");
                        PagesTab.favUid_row.remove(uid);
                        Toast.makeText(getBaseContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    }
            }
        }



        return super.onOptionsItemSelected(item);
    }





    public void FacebookShare(){

        ShareLinkContent shc = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://www-scf.usc.edu/~aswathap/hw8/index.html#/"))
                .setImageUrl(Uri.parse(AlbumsTab.detailsPic))
                .setContentDescription(AlbumsTab.detailsID)
                .setQuote("My Code")
                .build();


        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(cbManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                //Display success toast message here
                Toast.makeText(DetailsTabActivity.this, "Post Successfull!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                //Display appropriate message here
                Toast.makeText(DetailsTabActivity.this, "Post Cancelled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                //Display appropriate message here.
                Toast.makeText(DetailsTabActivity.this, "Error while posting!", Toast.LENGTH_SHORT).show();
            }
        });



        if(shareDialog.canShow(shc.getClass())){
            shareDialog.show(shc, ShareDialog.Mode.AUTOMATIC);
        }else{
            Log.d("No show", "false");
        }


        Log.d("Share","share");
    }





    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    AlbumsTab AlbumsT = new AlbumsTab();
                    return AlbumsT;
                case 1:
                    PostsTab PostsT = new PostsTab();
                    return PostsT;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "Albums";
//                case 1:
//                    return "Posts";
//            }
//            return null;
//        }
    }
}
