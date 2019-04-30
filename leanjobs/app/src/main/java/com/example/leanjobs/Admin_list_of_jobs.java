package com.example.leanjobs;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.util.ArrayList;

public class Admin_list_of_jobs extends ListActivity implements AsyncResponseAdminJoblist {

    public int page = 0;
    AdminJobsAdapter adapter;


    @Override
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
                Intent i = new Intent(getApplicationContext(),Admin_HomeScreen.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_of_jobs);
        try{
        CreateJob();}
        catch (Exception ex)
        {
            Toast.makeText(getApplication(),ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private void CreateJob() {
        ImageButton CreateJob = (ImageButton) findViewById(R.id.createButton);
        CreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        createJob.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void onResume(){
        super.onResume();
        AsyncAdminjoblist asyncTask =new AsyncAdminjoblist();
        asyncTask.delegate = this;
        asyncTask.execute();
    }

    @Override
    public void processFinishAdminJobList(final ArrayList<Job> jobs) {

        adapter = new AdminJobsAdapter(this, jobs);
        setListAdapter(adapter);

        if (jobs.size() > 0) {
            ListView listView = getListView();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    Job selectedJob  = jobs.get(position);
                    Intent intent = new Intent(Admin_list_of_jobs.this, Admin_job_details.class);
                    intent.putExtra("jobid", selectedJob.getJobID());
                    startActivity(intent);
                }
            });
        }
    }

}

class AsyncAdminjoblist extends AsyncTask<Void, Void, String> {

    public AsyncResponseAdminJoblist delegate = null;
    Admin_list_of_jobs obj = new Admin_list_of_jobs();

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

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://dhillonds.com/leanjobsweb/index.php/api/jobs/list_admin?page="+obj.page;
        HttpGet httpGet = new HttpGet(url);
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
            ArrayList<Job> joblist = new ArrayList<Job>();

            try {
                JSONObject jobj=new JSONObject(results);
                String c = jobj.getString("data");
                JSONArray jobs = new JSONArray(c);

                for(int i = 0; i<jobs.length(); i++) {
                    Job joba = new Job();
                    int jobid = jobs.getJSONObject(i).getInt("job_id");
                    String jobtitle = jobs.getJSONObject(i).getString("title");
                    String jobdesc = jobs.getJSONObject(i).getString("role_desc");
                    String jobwages = jobs.getJSONObject(i).getString("wages");
                    joba.setJobID(jobid);
                    joba.setJobTitle(jobtitle);
                    joba.setJobRoleDesc(jobdesc);
                    joba.setJobWages(jobwages);
                    joblist.add(joba);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            delegate.processFinishAdminJobList(joblist);
        }
    }

}