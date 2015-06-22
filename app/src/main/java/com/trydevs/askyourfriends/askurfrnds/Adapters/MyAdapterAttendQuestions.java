package com.trydevs.askyourfriends.askurfrnds.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.R;

import org.json.JSONArray;

import java.util.Collections;
import java.util.List;

public class MyAdapterAttendQuestions extends RecyclerView.Adapter<MyAdapterAttendQuestions.MyViewHolder> {

    List<Questions> data = Collections.emptyList();
    Context context;
    private LayoutInflater inflater;

    public MyAdapterAttendQuestions(Context context, List<Questions> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        if (data.size() > 0)
            this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_single_question_attend, parent, false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Questions current = data.get(position);
        holder.question.setText(current.getQuestion());
        holder.rb0.setText(current.getOptionA());
        holder.rb1.setText(current.getOptionB());
        holder.rb2.setText(current.getOptionC());
        holder.rb3.setText(current.getOptionD());
        holder.radioGroup.check(holder.radioGroup.getChildAt(current.getAnswer_Id()).getId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public JSONArray getAnswer() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            array.put(data.get(i).getJSONObjectAnswers());
        }
        return array;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView question;
        RadioGroup radioGroup;
        RadioButton rb0, rb1, rb2, rb3;

        public MyViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.card_question_question);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.card_question_radio_group);
            rb0 = (RadioButton) itemView.findViewById(R.id.card_question_radio_0);
            rb1 = (RadioButton) itemView.findViewById(R.id.card_question_radio_1);
            rb2 = (RadioButton) itemView.findViewById(R.id.card_question_radio_2);
            rb3 = (RadioButton) itemView.findViewById(R.id.card_question_radio_3);
            rb0.setOnClickListener(this);
            rb1.setOnClickListener(this);
            rb2.setOnClickListener(this);
            rb3.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.card_question_radio_0:
                    data.get(getAdapterPosition()).setAnswer(0);
                    break;
                case R.id.card_question_radio_1:
                    data.get(getAdapterPosition()).setAnswer(1);
                    break;
                case R.id.card_question_radio_2:
                    data.get(getAdapterPosition()).setAnswer(2);
                    break;
                case R.id.card_question_radio_3:
                    data.get(getAdapterPosition()).setAnswer(3);
                    break;
            }
        }
    }
}
