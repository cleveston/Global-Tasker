package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucasfronza on 10/12/15.
 */
public class SubtaskAdapter extends ArrayAdapter<SubtaskItem> {
    public SubtaskAdapter(Context context, ArrayList<SubtaskItem> subtask) {
        super(context, 0, subtask);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SubtaskItem subtask = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_subtask, parent, false);
        }
        // Lookup view for data population
        TextView idsubtask = (TextView) convertView.findViewById(R.id.idsubtask);
        TextView login = (TextView) convertView.findViewById(R.id.login);
        TextView score = (TextView) convertView.findViewById(R.id.score);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        // Populate the data into the template view using the data object
        idsubtask.setText("Task ID: " + subtask.getIdsubtask().toString());
        login.setText("User: " + subtask.getLogin());
        score.setText("Score: " + subtask.getScore().toString());
        if (subtask.getType().equals("a")) {
            type.setText("Type: Array Sorting");
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
