package com.example.leanjobs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView ProfilePic;
    String FullName,PhoneNo,Salt,UserId,Email;
    final int PERMISSION_REQUEST_CAMERA = 103;
    Button btnSave,UploadPDF;
    EditText fullName,emailAddress,mobileNumber;
    String imageString;
    private Bitmap scaledPhoto;
    String URLPost="http://dhillonds.com/leanjobsweb/index.php/api/users/update_profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        btnSave = findViewById(R.id.userprofSave);
        fullName = findViewById(R.id.edituserFullName);
        emailAddress = findViewById(R.id.edituserEmail);
        mobileNumber = findViewById(R.id.edituserMobile);
        this.ProfilePic = (ImageView)this.findViewById(R.id.userprofilePic);
        Intent i = getIntent();
        if(i.hasExtra("User_User_id"))
        {
            FullName = i.getStringExtra("User_FullName");
            PhoneNo = i.getStringExtra("User_PhoneNo");
            Email = i.getStringExtra("User_Email");
            Salt = i.getStringExtra("User_Salt");
            fullName.setText(FullName);
            mobileNumber.setText(PhoneNo);
            emailAddress.setText(Email);
        }
        else if(i.hasExtra("Admin_User_id")){
            FullName = i.getStringExtra("Admin_FullName");
            PhoneNo = i.getStringExtra("Admin_PhoneNo");
            Email = i.getStringExtra("Admin_Email");
            Salt = i.getStringExtra("Admin_Salt");
            fullName.setText(FullName);
            mobileNumber.setText(PhoneNo);
            emailAddress.setText(Email);
        }

//        fullName.setText(getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("full_name",""));
//        mobileNumber.setText(getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("phone_num",""));
//        emailAddress.setText(getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("email",""));

        emailAddress.setEnabled(false);
        UploadPDF = (Button) findViewById(R.id.userprofileUploadResume);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initImageButton();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PostSignUpData();
                } catch (Exception ex) {
                }
            }
        });

        UploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void initImageButton() {
        ImageButton ib = (ImageButton) findViewById(R.id.camera);
        ib.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(UserProfile.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(UserProfile.this, android.Manifest.permission.CAMERA)) {
                            Snackbar.make(findViewById(R.id.activity_signup), "The app needs permission to take pictures.", Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    ActivityCompat.requestPermissions(UserProfile.this, new String[]{ android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                                }
                            }).show();
                        } else {
                            ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                        }
                    }
                    else {
                        takePhoto();
                    }
                } else {
                    takePhoto();
                }
            }
        });
    }
    public void takePhoto(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    scaledPhoto = Bitmap.createScaledBitmap(photo, 144, 144, true);
                    ProfilePic.setImageBitmap(scaledPhoto);
                    ImageToString(scaledPhoto);
                }
                catch (Exception ex){
                    Toast.makeText(UserProfile.this, ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private String ImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Toast.makeText(UserProfile.this, "You will not be able to save contact pictures from this app", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void PostSignUpData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLPost, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("phone_num", mobileNumber.getText().toString()).commit();
                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("full_name", fullName.getText().toString()).commit();
                    JSONObject UserProfileData = new JSONObject(response);
                    String LoginFlag = UserProfileData.getString("status");
                    String Message = UserProfileData.getString("message");
                    Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                    if(LoginFlag == "true"){
                        Intent intent = new Intent(getApplicationContext(),
                                UserProfile.class);
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
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserProfile.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String User_Id = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("user_id","");
                params.put("full_name",FullName);
                params.put("phone_num",PhoneNo);
                params.put("salt",Salt);
                params.put("user_id",User_Id);
                if(scaledPhoto != null){
                params.put("picture_file",ImageToString(scaledPhoto));}
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
