package com.trydevs.askyourfriends.askurfrnds.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.Request.Method.POST;


public class MyAdapterFindFriends extends RecyclerView.Adapter<MyAdapterFindFriends.MyViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<Friends> data = Collections.emptyList();
    AsynchronousLoader loader;
    SharedPreferences loginDetails;
    private String unique_id, name;
    private long user_id;
    private RequestQueue requestQueue;

    public MyAdapterFindFriends(Context context, List<Friends> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        // getting Logged in data
        loginDetails = context.getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        user_id = loginDetails.getInt("user_id", 0);
        unique_id = loginDetails.getString("unique_id", "");
        name = loginDetails.getString("name", "");

        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();

    }

    public void setLoader(AsynchronousLoader loader) {
        this.loader = loader;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_row_find_friends, viewGroup, false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        if (i < data.size()) {
            myViewHolder.textViewName.setText(data.get(i).getName());
            myViewHolder.textViewInstitution.setText(data.get(i).getInstitution());
        } else {
            myViewHolder.textViewName.setText("Load more data");
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public void updateData(List<Friends> data) {
        if (this.data.size() == 0)
            this.data = data;
        else
            this.data.addAll(data);
        notifyDataSetChanged();
    }

    public interface AsynchronousLoader {
        void loadMore();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewInstitution;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewFindFriendsName);
            textViewInstitution = (TextView) itemView.findViewById(R.id.textViewFindFriendsInstitution);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewCustomRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == getItemCount() - 1) {
                loader.loadMore();
            } else {
                HashMap<String, String> params = new HashMap<>();
                params.put("sender_id", Long.toString(user_id));
                params.put("unique_id", unique_id);
                params.put("name", name);
                params.put("reciever_id", Long.toString(data.get(getAdapterPosition()).getId()));
                CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlAddFriends(), params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("result")) {
                            try {
                                Toast.makeText(context, response.getString("result"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                        String s = error.toString();
                        String message = context.getResources().getString(R.string.forgot_password_error) + "\n(or)" + context.getResources().getString(R.string.loginErrorMessage);
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });
                request.setRetryPolicy(new DefaultRetryPolicy(5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
            }
        }
    }
}
