package com.trydevs.askyourfriends.askurfrnds.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterUserList;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.extras.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUsersList extends Fragment {

    RecyclerView recyclerView;
    MyAdapterUserList userListAdapter;
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
        // Dummy data
        List<Friends> list = new ArrayList<>();
        for (int i = 100; i < 110; i++) {
            Friends data = new Friends();
            data.setName("Name " + (i - 99));
            data.setId(i);
            list.add(data);
        }
        // Setting adapter for RecyclerView
        userListAdapter = new MyAdapterUserList(getActivity(), list);
        recyclerView.setAdapter(userListAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACES_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


}
