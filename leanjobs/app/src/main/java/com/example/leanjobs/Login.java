package com.example.leanjobs;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Login extends AppCompatActivity {


    Button btnadmlogin, btnforgotpassword, btnsignup, btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        btnadmlogin = (Button) findViewById(R.id.btnadmlogin);
        btnforgotpassword = (Button) findViewById(R.id.btnforgotpassword);
        btnlogin = (Button) findViewById(R.id.btnLogin);


        btnadmlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        AdminLogin.class);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "Admin Login Screen", Toast.LENGTH_LONG)
                        .show();
            }
        });

        btnforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        ForgotPassword.class);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "Forgot password Screen", Toast.LENGTH_LONG)
                        .show();
            }
        });


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i = new Intent(getApplicationContext(),Signup.class);
                startActivity(i);
                finish();
              */
                Toast.makeText(getApplicationContext(),
                        "Signup Screen", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnlogin = (Button)findViewById(R.id.btnLogin);
                btnlogin.setClickable(false);
                new LongRunningGetIO().execute();
            }

            class LongRunningGetIO extends AsyncTask<Void, Void, String> {

                protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
                    InputStream in = entity.getContent();
                    StringBuffer out = new StringBuffer();
                    int n = 1;
                    while (n > 0) {
                        byte[] b = new byte[4096];
                        n = in.read(b);
                        if (n > 0) out.append(new String(b, 0, n));
                    }
                    return out.toString();
                }

                @SuppressLint("WrongThread")
                @Override
                protected String doInBackground(Void... params) {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpContext localContext = new BasicHttpContext();
                    HttpGet httpGet = new HttpGet("http://dhillonds.com/leanjobsweb/index.php/api/example/users");
                    String text = null;
                    try {
                        HttpResponse response = httpClient.execute(httpGet, localContext);
                        HttpEntity entity = response.getEntity();
                        text = getASCIIContentFromEntity(entity);
                    } catch (Exception e) {
                        return e.getLocalizedMessage();
                    }
                    return text;
                }

                protected void onPostExecute(String results) {
                    if (results != null) {
                        String JSONresults = results;
                        try {
                            JSONArray users = new JSONArray(JSONresults);
                            EditText edEmail = (EditText) findViewById(R.id.email);
                            EditText edPass = (EditText) findViewById(R.id.password);
                            String em = edEmail.getText().toString();
                            String pass = edPass.getText().toString();

                            for(int i = 0; i<users.length(); i++)
                            {
                                JSONObject obj = users.getJSONObject(i);
                                String email = obj.getString("email");
                                String password = obj.getString("name");
                                if(email.equals(em) && (password.equals(pass))){
                                    Intent intent = new Intent(getApplicationContext(),
                                            UserProfile.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(getApplicationContext(),
                                            "Login successful", Toast.LENGTH_SHORT)
                                            .show();
                                    break;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Button btnLogin = (Button) findViewById(R.id.btnLogin);
                    btnLogin.setClickable(true);
                }
            }
        });
    }
}
