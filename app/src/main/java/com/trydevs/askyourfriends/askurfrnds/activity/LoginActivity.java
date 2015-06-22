package com.trydevs.askyourfriends.askurfrnds.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class LoginActivity extends ActionBarActivity {

    private static RequestQueue requestQueue;
    EditText mail, password;
    Button login;
    TextView signup, forgotPassword;
    SharedPreferences loginDetails;
    private String clear = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CheckEditor();
        initialize();
    }

    private void CheckEditor() {
        // getting Logged in data if any
        loginDetails = getSharedPreferences(UrlLinksNames.getLoginFileName(), 0);
        int user_id = loginDetails.getInt("user_id", 0);
        String unique_id = loginDetails.getString("unique_id", "");
        // Checking for discrepency
        if (!(user_id == 0 || unique_id.equals(""))) {
            SharedPreferences.Editor editor = loginDetails.edit();
            editor.clear();
            editor.commit();
            finish();
        }
    }

    private void initialize() {
        // mapping the elements
        mail = (EditText) findViewById(R.id.login_mail);
        password = (EditText) findViewById(R.id.login_pass);
        login = (Button) findViewById(R.id.login_button);
        signup = (TextView) findViewById(R.id.login_signup);
        forgotPassword = (TextView) findViewById(R.id.login_forgot_password);
        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // Setting button click listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Toast.makeText(LoginActivity.this, "Please wait Logging in..", Toast.LENGTH_LONG).show();
                    Map<String, String> params = new HashMap<>();
                    params.put("mail", mail.getText().toString());
                    params.put("pass", password.getText().toString());
                    CustomRequest customRequest = new CustomRequest(POST, UrlLinksNames.getUrlLogin(), params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response.has("result")) {
                                try {
                                    if (response.get("result").toString().equalsIgnoreCase("success")) {
                                        SharedPreferences loginDetails = getSharedPreferences(UrlLinksNames.getLoginFileName(), MODE_PRIVATE);
                                        SharedPreferences.Editor editor = loginDetails.edit();
                                        editor.putInt("user_id", response.getInt("user_id"));
                                        editor.putString("unique_id", response.getString("uniq_id"));
                                        editor.putString("name", response.getString("name"));
                                        editor.commit();
                                        Toast.makeText(LoginActivity.this, "Welcome " + response.getString("name"), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (response.getString("result").equalsIgnoreCase("failure")) {
                                        String loginWrongDetailsMessage = getResources().getString(R.string.loginWrongDetailsMessage);
                                        Toast.makeText(LoginActivity.this, loginWrongDetailsMessage, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String loginErrorMessage = getResources().getString(R.string.loginErrorMessage);
                            Toast.makeText(getApplicationContext(), loginErrorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                    requestQueue.add(customRequest);
                } else {
                    String emptyFieldMessage = getResources().getString(R.string.emptyFieldMessage);
                    Toast.makeText(LoginActivity.this, emptyFieldMessage, Toast.LENGTH_LONG).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate() {
        return !(mail.getText().toString().equals(clear) || password.getText().toString().equals(clear));
    }


}
