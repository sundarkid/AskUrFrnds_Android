package com.trydevs.askyourfriends.askurfrnds.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterSuggestions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.database.DBAskUrFrnd;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;
import com.trydevs.askyourfriends.askurfrnds.extras.SpacesItemDecoration;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentQuizSuggestions extends Fragment {

    RecyclerView recyclerView;
    MyAdapterSuggestions adapterSuggestions;
    Activity activity;
    List<Questions> list;
    TextView info;
    private int SPACES_BETWEEN_ITEMS = 2;

    public FragmentQuizSuggestions() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_suggestions, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_suggestions);
        info = (TextView) view.findViewById(R.id.textView_info);
        // getting data
        list = MyApplication.getWritableDatabase().readAllQuestions(DBAskUrFrnd.TABLE_QUESTIONS);
        if (list.size() > 0)
            info.setText("");
        adapterSuggestions = new MyAdapterSuggestions(getActivity(), list, activity);
        recyclerView.setAdapter(adapterSuggestions);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACES_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }


}
