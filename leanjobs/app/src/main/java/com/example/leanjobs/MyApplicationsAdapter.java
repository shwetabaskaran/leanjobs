package com.example.leanjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyApplicationsAdapter extends ArrayAdapter<Appln> {
    private ArrayList<Appln> items;
    private Context adapterContext;

    public MyApplicationsAdapter(Context context, ArrayList<Appln> items) {
        super(context, R.layout.activity_view_my_applications_list_item, items);
        adapterContext = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            Appln apps = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.activity_view_my_applications_list_item, null);
            }

            TextView jtitle = (TextView) v.findViewById(R.id.textApplListJobTitle);
            jtitle.setText(apps.getJobTitle());

            TextView jstatus = (TextView) v.findViewById(R.id.ApplListJobStatus);
            jstatus.setText(apps.getJobStatus());

            TextView astatus = (TextView) v.findViewById(R.id.ApplListApplStatus);
            astatus.setText(apps.getApplicationStatus());


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}
