package com.trydevs.askyourfriends.askurfrnds.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trydevs.askyourfriends.askurfrnds.Adapters.MyAdapterViewQuestions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.activity.CreateQuiz;
import com.trydevs.askyourfriends.askurfrnds.extras.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentViewQuestion extends Fragment implements CreateQuiz.NotifierNewQuestion {

    RecyclerView recyclerView;
    MyAdapterViewQuestions adapterCreateQuiz;
    private int SPACE_BETWEEN_ITEMS = 2;
    static List<Questions> list;
    Activity activity;
    private static String PARCEL_KEY = "list_parcel";

    public FragmentViewQuestion() {
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
        View view = inflater.inflate(R.layout.fragment_view_question, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_create_quiz);

        // temp data
        list = new ArrayList<>();
        adapterCreateQuiz = new MyAdapterViewQuestions(getActivity(), list, activity);
        recyclerView.setAdapter(adapterCreateQuiz);
        recyclerView.addItemDecoration(new SpacesItemDecoration(SPACE_BETWEEN_ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onNewDataAdded(Questions question) {
        list.add(question);
        adapterCreateQuiz.notifyDataSetChanged();
    }

    @Override
    public List<Questions> OnSendRequested() {
        return list;
    }

}
