package com.trydevs.askyourfriends.askurfrnds.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trydevs.askyourfriends.askurfrnds.DataSet.Result;
import com.trydevs.askyourfriends.askurfrnds.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sundareswaran on 01-07-2015.
 */
public class MyAdapterResult extends RecyclerView.Adapter<MyAdapterResult.MyViewHolder> {

    List<Result> data = Collections.emptyList();
    Context context;
    LayoutInflater inflater;

    public MyAdapterResult(Context context, List<Result> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_single_result, parent, false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Result current = data.get(position);
        holder.name.setText(current.getName());
        holder.marks.setText(Integer.toString(current.getMarks()));
        holder.total.setText(Integer.toString(current.getTotal()));
        holder.date.setText(current.getDate());
    }

    public void onUpdated(List<Result> list) {
        data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, marks, total, date;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.custom_row_result_name);
            marks = (TextView) itemView.findViewById(R.id.custom_row_result_marks);
            total = (TextView) itemView.findViewById(R.id.custom_row_result_total);
            date = (TextView) itemView.findViewById(R.id.custom_row_result_date);
        }
    }
}
