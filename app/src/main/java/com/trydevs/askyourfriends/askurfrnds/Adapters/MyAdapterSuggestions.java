package com.trydevs.askyourfriends.askurfrnds.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.R;

import java.util.List;


public class MyAdapterSuggestions extends RecyclerView.Adapter<MyAdapterSuggestions.MyHolder> {

    List<Questions> data;
    LayoutInflater inflater;
    Activity activity;

    public MyAdapterSuggestions(Context context, List<Questions> data, Activity activity) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.activity = activity;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_row_question_suggestion, parent, false);
        return new MyHolder(view);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.question.setText(data.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Interfaces to rally data between Fragments
    public interface SuggestionListener {
        void OnSuggested(Questions questions);
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView add;
        TextView question;

        public MyHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question_suggestion);
            add = (ImageView) itemView.findViewById(R.id.imageView_add_question);
            add.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Questions questions = data.get(getAdapterPosition());
            ((SuggestionListener) activity).OnSuggested(questions);
        }
    }
}
