package com.example.leanjobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MyApplicationDetails extends AppCompatActivity {

    public int appid, jobid;
    public String jobtit,jobdesc,jobreq,wages,jobstat,appstat;;
    public TextView jtitle,jstat, jdes, jreq, jwag, ast;

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.homescreen, menu);
//        return true;
//        //return super.onCreateOptionsMenu(menu);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.home:
//                Intent i = new Intent(getApplicationContext(),User_HomeScreen.class);
//                startActivity(i);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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
