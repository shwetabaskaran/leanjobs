package com.example.leanjobs;

import android.app.Application;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

public class List_of_applicants extends ListActivity implements AsyncResponse3 {
    public int page = 0;
    ApplicantsAdapter adapter;
    public int jobid;
    public String jobti,jobsta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_applicants);

        Bundle extras = getIntent().getExtras();
        jobid = extras.getInt("jobid");
        jobti = extras.getString("jobtit");
        jobsta = extras.getString("jobsta");

        if(extras != null) {
            LongRunningGetIOO asyncTask =new LongRunningGetIOO();
            asyncTask.delegate1 = this;
            asyncTask.execute(jobid);
        }

    }

    @Override
    public void processFinished(final ArrayList<Applicant> applicants) {

        adapter = new ApplicantsAdapter(this, applicants);
        setListAdapter(adapter);


        if (applicants.size() > 0) {
            ListView listView = getListView();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    Applicant selectedApplicant  = applicants.get(position);
                    Intent intent = new Intent(List_of_applicants.this, Admin_applicant_details.class);
                    intent.putExtra("appid", selectedApplicant.getApplicantID());
                    intent.putExtra("appname", selectedApplicant.getApplicantName());
                    intent.putExtra("email",selectedApplicant.getApplicantemail());
                    intent.putExtra("phone",selectedApplicant.getApplicantPhone());
                    intent.putExtra("status",selectedApplicant.getApplicantStatus());
                    intent.putExtra("jobid",selectedApplicant.getJobID());
                    intent.putExtra("jobti",jobti);
                    intent.putExtra("jobsta", jobsta);
                    startActivity(intent);
                }
            });
        }
    }
}

class LongRunningGetIOO extends AsyncTask<Integer, Void, Wrapper> {

    public AsyncResponse3 delegate1 = null;

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
        String url = "http://dhillonds.com/leanjobsweb/index.php/api/jobs/list_applications?job_id="+job;
        HttpGet httpGet = new HttpGet(url);
        String text = null;
        Wrapper w = new Wrapper();
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
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
            ArrayList<Applicant> applicantlist = new ArrayList<Applicant>();

            Log.d("Appls",w.results);
            try {
                JSONObject jobj=new JSONObject(w.results);
                String c = jobj.getString("data");
                JSONArray applicants = new JSONArray(c);

                for(int i = 0; i<applicants.length(); i++) {
                    Applicant appl = new Applicant();
                    int appid = applicants.getJSONObject(i).getInt("app_id");
                    String appname = applicants.getJSONObject(i).getString("full_name");
                    String appemail = applicants.getJSONObject(i).getString("email");
                    String appphone = applicants.getJSONObject(i).getString("phone_num");
                    int astat = Integer.parseInt(applicants.getJSONObject(i).getString("job_status"));
                    if(astat == 0)
                        appl.setApplicantStatus("Applied");
                    else if(astat == 1)
                        appl.setApplicantStatus("Shortlisted");
                    else if(astat == 2)
                        appl.setApplicantStatus("Accepted");
                    else if(astat == 3)
                        appl.setApplicantStatus("Rejected");
                    appl.setApplicantID(appid);
                    appl.setApplicantName(appname);
                    appl.setApplicantemail(appemail);
                    appl.setApplicantPhone(appphone);
                    appl.setJobID(w.jobid);
                    applicantlist.add(appl);
                    }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            delegate1.processFinished(applicantlist);
        }
    }

}