package com.example.leanjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserJobsAdapter extends ArrayAdapter<Job> {

    private ArrayList<Job> items;
    private Context adapterContext;

    public UserJobsAdapter(Context context, ArrayList<Job> items) {
        super(context, R.layout.activity_user_list_of_jobs_listitem, items);
        adapterContext = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            Job job = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.activity_user_list_of_jobs_listitem, null);
            }

            TextView jobTitle = (TextView) v.findViewById(R.id.textUserJobListJobTitle);
            jobTitle.setText(job.getjobTitle());
            TextView jobDesc = (TextView) v.findViewById(R.id.UserJobListDesc);
            jobDesc.setText(job.getJobRoleDesc());

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}
