package com.trydevs.askyourfriends.askurfrnds.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.R;

import java.util.Collections;
import java.util.List;

public class MyAdapterViewQuestions extends RecyclerView.Adapter<MyAdapterViewQuestions.MyHolder> {

    Context context;
    List<Questions> data = Collections.emptyList();
    LayoutInflater inflater;
    Activity activity;

    public MyAdapterViewQuestions(Context context, List<Questions> data, Activity activity) {
        this.context = context;
        this.data = data;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_single_question_view, parent, false);
        return (new MyHolder(view));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Questions current = data.get(position);
        holder.question.setText(current.getQuestion());
        holder.opt[0].setText(current.getOptionA());
        holder.opt[1].setText(current.getOptionB());
        holder.opt[2].setText(current.getOptionC());
        holder.opt[3].setText(current.getOptionD());
        holder.answer.setText(current.getAnswer());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnEditClicked {
        void OnEdit(Questions questions);
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView question, opt[], answer;
        FloatingActionButton edit;
        public MyHolder(View itemView) {
            super(itemView);
            opt = new TextView[4];
            question = (TextView) itemView.findViewById(R.id.view_question_question);
            opt[0] = (TextView) itemView.findViewById(R.id.view_question_option_A);
            opt[1] = (TextView) itemView.findViewById(R.id.view_question_option_B);
            opt[2] = (TextView) itemView.findViewById(R.id.view_question_option_C);
            opt[3] = (TextView) itemView.findViewById(R.id.view_question_option_D);
            answer = (TextView) itemView.findViewById(R.id.view_question_answer);
            edit = (FloatingActionButton) itemView.findViewById(R.id.floating_action_question_edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Questions questions = data.get(getAdapterPosition());
                    ((OnEditClicked) activity).OnEdit(questions);
                    data.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            data.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            return true;
        }
    }
}
