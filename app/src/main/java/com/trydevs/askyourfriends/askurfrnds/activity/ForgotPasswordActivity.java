package com.trydevs.askyourfriends.askurfrnds.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trydevs.askyourfriends.askurfrnds.DataSet.UrlLinksNames;
import com.trydevs.askyourfriends.askurfrnds.Network.CustomRequest;
import com.trydevs.askyourfriends.askurfrnds.Network.VolleySingleton;
import com.trydevs.askyourfriends.askurfrnds.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class ForgotPasswordActivity extends ActionBarActivity {

    private static RequestQueue requestQueue;
    Toolbar toolbar;
    EditText mail;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialize();
    }

    private void initialize() {
        // Setting Top bar 'Tool bar'
        toolbar = (Toolbar) findViewById(R.id.top_bar_forgot_password);
        setSupportActionBar(toolbar);
        // Mapping the views
        mail = (EditText) findViewById(R.id.forgot_password_mail_id);
        send = (Button) findViewById(R.id.forgot_password_send);
        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // Setting Onclick Listener
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Map<String, String> params = new HashMap<>();
                    params.put("mail_id", mail.getText().toString());
                    CustomRequest request = new CustomRequest(POST, UrlLinksNames.getUrlForgotPassword(), params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response.has("result")) {
                                try {
                                    if (response.getString("result").equalsIgnoreCase("success")) {
                                        String message = getResources().getString(R.string.forgot_password_success);
                                        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_LONG).show();

                                    } else if (response.getString("result").equals("failure")) {
                                        if (response.getString("message").equalsIgnoreCase("not found")) {
                                            String message = getResources().getString(R.string.forgot_password_not_found);
                                            Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        String message = getResources().getString(R.string.forgot_password_error);
                                        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    String s = e.toString();
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String ErrorMessage = getResources().getString(R.string.signupErrorMessage);
                            Toast.makeText(getApplicationContext(), ErrorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                    requestQueue.add(request);
                }
            }
        });
    }

    private boolean validate() {
        return !mail.getText().toString().equals("");
    }

}
