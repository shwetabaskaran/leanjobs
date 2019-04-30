package com.example.leanjobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Admin_applicant_details extends AppCompatActivity {

    public int appid, jobid;
    public String appname,appemail,appphone,appstatus,jobtit,jobstat;
    TextView name,email,phone,status, jobtitle,jst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_applicant_details);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            appid = extras.getInt("appid");
            appname = extras.getString("appname");
            appemail = extras.getString("email");
            appphone = extras.getString("phone");
            appstatus = extras.getString("status");
            jobid = extras.getInt("jobid");
            jobtit = extras.getString("jobti");
            jobstat = extras.getString("jobsta");
        }

        name = (TextView) findViewById(R.id.applname);
        email = (TextView) findViewById(R.id.emailid);
        phone = (TextView) findViewById(R.id.phone);
        status = (TextView) findViewById(R.id.status);
        jobtitle = (TextView) findViewById(R.id.JobTitle);
        jst = (TextView) findViewById(R.id.AdminJobStatus);
        name.setText(appname);
        email.setText(appemail);
        phone.setText(appphone);
        status.setText(appstatus);
        jobtitle.setText(jobtit);
        jst.setText(jobstat);



    }
}
