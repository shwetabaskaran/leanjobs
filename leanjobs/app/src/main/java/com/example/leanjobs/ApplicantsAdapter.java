package com.example.leanjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ApplicantsAdapter extends ArrayAdapter<Applicant> {

    private ArrayList<Applicant> items;
    private Context adapterContext;

    public ApplicantsAdapter(Context context, ArrayList<Applicant> items) {
        super(context, R.layout.activity_list_of_applicants_item, items);
        adapterContext = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            Applicant applicant = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.activity_list_of_applicants_item, null);
            }

            TextView applicantName = (TextView) v.findViewById(R.id.applicantname);
            applicantName.setText(applicant.getApplicantName());


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}
