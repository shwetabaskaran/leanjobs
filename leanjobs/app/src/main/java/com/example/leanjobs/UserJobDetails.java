package com.example.leanjobs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.Map;

public class UserJobDetails extends AppCompatActivity implements AsyncResponse2{

    TextView jobtitle,jobroledesc,jobreqs,jobwages;
    Button apply;
    public int jobID;
    public int page = 0;
    public int user_id;
    User user = new User();
    UserJob ub = new UserJob();
    String UserID, Salt;
    int JobID;

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
                Intent i = new Intent(getApplicationContext(),User_HomeScreen.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job_details);
        jobtitle = (TextView) findViewById(R.id.JobTitle);
        jobroledesc = (TextView) findViewById(R.id.JobRoleDesc);
        jobreqs = (TextView) findViewById(R.id.JobReqts);
        jobwages = (TextView) findViewById(R.id.JobWages);

        user = (User) getIntent().getParcelableExtra("Userdetails");
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Async asyncTask2 = new Async();
            asyncTask2.delegate = (AsyncResponse2) this;
            ub.setJobID(extras.getInt("jobid"));
            ub.setUserID(extras.getString("userid"));
            asyncTask2.execute(ub);
            JobID = extras.getInt("jobid");
            UserID = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("User_user_id","");
            Salt = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("UserSalt","");

        }
    }

    private void PostSignUpData(final int Jobid, final String UserID,final String Salt) {
        String URLPost = "http://dhillonds.com/leanjobsweb/index.php/api/jobs/apply_job";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLPost, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject UserCredentials = new JSONObject(response);
                    String LoginFlag = UserCredentials.getString("status");
                    String Message = UserCredentials.getString("message");
                    Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                    if(LoginFlag == "true"){
                        JSONObject Data = UserCredentials.getJSONObject("data");
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
                Toast.makeText(UserJobDetails.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("job_id",String.valueOf(Jobid));
                params.put("user_id",UserID);
                params.put("salt",String.valueOf(Salt));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void processFinish2(final Job job) {
        final int jobid = job.getJobID();
        jobtitle.setText(job.getjobTitle());
        jobroledesc.setText(job.getJobRoleDesc());
        jobreqs.setText(job.getjobReqs());
        jobwages.setText(job.getJobWages());

        apply = (Button) findViewById(R.id.Applybutton);
        apply.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                PostSignUpData(JobID,UserID,Salt);
            }
        });
    }
}

class Async extends AsyncTask<UserJob, Void, Wrapper> {

    public AsyncResponse2 delegate = null;
    UserJobDetails obj = new UserJobDetails();

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
    protected Wrapper doInBackground(UserJob... userjob) {
        int jobid = userjob[0].getJobID();
        String userid = userjob[0].getUserID();

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://dhillonds.com/leanjobsweb/index.php/api/jobs/list_user?page=" + obj.page + "&user_id=" + userid;
        HttpGet httpGet = new HttpGet(url);
        String text = null;
        Wrapper w = new Wrapper();
        try {

            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);

            w.results = text;
            w.jobid = jobid;
        } catch (Exception e) {
            w.results = e.getLocalizedMessage();
            return w;
        }
        w.results = text;
        w.jobid = jobid;
        return w;
    }

    protected void onPostExecute(Wrapper w) {
        if (w.results != null) {
            ArrayList<Job> joblist = new ArrayList<Job>();
            Job joba = new Job();

            try {
                JSONObject jobj = new JSONObject(w.results);
                String c = jobj.getString("data");
                JSONArray jobs = new JSONArray(c);

                for (int i = 0; i < jobs.length(); i++) {
                    int jobid = jobs.getJSONObject(i).getInt("job_id");
                    if (jobid == w.jobid) {
                        String jobtitle = jobs.getJSONObject(i).getString("title");
                        String jobdesc = jobs.getJSONObject(i).getString("role_desc");
                        String jobreqs = jobs.getJSONObject(i).getString("job_reqs");
                        String jobwages = jobs.getJSONObject(i).getString("wages");

                        joba.setJobID(jobid);
                        joba.setJobTitle(jobtitle);
                        joba.setJobRoleDesc(jobdesc);
                        joba.setJobReqs(jobreqs);
                        joba.setJobWages(jobwages);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            delegate.processFinish2(joba);
        }
    }
}