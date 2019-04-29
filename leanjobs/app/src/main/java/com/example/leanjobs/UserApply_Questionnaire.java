package com.example.leanjobs;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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

public class UserApply_Questionnaire extends ListActivity implements AsyncQuest {

    Button submit;
    public int userid;
    public int page = 0;
    UserApplyQuestionnaireAdapter adapter;
    ListView listView;
    UserJob ub = new UserJob();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_apply_questionnaire);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            AsyncQuestion asyncTask = new AsyncQuestion();
            asyncTask.delegate = (AsyncQuest) this;
            ub.setJobID(extras.getInt("jobid"));
            ub.setUserID(extras.getString("userid"));
            asyncTask.execute(ub);
        }
    }

    @Override
    public void processFinishQuestionnaire(final ArrayList<QuestionAnswer> questions){

        adapter = new UserApplyQuestionnaireAdapter(this, questions);
        setListAdapter((ListAdapter) adapter);

        submit = (Button) findViewById(R.id.questionsubmit);
        submit.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                ListView lvName = (ListView) findViewById(android.R.id.list);
                View view;
                EditText et;

                int listLength = lvName.getAdapter().getCount();
                String[] answers = new String[listLength];
                for (int i = 0; i < listLength; i++)
                {
                    view = lvName.getChildAt(i);
                    et = (EditText) view.findViewById(R.id.Answer);
                    String ans = et.getText().toString();
                    if(ans.isEmpty())
                        answers[i] = "Not answered";
                    else
                        answers[i] = ans;
                }

                PostApplyJob(questions,answers,ub.getJobID(),ub.getUserID());
//                Intent intent = new Intent(getApplicationContext(),ApplicationForm.class);
//                intent.putExtra("questions", questions);
//                intent.putExtra("answers", answers);
//                intent.putExtra("userid",ub.getUserID());
//                intent.putExtra("jobid",ub.getJobID());
//
//                startActivity(intent);
//                finish();
            }
        });
    }

    private void PostApplyJob(ArrayList<QuestionAnswer> questions,String answers[], int jobid, String userid)
    {
        String URLPost = "http://dhillonds.com/leanjobsweb/index.php/api/jobs/apply_job";
    }



}
    class AsyncQuestion extends AsyncTask<UserJob, Void, Wrapper> {

        public AsyncQuest delegate = null;
        UserApply_Questionnaire obj = new UserApply_Questionnaire();

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
        protected Wrapper doInBackground(UserJob...userjob) {

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
                ArrayList<QuestionAnswer> questionlist = new ArrayList<QuestionAnswer>();

                try {
                    JSONObject qobj = new JSONObject(w.results);
                    String c = qobj.getString("data");
                    JSONArray quests = new JSONArray(c);

                    for (int i = 0; i < quests.length(); i++) {

                        int jobid = quests.getJSONObject(i).getInt("job_id");
                        if (jobid == w.jobid) {
                            JSONArray QuestionArray = quests.getJSONObject(i).getJSONArray("questions");
                            for (int j = 0; j < QuestionArray.length(); j++) {
                                QuestionAnswer questions = new QuestionAnswer();
                                int questionid = Integer.parseInt(QuestionArray.getJSONObject(j).getString("ques_id"));
                                String questiondesc = QuestionArray.getJSONObject(j).getString("question_desc");
                                Log.d("quest",questiondesc);
                                questions.setjobID(jobid);
                                questions.setquestionID(questionid);
                                questions.setquestiondesc(questiondesc);
                                questionlist.add(questions);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                delegate.processFinishQuestionnaire(questionlist);
            }
        }
    }
