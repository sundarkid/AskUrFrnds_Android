package com.trydevs.askyourfriends.askurfrnds.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.activity.CreateQuiz;

import java.util.Collections;
import java.util.List;


public class MyAdapterUserList extends RecyclerView.Adapter<MyAdapterUserList.MyViewHolder> {

    List<Friends> data = Collections.emptyList();
    Context context;
    private LayoutInflater inflater;

    public MyAdapterUserList(Context context, List<Friends> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_user, parent, false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Friends current = data.get(position);
        holder.textView.setText(current.getName());
    }

    public void newListData(List<Friends> list) {
        if (data.size() > 0)
            data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void listDataAdded(List<Friends> list) {
        if (data.size() > 0)
            data.addAll(list);
        else
            data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewCustomRow);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewCustomRow);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, CreateQuiz.class);
            intent.putExtra("user", data.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }

}
