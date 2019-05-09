package com.example.leanjobs;
import android.content.Intent;
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
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {
    EditText Email, Password, Full_Name, Phone_Num;
    Button Submit;
    String URLPost="http://dhillonds.com/leanjobsweb/index.php/api/users/register";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Email = findViewById(R.id.editEmail);
        Password = findViewById(R.id.editPassword);
        Full_Name = findViewById(R.id.editFullName);
        Phone_Num = findViewById(R.id.editMobile);
        Submit = findViewById(R.id.Signup);

        Submit.setOnClickListener(new Button.OnClickListener() {

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
                        String SignUp = "El usuario ha sido creado exitosamente";
                        Toast.makeText(getApplication(),SignUp,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),
                                Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(LoginFlag == "false"){
                        Toast.makeText(getApplication(),Message,Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){

                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp.this,error+"",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String full_name = Full_Name.getText().toString();
                String phone_num = Phone_Num.getText().toString();
                params.put("email",email);
                params.put("password",password);
                params.put("full_name",full_name);
                params.put("phone_num",phone_num);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}