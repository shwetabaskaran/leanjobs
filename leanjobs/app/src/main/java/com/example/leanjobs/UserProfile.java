package com.example.leanjobs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

//import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;






public class UserProfile extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private byte[] PDFbytesArray;
    private ImageView ProfilePic;
    String ResumePath;
    TextView PdfNameEditText ;
    String FullName,PhoneNo,Salt,UserId,Email,ProfilePicURL;
    final int PERMISSION_REQUEST_CAMERA = 103;
    Button btnSave,UploadPDF;
    EditText fullName,emailAddress,mobileNumber;
    Button ViewResume;
    Uri uri;
    public int PDF_REQ_CODE = 1;
    private Bitmap scaledPhoto;
    File file;String filename;
    String PdfNameHolder, PdfPathHolder, PdfID;
    String URLPost="http://dhillonds.com/leanjobsweb/index.php/api/users/update_profile";
//For Home button

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homescreen, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(getApplicationContext(),
                        "Home button", Toast.LENGTH_SHORT)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        btnSave = findViewById(R.id.userprofSave);
        fullName = findViewById(R.id.edituserFullName);
        emailAddress = findViewById(R.id.edituserEmail);
        mobileNumber = findViewById(R.id.edituserMobile);
        PdfNameEditText = findViewById(R.id.txtPdfName);
        ViewResume = findViewById(R.id.btnViewResume);
        this.ProfilePic = (ImageView)this.findViewById(R.id.userprofilePic);


        Intent i = getIntent();
        //ProfilePicURL = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("profilePicURL","");

        ProfilePicURL = i.getStringExtra("User_ProfileURL");
        ResumePath = i.getStringExtra("User_ResumePath");
        if(i.hasExtra("User_User_id"))
        {
            FullName = i.getStringExtra("User_FullName");
            PhoneNo = i.getStringExtra("User_PhoneNo");
            Email = i.getStringExtra("User_Email");
            Salt = i.getStringExtra("User_Salt");
            UserId = i.getStringExtra("User_User_id");

            fullName.setText(FullName);
            mobileNumber.setText(PhoneNo);
            emailAddress.setText(Email);
        }
        else if(i.hasExtra("Admin_User_id")){
            FullName = i.getStringExtra("Admin_FullName");
            PhoneNo = i.getStringExtra("Admin_PhoneNo");
            Email = i.getStringExtra("Admin_Email");
            Salt = i.getStringExtra("Admin_Salt");
            UserId = i.getStringExtra("Admin_User_id");
            fullName.setText(FullName);
            mobileNumber.setText(PhoneNo);
            emailAddress.setText(Email);
        }
        CheckProfilePicture();
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

        ViewResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try{ String URL=ResumePath;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(browserIntent);}
                catch (Exception ex){
                    Toast.makeText(UserProfile.this, "No Resume Available", Toast.LENGTH_LONG).show();
                }
            }
        });

        UploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);


            }
        });
    }

    private void CheckProfilePicture() {
        try{
        String CheckURL = ProfilePicURL;
        ImageRequest request = new ImageRequest(CheckURL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ProfilePic.setImageBitmap(bitmap);
                    }
                }, 144, 144, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                    }

                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);}
        catch (Exception ex){
            Toast.makeText(UserProfile.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

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
        else  if(requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            File file = new File(uri.getPath());

            try {
                InputStream is = this.getContentResolver().openInputStream(uri);
                PDFbytesArray = new byte[is.available()];
                is.read(PDFbytesArray);
            /*
            File myPdf=new File(Environment.getExternalStorageDirectory(), "myPdf.pdf");
            FileOutputStream fos=new FileOutputStream(myPdf.getPath());
            fos.write(bytesArray);
            fos.close();*/


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
        //StringRequest stringRequest = new StringRequest(Request.Method.POST,URLPost, new Response.Listener<String>(){
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLPost, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);

                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");
                    Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("full_name", fullName.getText().toString());
                params.put("phone_num", mobileNumber.getText().toString());
                params.put("salt", Salt);
                params.put("user_id", UserId);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if(PDFbytesArray != null){
                params.put("picture_file", new DataPart(FullName + " ProfilePic.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), ProfilePic.getDrawable()), "image/jpeg"));
                params.put("resume_file", new DataPart(FullName+" Resume.pdf", PDFbytesArray, "pdf"));
                }return params;}
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }
}
