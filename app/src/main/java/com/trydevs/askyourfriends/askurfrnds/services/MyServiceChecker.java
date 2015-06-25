package com.trydevs.askyourfriends.askurfrnds.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Info;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

import static com.android.volley.Request.Method.POST;


public class MyServiceChecker extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Toast.makeText(this, "onstart job", Toast.LENGTH_SHORT).show();
        new CheckServer(this, this).execute(jobParameters);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private static class CheckServer extends AsyncTask<JobParameters, Void, JobParameters> {

        MyServiceChecker myServiceChecker;
        Context context;
        SharedPreferences loginDetails;
        String unique_id, name;
        long user_id;
        HashMap<String, String> params = new HashMap<>();
        JSONObject response = null;
        private RequestQueue requestQueue;

        CheckServer(MyServiceChecker myServiceChecker, Context context) {
            this.myServiceChecker = myServiceChecker;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // getting Logged in data
            loginDetails = myServiceChecker.getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
            user_id = loginDetails.getInt("user_id", 0);
            unique_id = loginDetails.getString("unique_id", "");
            name = loginDetails.getString("name", "");
            params.put("user_id", Long.toString(user_id));
            params.put("unique_id", unique_id);
            params.put("name", name);
            requestQueue = VolleySingleton.getInstance().getmRequestQueue();
            super.onPreExecute();
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameterses) {
            getDataFromServer();
            return jobParameterses[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            myServiceChecker.jobFinished(jobParameters, false);
        }

        private void getDataFromServer() {

            RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

            CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlGetNewInfo(), params, requestFuture, requestFuture);
            requestQueue.add(request);

            try {
                response = requestFuture.get(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            if (response != null)
                if (response.has("result")) {
                    try {
                        //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                        if (response.getString("result").equalsIgnoreCase("success")) {
                            decodeData(response);
                        } else {
                            //Toast.makeText(context,"result failure",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }

        private void decodeData(JSONObject response) {
            if (response.has("list")) {
                try {
                    String s = response.getString("list");
                    JSONArray jsonArray = new JSONArray(s);
                    List<Info> infos = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        infos.add(Info.getInfoFromJSONObject(object));
                    }
                    if (infos.size() > 0)
                        MyApplication.getWritableDatabase().insertInfoList(infos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (response.has("questions")) {
                try {
                    String s = response.getString("questions");
                    JSONArray jsonArray = new JSONArray(s);
                    List<Questions> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        list.add(Questions.getQuestionsFromJson(object));
                    }
                    if (list.size() > 0)
                        MyApplication.getWritableDatabase().insertQuestions(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
