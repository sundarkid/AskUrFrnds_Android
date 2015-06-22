package com.trydevs.askyourfriends.askurfrnds.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCreateQuizQuestion extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Activity activity;
    FloatingActionButton floatingActionButton;
    EditText[] option;
    int x = 0;
    EditText question;
    RadioGroup radioGroup;

    public FragmentCreateQuizQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_quiz_questions, container, false);
        // Initializing items
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_action_fragment_new_question);
        option = new EditText[4];
        option[0] = (EditText) view.findViewById(R.id.fragment_create_question_optionA);
        option[1] = (EditText) view.findViewById(R.id.fragment_create_question_optionB);
        option[2] = (EditText) view.findViewById(R.id.fragment_create_question_optionC);
        option[3] = (EditText) view.findViewById(R.id.fragment_create_question_optionD);
        question = (EditText) view.findViewById(R.id.fragment_create_question_question);
        radioGroup = (RadioGroup) view.findViewById(R.id.fragment_create_question_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        floatingActionButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Questions questions = new Questions();
        if (validate()) {
            questions.setQuestion(question.getText().toString());
            questions.setOptionA(option[0].getText().toString());
            questions.setOptionB(option[1].getText().toString());
            questions.setOptionC(option[2].getText().toString());
            questions.setOptionD(option[3].getText().toString());
            questions.setAnswer(x);
            ((NewQuestionListener) activity).onAddQuestion(questions);
            clear();
        } else {
            Toast.makeText(getActivity(), "Something missing :-)", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        String empty = "";
        question.setText(empty);
        option[0].setText(empty);
        option[1].setText(empty);
        option[2].setText(empty);
        option[3].setText(empty);
    }

    private boolean validate() {
        // If either question not entered or at least one option not entered
        return !(question.getText().toString().equalsIgnoreCase("") || (option[0].getText().toString().equalsIgnoreCase("") && option[1].getText().toString().equalsIgnoreCase("")
                && option[2].getText().toString().equalsIgnoreCase("") && option[3].getText().toString().equalsIgnoreCase("")) || radioGroup.getCheckedRadioButtonId() == -1);
    }


    public void setData(Questions questions) {
        question.setText(questions.getQuestion());
        option[0].setText(questions.getOptionA());
        option[1].setText(questions.getOptionB());
        option[2].setText(questions.getOptionC());
        option[3].setText(questions.getOptionD());
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
        if (radioButton.getText().toString().equalsIgnoreCase("A")) {
            x = 0;
        } else if (radioButton.getText().toString().equalsIgnoreCase("B")) {
            x = 1;
        } else if (radioButton.getText().toString().equalsIgnoreCase("C")) {
            x = 2;
        } else if (radioButton.getText().toString().equalsIgnoreCase("D")) {
            x = 3;
        }
    }

    // Interfaces to rally data between Fragments
    public interface NewQuestionListener {
        void onAddQuestion(Questions question);
    }

}
