package com.codepath.simpletodo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.Model.Task;
import com.codepath.simpletodo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Asus on 1/31/2017.
 */

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDeadline = (TextView) convertView.findViewById(R.id.tvDeadline);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        // Populate the data into the template view using the data object
        tvName.setText(task.name);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String deadline = formatter.format(task.deadline);
        tvDeadline.setText(deadline);
        tvPriority.setText(task.priority);
        switch (task.priority){
            case "Low":
                tvPriority.setTextColor(Color.GREEN);
                break;
            case "Normal":
                tvPriority.setTextColor(Color.YELLOW);
                break;
            case "High":
                tvPriority.setTextColor(Color.RED);
                break;
            default:
                break;
        }
        // Return the completed view to render on screen
        return convertView;
    }


}
