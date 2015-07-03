package com.trydevs.askyourfriends.askurfrnds.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterViewQuizList;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Info;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;
import com.trydevs.askyourfriends.askurfrnds.extras.SpacesItemDecoration;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentViewQuizList extends Fragment {

    private static int SPACES_BETWEEN_ITEMS = 2;
    RecyclerView recyclerView;
    MyAdapterViewQuizList adapterViewQuizList;
    List<Info> list;
    TextView info;

    public FragmentViewQuizList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_quiz_list, container, false);
        // Mapping the views
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_quiz_list);
        info = (TextView) view.findViewById(R.id.textView_info);
        SharedPreferences loginDetails = getActivity().getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        // Setting Adapter
        list = Collections.emptyList();
        adapterViewQuizList = new MyAdapterViewQuizList(getActivity(), list, loginDetails.getInt("user_id", 0));
        recyclerView.setAdapter(adapterViewQuizList);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACES_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Info> newList = MyApplication.getWritableDatabase().getInfo();
        adapterViewQuizList.newListData(newList);
        if (newList.size() > 0)
            info.setText("");
    }
}
