package com.example.leanjobs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserApplyQuestionnaireAdapter extends ArrayAdapter<QuestionAnswer> {

    private ArrayList<QuestionAnswer> items;
    private Context adapterContext;

    public UserApplyQuestionnaireAdapter(Context context, ArrayList<QuestionAnswer> items) {
        super(context, R.layout.activity_user_apply_questionnaire_listitem, items);
        adapterContext = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            QuestionAnswer questions = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.activity_user_apply_questionnaire_listitem, null);
            }

            TextView question = (TextView) v.findViewById(R.id.textQuestion);
            question.setText(questions.getquestiondesc());


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}