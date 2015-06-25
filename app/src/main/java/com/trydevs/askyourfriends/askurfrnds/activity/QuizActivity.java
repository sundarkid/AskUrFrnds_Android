package com.trydevs.askyourfriends.askurfrnds.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.clans.fab.FloatingActionButton;
import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterAttendQuestions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Info;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;
import com.trydevs.askyourfriends.askurfrnds.extras.SpacesItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.android.volley.Request.Method.POST;


public class QuizActivity extends ActionBarActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    MyAdapterAttendQuestions adapterMyQuestions;
    FloatingActionButton floatingButton;
    SharedPreferences loginDetails;
    Info info;
    List<Questions> list;
    private int SPACES_BETWEEN_ITEMS = 2;
    private String unique_id;
    private int user_id;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            info = extras.getParcelable("info");
            Toast.makeText(this, info.getName(), Toast.LENGTH_SHORT).show();
        }

        initialize();
    }

    private void initialize() {
        // Setting the Tool bar
        toolbar = (Toolbar) findViewById(R.id.top_bar_quiz_activity);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_question_list_1);
        floatingButton = (FloatingActionButton) findViewById(R.id.send_quiz_activity);
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
        list = MyApplication.getWritableDatabase().getQuestions(info.getGroup());
        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // Setting Adapter
        adapterMyQuestions = new MyAdapterAttendQuestions(this, list);
        recyclerView.setAdapter(adapterMyQuestions);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACES_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", Long.toString(user_id));
                params.put("unique_id", unique_id);
                params.put("group_no", Long.toString(info.getGroup()));
                params.put("answers", adapterMyQuestions.getAnswerJsonArray().toString());
                CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlQuizResult(), params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("result")) {
                            try {
                                if (response.getString("result").equalsIgnoreCase("success")) {
                                    String s = response.toString();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Log.d("json", adapterMyQuestions.getAnswerJsonArray().toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
