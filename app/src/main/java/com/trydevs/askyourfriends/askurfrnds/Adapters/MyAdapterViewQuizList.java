package com.trydevs.askyourfriends.askurfrnds.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trydevs.askyourfriends.askurfrnds.DataSet.Info;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.activity.QuizActivity;

import java.util.Collections;
import java.util.List;


public class MyAdapterViewQuizList extends RecyclerView.Adapter<MyAdapterViewQuizList.MyViewHolder> {

    List<Info> data = Collections.emptyList();
    Context context;
    private LayoutInflater inflater;
    private long user_id;

    public MyAdapterViewQuizList(Context context, List<Info> data, long user_id) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        this.user_id = user_id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_row_quiz_list, parent, false);

        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Info current = data.get(position);
        if (current.getUser_id() == user_id)
            holder.imageView.setImageResource(R.drawable.ic_my_quiz);
        else
            holder.imageView.setImageResource(R.drawable.ic_quiz);
        holder.textViewName.setText(current.getName());
        holder.textViewDate.setText(current.getDate());
    }

    public void listDataAdded(List<Info> list) {
        if (data.size() > 0)
            data.addAll(list);
        else
            data = list;
        notifyDataSetChanged();
    }

    public void newListData(List<Info> list) {
        if (data.size() > 0)
            data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textViewName, textViewDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_quiz_list);
            textViewName = (TextView) itemView.findViewById(R.id.text_name_quiz_list);
            textViewDate = (TextView) itemView.findViewById(R.id.text_date_quiz_list);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // TODO set the intent open action along with sending the data from info table
            Info current = data.get(getAdapterPosition());
            Intent intent = new Intent(context, QuizActivity.class);
            intent.putExtra("info", current);
            context.startActivity(intent);
        }
    }
}
