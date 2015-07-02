package com.trydevs.askyourfriends.askurfrnds.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterResult;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Result;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;
import com.trydevs.askyourfriends.askurfrnds.extras.SpacesItemDecoration;

import java.util.List;

public class ResultActivity extends ActionBarActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SharedPreferences loginDetails;
    MyAdapterResult adapterResult;
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
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_result);
        loginDetails = getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        user_id = loginDetails.getInt("user_id", 0);
        unique_id = loginDetails.getString("unique_id", "");
        // getting data
        List<Result> list = MyApplication.getWritableDatabase().getResultList();
        // Setting Adapter
        adapterResult = new MyAdapterResult(ResultActivity.this, list);
        recyclerView.setAdapter(adapterResult);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACES_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(ResultActivity.this));

    }

}
