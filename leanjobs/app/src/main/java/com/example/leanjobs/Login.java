package com.example.leanjobs;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button btnadmlogin, btnforgotpassword, btnsignup, btnlogin;
    EditText Email,Password;
    String URLPost="http://dhillonds.com/leanjobsweb/index.php/api/users/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnsignup = findViewById(R.id.btnsignup);
        btnadmlogin = findViewById(R.id.btnadmlogin);
        btnforgotpassword = findViewById(R.id.btnforgotpassword);
        btnlogin = findViewById(R.id.btnLogin);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

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
                Intent i = new Intent(getApplicationContext(),SignUp.class);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "SignUp Screen", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PostSignUpData();
                } catch (Exception ex) {
                }
            }
        });
    }
    private void PostSignUpData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLPost, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject UserCredentials = new JSONObject(response);
                    String LoginFlag = UserCredentials.getString("status");
                    String Message = UserCredentials.getString("message");
                    if(LoginFlag == "true"){
                    JSONObject Data = UserCredentials.getJSONObject("data");
                    String Salt = Data.getString("salt");
                    String User_ID = Data.getString("user_id");
                    String Email = Data.getString("email");
                    String Phone_num = Data.getString("phone_num");
                    String Full_Name = Data.getString("full_name");
//                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("salt", Salt).commit();
//                    String User_ID = Data.getString("user_id");
//                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("user_id", User_ID).commit();
//                    String Email = Data.getString("email");
//                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("email", Email).commit();
//                    String Phone_num = Data.getString("phone_num");
//                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("phone_num", Phone_num).commit();
//                    String Full_Name = Data.getString("full_name");
//                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("full_name", Full_Name).commit();

                        Intent intent = new Intent(getApplicationContext(),
                                User_HomeScreen.class);
                        intent.putExtra("userid",User_ID);
                        intent.putExtra("FullName",Full_Name);
                        intent.putExtra("email",Email);
                        intent.putExtra("phoneNo",Phone_num);
                        intent.putExtra("salt",Salt);

                        startActivity(intent);
                        finish();
                    }
                    else if(LoginFlag == "false"){
                        Toast.makeText(getApplication(),Message,Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplication(),ex.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                int Is_Admin = 0;
                params.put("email",email);
                params.put("password",password);
                params.put("is_admin",String.valueOf(Is_Admin));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }}