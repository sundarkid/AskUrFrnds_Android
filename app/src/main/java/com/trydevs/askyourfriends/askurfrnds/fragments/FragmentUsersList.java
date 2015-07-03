package com.trydevs.askyourfriends.askurfrnds.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterUserList;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.Request.Method.POST;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUsersList extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MyAdapterUserList userListAdapter;
    SharedPreferences loginDetails;
    List<Friends> list;
    int check = 0;
    long user_id;
    String unique_id;
    RequestQueue requestQueue;
    TextView info;
    private int SPACES_BETWEEN_ITEMS = 2;

    public FragmentUsersList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        // Mapping the views
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewUserList);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_user_list);
        info = (TextView) view.findViewById(R.id.textView_info);
        // Getting request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // getting shared preference data
        loginDetails = getActivity().getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        user_id = loginDetails.getInt("user_id", 0);
        unique_id = loginDetails.getString("unique_id", "");

        // Dummy data
        list = Collections.emptyList();
        // Setting adapter for RecyclerView
        userListAdapter = new MyAdapterUserList(getActivity(), list);
        recyclerView.setAdapter(userListAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACES_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        check = 1;
        return view;
    }

    public void getData() {
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", Long.toString(user_id));
        params.put("unique_id", unique_id);
        CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlFriendList(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("result")) {
                    try {
                        if (response.getString("result").equalsIgnoreCase("success")) {
                            if (response.has("list")) {
                                try {
                                    String s = response.getString("list");
                                    JSONArray jsonArray = new JSONArray(s);
                                    List<Friends> friendsList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        friendsList.add(Friends.getFriendsFromJSONObject(object));
                                    }
                                    if (friendsList.size() > 0) {
                                        MyApplication.getWritableDatabase().insertFriendsList(friendsList);
                                        userListAdapter.newListData(MyApplication.getWritableDatabase().getFriendsList());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
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
                String s = error.toString();
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        });

        requestQueue.add(request);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (check == 1) {
            List<Friends> friendsList = MyApplication.getWritableDatabase().getFriendsList();
            userListAdapter.newListData(friendsList);
            if (friendsList.size() > 0)
                info.setText("");
        }
    }
}
