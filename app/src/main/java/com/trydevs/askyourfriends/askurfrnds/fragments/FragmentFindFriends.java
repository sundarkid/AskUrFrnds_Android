package com.trydevs.askyourfriends.askurfrnds.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterFindFriends;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;
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
public class FragmentFindFriends extends Fragment {

    RecyclerView recyclerView;
    MyAdapterFindFriends adapterFindFriends;
    List<Friends> list;
    SharedPreferences loginDetails;
    private int SPACES_BETWEEN_ITEMS = 2;
    private String unique_id, name;
    private long user_id, offset;
    private RequestQueue requestQueue;

    public FragmentFindFriends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_friends, container, false);

        // Mapping the views
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewUserList);
        // Dummy data
        list = Collections.emptyList();
        offset = 0;
        // Setting adapter for RecyclerView
        adapterFindFriends = new MyAdapterFindFriends(getActivity(), list);
        recyclerView.setAdapter(adapterFindFriends);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACES_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        requestingDataFromServer();
        adapterFindFriends.setLoader(new MyAdapterFindFriends.AsynchronousLoader() {
            @Override
            public void loadMore() {
                requestingDataFromServer();
            }
        });
        return view;
    }

    private void requestingDataFromServer() {
        // getting Logged in data
        loginDetails = getActivity().getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        user_id = loginDetails.getInt("user_id", 0);
        unique_id = loginDetails.getString("unique_id", "");
        name = loginDetails.getString("name", "");

        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();

        // Creating request
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", Long.toString(user_id));
        params.put("unique_id", unique_id);
        params.put("name", name);
        params.put("available", Long.toString(offset));
        CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlGetPeopleList(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("result"))
                    try {
                        if (response.getString("result").equalsIgnoreCase("success")) {
                            List<Friends> friendsList = new ArrayList<>();
                            try {
                                String s = response.getString("list");
                                JSONArray array = new JSONArray(s);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    friendsList.add(Friends.getFriendsFromJSONObject(object));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (friendsList.size() > 0) {
                                adapterFindFriends.updateData(friendsList);
                                offset += friendsList.size();
                            } else if (response.getString("result").equalsIgnoreCase("failure")) {
                                Toast.makeText(getActivity(), "Couldn't find friends try after sometime.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                String message = getResources().getString(R.string.forgot_password_error) + "\n" + getResources().getString(R.string.loginErrorMessage);
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }


}
