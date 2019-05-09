package com.example.leanjobs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class Admin_job_details extends AppCompatActivity implements AsyncResponseAdminJobdetails{

    TextView jobtitle,jobroledesc,jobreqs,jobwages,jobstatus;
    Button changestatus;
    public int jobid;
    String jobti, jobsta;
    //    Spinner dropdown;
    public int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job_details);
        setContentView(R.layout.activity_admin_job_details);

        jobtitle = (TextView) findViewById(R.id.JobTitle);
        jobstatus = (TextView) findViewById (R.id.AdminJobStatus);
        jobroledesc = (TextView) findViewById(R.id.JobRoleDesc);
        jobreqs = (TextView) findViewById(R.id.JobReqts);
        jobwages = (TextView) findViewById(R.id.JobWages);
        changestatus = (Button) findViewById(R.id.AdminChangeRequisition);

//        dropdown = findViewById(R.id.spinnerJobStatus);
//        String[] items = new String[]{"Active", "Inactive"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        dropdown.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        jobid = extras.getInt("jobid");
        if(extras != null) {
            AsyncAdminJobDetails asyncTaskAdminJobDetails = new AsyncAdminJobDetails();
            asyncTaskAdminJobDetails.delegate = (AsyncResponseAdminJobdetails) this;
            asyncTaskAdminJobDetails.execute(extras.getInt("jobid"));
            int jobid = extras.getInt("jobid");
            getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).edit() .putString("job_id",String.valueOf(jobid)).commit();


        }
        Button job = (Button) findViewById(R.id.AdminViewAppl);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        List_of_applicants.class);
                i.putExtra("jobid", jobid);
                i.putExtra("jobtit",jobti);
                i.putExtra("jobsta", jobsta);
                startActivity(i);
            }
        });



        changestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostStatusChangeRequest();
            }
        });
    }


    private void PostStatusChangeRequest() {
        String URLPost = "http://dhillonds.com/leanjobsweb/index.php/api/jobs/change_requisition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLPost, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject UserCredentials = new JSONObject(response);
                    String StatusFlag = UserCredentials.getString("status");
                    String Message = UserCredentials.getString("message");
                    if(StatusFlag == "true"){
                        JSONObject Data = UserCredentials.getJSONObject("data");
                        String Updated_Status = Data.getString("updated_status");
                        if(Updated_Status.equals("1")){
                            jobstatus.setText("Activo");
                            changestatus.setText("Cerrar Solicitud");
                        }
                        else if(Updated_Status.equals("0")){
                            jobstatus.setText("Inactivo");
                            changestatus.setText("Re-Abrir Solicitud");
                        }
                    }
                    else if(StatusFlag == "false"){
                        Toast.makeText(getApplication(),"StatusFlag"+Message,Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplication(),ex.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Admin_job_details.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String user_id = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("user_id","");
                String salt = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("salt","");
                String jobid = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("job_id","");
                params.put("user_id",user_id);
                params.put("salt",salt);
                params.put("job_id",jobid.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void processFinishAdminJobDetails(final Job job) {
        int jobid = job.getJobID();
        jobtitle.setText(job.getjobTitle());
        jobti = job.getjobTitle();
        if(job.getJobIsActive()==1) {
            jobstatus.setText("Activo");
            changestatus.setText("Cerrar Solicitud");
            jobsta = "Active";
        }
        else if(job.getJobIsActive()==0) {
            jobstatus.setText("Inactivo");
            changestatus.setText("Re-Obrir Solicitud");
            jobsta = "Inactive";
        }

        jobroledesc.setText(job.getJobRoleDesc());
        jobreqs.setText(job.getjobReqs());
        jobwages.setText(job.getJobWages());
    }
}

class AsyncAdminJobDetails extends AsyncTask<Integer, Void, Wrapper> {

    public AsyncResponseAdminJobdetails delegate = null;
    Admin_job_details obj = new Admin_job_details();

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
    protected Wrapper doInBackground(Integer... jobid) {
        int job = jobid[0];
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://dhillonds.com/leanjobsweb/index.php/api/jobs/list_admin?page=" + obj.page;
        HttpGet httpGet = new HttpGet(url);
        String text = null;
        Wrapper w = new Wrapper();
        try {

            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);

            w.results = text;
            w.jobid = job;
        } catch (Exception e) {
            w.results = e.getLocalizedMessage();
            return w;
        }
        w.results = text;
        w.jobid = job;
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
                        String jobstatus = jobs.getJSONObject(i).getString("is_active");
                        String jobdesc = jobs.getJSONObject(i).getString("role_desc");
                        String jobreqs = jobs.getJSONObject(i).getString("job_reqs");
                        String jobwages = jobs.getJSONObject(i).getString("wages");
                        joba.setJobID(jobid);
                        joba.setJobTitle(jobtitle);
                        joba.setJobIsActive(Integer.parseInt(jobstatus));
                        joba.setJobRoleDesc(jobdesc);
                        joba.setJobReqs(jobreqs);
                        joba.setJobWages(jobwages);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            delegate.processFinishAdminJobDetails(joba);
        }
    }
}