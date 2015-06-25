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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterSuggestions;
import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterViewQuestions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Info;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;
import com.trydevs.askyourfriends.askurfrnds.fragments.FragmentCreateQuizQuestion;
import com.trydevs.askyourfriends.askurfrnds.fragments.FragmentQuizSuggestions;
import com.trydevs.askyourfriends.askurfrnds.fragments.FragmentViewQuestion;
import com.trydevs.askyourfriends.askurfrnds.tabs.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.Request.Method.POST;


public class CreateQuiz extends ActionBarActivity implements FragmentCreateQuizQuestion.NewQuestionListener, MyAdapterViewQuestions.OnEditClicked, MyAdapterSuggestions.SuggestionListener {

    private static int NO_OF_FRAGMENTS = 3;

    private static int FRAGMENT_CREATE_QUESTIONS = 0;
    private static int FRAGMENT_VIEW_QUESTIONS = 1;
    private static int FRAGMENT_SUGGEST_QUESTIONS = 2;
    public List<Questions> questions = Collections.emptyList();
    MyPagerAdapter adapter;
    SharedPreferences loginDetails;
    private String unique_id, name;
    private long user_id;
    private Friends friends;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            friends = extras.getParcelable("user");
            Toast.makeText(this, friends.getName(), Toast.LENGTH_SHORT).show();
        }
        initialize();

    }

    private void initialize() {
        // Setting Top bar 'Tool bar'
        toolbar = (Toolbar) findViewById(R.id.top_bar_new_quiz);
        setSupportActionBar(toolbar);
        // Mapping the views
        viewPager = (ViewPager) findViewById(R.id.view_pager_create_quiz_activity);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout_create_quiz_activity);
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
        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // Setting up the View Pager and the Tab Layout
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.tab_ripple, R.id.textView_tab_ripple);
        slidingTabLayout.setViewPager(viewPager);


    }

    @Override
    public void onAddQuestion(Questions question) {
        FragmentViewQuestion fragmentViewQuestion = (FragmentViewQuestion) adapter.instantiateItem(viewPager, FRAGMENT_VIEW_QUESTIONS);
        fragmentViewQuestion.onNewDataAdded(question);
        viewPager.setCurrentItem(FRAGMENT_VIEW_QUESTIONS, true);
    }

    @Override
    public void OnEdit(Questions questions) {
        FragmentCreateQuizQuestion fragmentCreateQuizQuestion = (FragmentCreateQuizQuestion) adapter.instantiateItem(viewPager, FRAGMENT_CREATE_QUESTIONS);
        fragmentCreateQuizQuestion.setData(questions);
        viewPager.setCurrentItem(FRAGMENT_CREATE_QUESTIONS, true);
    }

    @Override
    public void OnSuggested(Questions questions) {
        FragmentViewQuestion fragmentViewQuestion = (FragmentViewQuestion) adapter.instantiateItem(viewPager, FRAGMENT_VIEW_QUESTIONS);
        fragmentViewQuestion.onNewDataAdded(questions);
        //viewPager.setCurrentItem(FRAGMENT_VIEW_QUESTIONS,true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            // Getting the List data from the View Question Fragment
            FragmentViewQuestion fragmentViewQuestion = (FragmentViewQuestion) adapter.instantiateItem(viewPager, FRAGMENT_VIEW_QUESTIONS);
            List<Questions> list = fragmentViewQuestion.OnSendRequested();
            viewPager.setCurrentItem(FRAGMENT_VIEW_QUESTIONS, true);
            if (list.size() == 0 || friends == null) {
                Toast.makeText(CreateQuiz.this, getResources().getString(R.string.no_question), Toast.LENGTH_SHORT).show();
                return true;
            }

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < list.size(); i++)
                jsonArray.put(list.get(i).getJSONObjectQuestion());
            Log.d("List ", jsonArray.toString());
            sendQuestioDataToServer(jsonArray, list);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertSuggestionsIntoDatabase(List<Questions> list) {
        MyApplication.getWritableDatabase().insertQuestionSuggestions(list);
    }

    private void sendQuestioDataToServer(JSONArray jsonArray, List<Questions> list) {
        HashMap<String, String> params = new HashMap<>();
        insertSuggestionsIntoDatabase(list);
        params.put("user_id", Long.toString(user_id));
        params.put("unique_id", unique_id);
        params.put("name", name);
        params.put("quiz_data", jsonArray.toString());
        params.put("friends", Long.toString(friends.getId()));
        Log.d("user id", Long.toString(user_id));
        CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlUploadQuiz(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("result")) {
                    try {
                        if (response.getString("result").equalsIgnoreCase("success")) {
                            long group = response.getLong("group_no");
                            String date = response.getString("date");
                            Info info = new Info();
                            info.setName(friends.getName());
                            info.setUser_id(user_id);
                            info.setGroup(group);
                            info.setDate(date);
                            getQuestionFromServer(info);
                            Toast.makeText(CreateQuiz.this, "Sent", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                String message = getResources().getString(R.string.forgot_password_error) + "\n" + getResources().getString(R.string.loginErrorMessage);
                Toast.makeText(CreateQuiz.this, message, Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private void getQuestionFromServer(final Info infoData) {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", Long.toString(user_id));
        params.put("unique_id", unique_id);
        params.put("name", name);
        params.put("group_id", Long.toString(infoData.getGroup()));
        CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlDownloadQuiz(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("questions")) {
                    List<Questions> questions = new ArrayList<>();
                    try {
                        String s = response.getString("questions");
                        JSONArray jsonArray = new JSONArray(s);
                        List<Questions> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            list.add(Questions.getQuestionsFromJson(object));
                        }
                        if (list.size() > 0) {
                            MyApplication.getWritableDatabase().insertInfo(infoData);
                            MyApplication.getWritableDatabase().insertQuestions(list);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                String message = getResources().getString(R.string.forgot_password_error) + "\n" + getResources().getString(R.string.loginErrorMessage);
                Toast.makeText(CreateQuiz.this, message, Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    // Interfaces to rally data between Fragments
    public interface NotifierNewQuestion {
        void onNewDataAdded(Questions question);

        List<Questions> OnSendRequested();
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tab_names;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tab_names = getResources().getStringArray(R.array.create_quiz_activity_tabs);
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
                    fragment = new FragmentCreateQuizQuestion();
                    break;
                case 1:
                    fragment = new FragmentViewQuestion();
                    break;
                case 2:
                    fragment = new FragmentQuizSuggestions();
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
