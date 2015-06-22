package com.trydevs.askyourfriends.askurfrnds.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.fragments.FragmentFindFriends;
import com.trydevs.askyourfriends.askurfrnds.fragments.FragmentRequest;
import com.trydevs.askyourfriends.askurfrnds.tabs.SlidingTabLayout;

public class AddFriendsActivity extends ActionBarActivity {

    private static int NO_OF_FRAGMENTS = 2;
    SharedPreferences loginDetails;
    private String unique_id, name;
    private int user_id;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        check();
        initialize();

    }

    private void check() {
        // getting Logged in data
        loginDetails = getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        String clear = "";
        user_id = loginDetails.getInt("user_id", 0);
        unique_id = loginDetails.getString("unique_id", "");
        name = loginDetails.getString("name", "");
        // Checking for discrepency
        if (user_id == 0 || unique_id.equalsIgnoreCase(clear) || name.equalsIgnoreCase(clear)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initialize() {
        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // Setting Top bar 'Tool bar'
        toolbar = (Toolbar) findViewById(R.id.top_bar_add_friends_activity);
        setSupportActionBar(toolbar);
        // Mapping the views
        viewPager = (ViewPager) findViewById(R.id.view_pager_add_friends_activity);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout_add_friends_activity);
        // Setting up the View Pager and the Tab Layout
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.tab_ripple, R.id.textView_tab_ripple);
        slidingTabLayout.setViewPager(viewPager);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tab_names;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tab_names = getResources().getStringArray(R.array.add_friends_activity_tabs);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tab_names[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new FragmentFindFriends();
                    break;
                case 1:
                    fragment = new FragmentRequest();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NO_OF_FRAGMENTS;
        }
    }

}
