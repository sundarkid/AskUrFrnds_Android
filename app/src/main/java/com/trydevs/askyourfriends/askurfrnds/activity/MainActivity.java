package com.trydevs.askyourfriends.askurfrnds.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.fragments.FragmentUsersList;
import com.trydevs.askyourfriends.askurfrnds.fragments.FragmentViewQuizList;
import com.trydevs.askyourfriends.askurfrnds.services.MyServiceChecker;
import com.trydevs.askyourfriends.askurfrnds.tabs.SlidingTabLayout;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;


public class MainActivity extends ActionBarActivity {

    private static final int JOB_ID = 1020;
    private static int NO_OF_FRAGMENTS = 2;
    private static int QUIZ = 0;
    private static int FRIENDS = 1;
    FloatingActionButton floatingActionButton;
    SharedPreferences loginDetails;
    private String unique_id;
    private int user_id;
    private JobScheduler mJobScheduler;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check();
        initialize();
        // constructJob();
    }

    private void check() {
        // getting Logged in data
        loginDetails = getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        user_id = loginDetails.getInt("user_id", 0);
        unique_id = loginDetails.getString("unique_id", "");
        // Checking for discrepency
        if (user_id == 0 || unique_id.equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initialize() {
        // Instantiating JobScheduler
        mJobScheduler = JobScheduler.getInstance(this);
        // Setting Top bar 'Tool bar'
        toolbar = (Toolbar) findViewById(R.id.top_bar_MainActivity);
        setSupportActionBar(toolbar);
        // Mapping the views
        viewPager = (ViewPager) findViewById(R.id.view_pager_main_activity);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout_main_activity);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_main_activity);
        // Setting up the View Pager and the Tab Layout
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.tab_ripple, R.id.textView_tab_ripple);
        slidingTabLayout.setViewPager(viewPager);
        // setting floating action button icons
        int[] iconsId = {R.drawable.ic_action_edit, R.drawable.ic_add_friend};
        // passing floating action button and images
        slidingTabLayout.setImageButtonAndIcons(floatingActionButton, iconsId);
        // Creaiting the click listner for the Floating action button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        viewPager.setCurrentItem(FRIENDS, true);
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.select_friend), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent intent = new Intent(MainActivity.this, AddFriendsActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Still working on finding friends.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public void constructJob() {
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyServiceChecker.class));
        long time = 30 * 60;
        builder.setPeriodic(time)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true);

        mJobScheduler.schedule(builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Selecting the action
        switch (id) {
            case R.id.action_logout:
                SharedPreferences.Editor editor = loginDetails.edit();
                editor.clear();
                Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent3);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tab_names;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tab_names = getResources().getStringArray(R.array.main_activity_tabs);
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
                    fragment = new FragmentViewQuizList();
                    break;
                case 1:
                    fragment = new FragmentUsersList();
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
