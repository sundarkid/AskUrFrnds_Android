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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.Request.Method.POST;

/**
 * Created by Sundareswaran on 18-06-2015.
 */
public class MyAdapterRequest extends RecyclerView.Adapter<MyAdapterRequest.MyViewHolder> {

    Context context;
    List<Friends> data = Collections.emptyList();
    SharedPreferences loginDetails;
    private LayoutInflater inflater;
    private String unique_id, name;
    private long user_id;
    private RequestQueue requestQueue;

    public MyAdapterRequest(Context context, List<Friends> friends) {
        data = friends;
        this.context = context;
        inflater = LayoutInflater.from(context);

        // getting Logged in data
        loginDetails = context.getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        user_id = loginDetails.getInt("user_id", 0);
        unique_id = loginDetails.getString("unique_id", "");
        name = loginDetails.getString("name", "");

        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_row_user, viewGroup, false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(data.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<Friends> data) {
        if (this.data.size() == 0)
            this.data = data;
        else
            this.data.addAll(data);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            HashMap<String, String> params = new HashMap<>();
            params.put("sender_id", Long.toString(user_id));
            params.put("unique_id", unique_id);
            params.put("name", name);
            params.put("reciever_id", Long.toString(data.get(getAdapterPosition()).getId()));
            params.put("request_id", data.get(getAdapterPosition()).getRequest_id());
            final CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlConfirmFriends(), params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.has("result")) {
                        try {
                            if (response.getString("result").equalsIgnoreCase("success")) {
                                String s = response.getString("friend");
                                JSONObject object = new JSONObject(s);
                                List<Friends> f = new ArrayList<>();
                                f.add(Friends.getFriendsFromJSONObject(object));
                                MyApplication.getWritableDatabase().insertFriendsList(f);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error.toString());
                    String message = context.getResources().getString(R.string.forgot_password_error) + "\n(or)" + context.getResources().getString(R.string.loginErrorMessage);
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(request);
        }
    }
}
