package com.example.leanjobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MyApplicationDetails extends AppCompatActivity {

    public int appid, jobid;
    public String jobtit,jobdesc,jobreq,wages,jobstat,appstat;;
    public TextView jtitle,jstat, jdes, jreq, jwag, ast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_application_details);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            jobid = extras.getInt("jobid");
            appid = extras.getInt("appid");
            jobtit = extras.getString("jobtitle");
            jobdesc = extras.getString("jobdesc");
            jobreq = extras.getString("jobreq");
            wages = extras.getString("wages");
            jobstat = extras.getString("jobstat");
            appstat = extras.getString("appstatus");

            jtitle = (TextView) findViewById(R.id.JobTitle);
            jstat = (TextView) findViewById(R.id.JobStatus);
            jdes = (TextView) findViewById(R.id.JobRoleDesc);
            jreq = (TextView) findViewById(R.id.JobReqts);
            jwag = (TextView) findViewById(R.id.JobWages);
            ast = (TextView) findViewById(R.id.astatus);

            jtitle.setText(jobtit);
            jstat.setText(jobstat);
            jdes.setText(jobdesc);
            jreq.setText(jobreq);
            jwag.setText(wages);
            ast.setText(appstat);

        }
    }
}
