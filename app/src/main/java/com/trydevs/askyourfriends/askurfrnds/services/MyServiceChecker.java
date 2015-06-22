package com.trydevs.askyourfriends.askurfrnds.services;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Info;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;
import com.trydevs.askyourfriends.askurfrnds.extras.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

import static com.android.volley.Request.Method.POST;


public class MyServiceChecker extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //Toast.makeText(this, "onstart job", Toast.LENGTH_SHORT).show();
        new CheckServer(this).execute(jobParameters);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private static class CheckServer extends AsyncTask<JobParameters, Void, JobParameters> {

        MyServiceChecker myServiceChecker;
        SharedPreferences loginDetails;
        String unique_id, name;
        long user_id;
        HashMap<String, String> params = new HashMap<>();
        private RequestQueue requestQueue;

        CheckServer(MyServiceChecker myServiceChecker) {
            this.myServiceChecker = myServiceChecker;
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
            List<Friends> info = checkInfo();
            return jobParameterses[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            myServiceChecker.jobFinished(jobParameters, false);
        }


        private List<Friends> checkInfo() {

            return null;
        }


        private void getQuestionFromServer(Info info) {
            CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlDownloadQuiz(), params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error.toString());
                    String message = myServiceChecker.getResources().getString(R.string.forgot_password_error) + "\n" + myServiceChecker.getResources().getString(R.string.loginErrorMessage);
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        }

        private void decodeData(JSONObject response) {
            if (response.has("questions")) {
                List<Questions> questions = new ArrayList<>();
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
