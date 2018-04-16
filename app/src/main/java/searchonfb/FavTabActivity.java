package com.example.fbsearch.searchonfb;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import static android.content.ContentValues.TAG;
import static com.example.fbsearch.searchonfb.R.attr.icon;

public class FavTabActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        tabLayout.getTabAt(0).setIcon(R.drawable.user);
        tabLayout.getTabAt(0).setText("Users");
        tabLayout.getTabAt(1).setIcon(R.drawable.pages);
        tabLayout.getTabAt(1).setText("Pages");

        tabLayout.getTabAt(2).setIcon(R.drawable.events);
        tabLayout.getTabAt(2).setText("Events");

        tabLayout.getTabAt(3).setIcon(R.drawable.places);
        tabLayout.getTabAt(3).setText("Places");

        tabLayout.getTabAt(4).setIcon(R.drawable.groups);
        tabLayout.getTabAt(4).setText("Groups");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(TAG, "The eeeeeeeeeeeeeeeee is: " +position);
                if(position==4) {
//                    FavGroupsTab.favadap.notifyDataSetChanged();
                }
                if(position==3) {
//                    new getJson("place").execute("http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=place&keyword="+HomeActivity.keyword);
                }
                if(position==2) {
//                    new getJson("event").execute("http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=event&keyword="+HomeActivity.keyword);
                }
                if(position==1) {
//                    new getJson("page").execute("http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=page&keyword="+HomeActivity.keyword);
                }
                if(position==0) {
//                    new getJson("user").execute("http://ec2-34-208-27-227.us-west-2.compute.amazonaws.com/process.php?type=user&keyword="+HomeActivity.keyword);


                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

//    public void starHandler(View view) {
//        String star_state = String.valueOf(view.getTag());
//        if (star_state == "favOff") {
//            view.setTag("favOn");
//            ImageView imageView = (ImageView)view.findViewById(R.id.star);
//            imageView.setImageResource(R.drawable.fav_on);
////            UsersTab.row_data
//        }
//    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FavUsersTab userT = new FavUsersTab();
                    return userT;
                case 1:
                    FavPagesTab pagesT = new FavPagesTab();
                    return pagesT;
                case 2:
                    FavEventsTab eventsT = new FavEventsTab();
                    return eventsT;
                case 3:
                    FavPlacesTab placesT = new FavPlacesTab();
                    return placesT;
                case 4:
                    FavGroupsTab groupsT = new FavGroupsTab();
                    return groupsT;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "USER";
//                case 1:
//                    return "PAGES";
//                case 2:
//                    return "EVENTS";
//                case 3:
//                    return "PLACES";
//                case 4:
//                    return "GROUPS";
//            }
//            return null;
//        }
    }
}
