package com.example.leanjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminJobsAdapter extends ArrayAdapter<Job> {

    private ArrayList<Job> items;
    private Context adapterContextAdmin;

    public AdminJobsAdapter(Context context, ArrayList<Job> items) {
        super(context, R.layout.activity_admin_list_of_jobs_listitem, items);
        adapterContextAdmin = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            Job job = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContextAdmin.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.activity_admin_list_of_jobs_listitem, null);
            }

            TextView jobTitle = (TextView) v.findViewById(R.id.textAdminJobListJobTitle);
            jobTitle.setText(job.getjobTitle());
            TextView jobDesc = (TextView) v.findViewById(R.id.AdminJobListDesc);
            jobDesc.setText(job.getJobRoleDesc());
            TextView jobWages = (TextView) v.findViewById(R.id.AdminJobListWages);
            jobWages.setText("Wages: "+job.getJobWages());

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}
