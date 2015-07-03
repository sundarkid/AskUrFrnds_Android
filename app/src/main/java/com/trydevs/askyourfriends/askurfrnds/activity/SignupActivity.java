package com.trydevs.askyourfriends.askurfrnds.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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


public class SignupActivity extends ActionBarActivity {

    private static RequestQueue requestQueue;
    public String clear = "";
    Toolbar toolbar;
    EditText name, email, phone, institution, place, password;
    TextView terms;
    Button signup, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initialize();
    }

    private void initialize() {
        // Setting Top bar 'Tool bar'
        toolbar = (Toolbar) findViewById(R.id.top_bar_signup);
        setSupportActionBar(toolbar);
        // Mapping the view elements
        name = (EditText) findViewById(R.id.signup_name);
        email = (EditText) findViewById(R.id.signup_mail);
        phone = (EditText) findViewById(R.id.signup_phone);
        institution = (EditText) findViewById(R.id.signup_institution);
        place = (EditText) findViewById(R.id.signup_place);
        password = (EditText) findViewById(R.id.signup_password);
        signup = (Button) findViewById(R.id.signup_signup);
        reset = (Button) findViewById(R.id.signup_reset);
        terms = (TextView) findViewById(R.id.privacy_and_terms);
        // Getting the request queue
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // Setting button click listeners
        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                name.setText(clear);
                email.setText(clear);
                phone.setText(clear);
                institution.setText(clear);
                place.setText(clear);
                password.setText(clear);
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlLinksNames.getUrlPrivacyAndTerms()));
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {
                    Toast.makeText(SignupActivity.this, "Please wait Signing up..", Toast.LENGTH_LONG).show();
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name.getText().toString());
                    params.put("mail_id", email.getText().toString());
                    params.put("phone", phone.getText().toString());
                    params.put("place", place.getText().toString());
                    params.put("institution", institution.getText().toString());
                    params.put("password", password.getText().toString());
                    CustomRequest customRequest = new CustomRequest(POST, UrlLinksNames.getUrlSignup(), params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response.has("result")) {
                                try {
                                    String signupSuccessMessage = getResources().getString(R.string.signupSuccessMessage);
                                    if (response.get("result").toString().equalsIgnoreCase("success")) {
                                        Toast.makeText(SignupActivity.this, signupSuccessMessage, Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (response.has("existing")) {
                                try {
                                    Toast.makeText(SignupActivity.this, response.getString("existing"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String signupErrorMessage = getResources().getString(R.string.signupErrorMessage);
                            Toast.makeText(getApplicationContext(), signupErrorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                    requestQueue.add(customRequest);
                } else {
                    String emptyFieldsMessage = getResources().getString(R.string.emptyFieldMessage);
                    Toast.makeText(SignupActivity.this, emptyFieldsMessage, Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.contacts_data), Toast.LENGTH_LONG).show();
                }

            }

            private boolean validate() {
                return !(name.getText().toString().equals(clear) || email.getText().toString().equals(clear) ||
                        phone.getText().toString().equals(clear) || place.getText().toString().equals(clear)
                        || institution.getText().toString().equals(clear) || password.getText().toString().equals(clear));
            }
        });

    }


}
