package com.trydevs.askyourfriends.askurfrnds.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterResult;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Result;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;
import com.trydevs.askyourfriends.askurfrnds.extras.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.Request.Method.POST;

public class ResultActivity extends ActionBarActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SharedPreferences loginDetails;
    MyAdapterResult adapterResult;
    SwipeRefreshLayout swipeRefreshLayout;
    RequestQueue requestQueue;
    private String unique_id;
    private int user_id;
    private int SPACES_BETWEEN_ITEMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initialize();
    }

    private void initialize() {
        // Setting the Tool bar
        toolbar = (Toolbar) findViewById(R.id.top_bar_result_activity);
        setSupportActionBar(toolbar);
        // mapping UI elements
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh_result);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_result);
        loginDetails = getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        user_id = loginDetails.getInt("user_id", 0);
        unique_id = loginDetails.getString("unique_id", "");
        // getting data
        final List<Result> list = MyApplication.getWritableDatabase().getResultList();
        // getting request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // Setting Adapter
        adapterResult = new MyAdapterResult(ResultActivity.this, list);
        recyclerView.setAdapter(adapterResult);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACES_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
        // Setting swipe listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", Long.toString(user_id));
                params.put("unique_id", unique_id);
                if (list.size() > 0)
                    params.put("sno", Long.toString(list.get(0).getSno()));
                else
                    params.put("sno", Long.toString(0));
                CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlQuizResult(), params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("result")) {
                            try {
                                if (response.getString("result").equalsIgnoreCase("success")) {
                                    if (response.has("object"))
                                        try {
                                            String s = response.getString("object");
                                            JSONArray jsonArray = new JSONArray(s);
                                            List<Result> results = new ArrayList<>();
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                results.add(Result.getResultFromJSON(object));
                                            }
                                            if (results.size() > 0)
                                                MyApplication.getWritableDatabase().insertResults(results);
                                        } catch (JSONException e) {
                                            if (response.get("object").equals(null))
                                                Toast.makeText(ResultActivity.this, getResources().getString(R.string.no_new_data_found), Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                    adapterResult.onUpdated(MyApplication.getWritableDatabase().getResultList());
                                } else {
                                    Toast.makeText(ResultActivity.this, getResources().getString(R.string.no_new_data_found), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResultActivity.this, getResources().getString(R.string.forgot_password_error), Toast.LENGTH_LONG).show();
                        adapterResult.onUpdated(MyApplication.getWritableDatabase().getResultList());
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }
                });
                requestQueue.add(request);
            }
        });

    }

}
