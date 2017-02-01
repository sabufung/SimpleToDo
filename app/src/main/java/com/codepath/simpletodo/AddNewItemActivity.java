package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private static final String TIME_PATTERN = "HH:mm";
    private EditText etName;
    private EditText etDeadline;
    private EditText etDescription;
    private BetterSpinner snPriority;
    private ImageButton imDeadline;

    private String[] PRIORITYLIST = {"Low", "Normal", "High"};

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        etName = (EditText) findViewById(R.id.etName);
        etDeadline = (EditText) findViewById(R.id.etDeadline);
        etDescription = (EditText) findViewById(R.id.etDescription);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PRIORITYLIST);
        snPriority = (BetterSpinner)
                findViewById(R.id.snPriority);
        snPriority.setAdapter(adapter);
        snPriority.setText("Normal");
//        etStatus = (EditText) findViewById(R.id.etStatus);
//        etPriority = (EditText) findViewById(R.id.etPriority);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,1);
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        etDeadline.setText(calendar.getTime().toString());
    }

    public void openDateTimePicker(View view) {
        switch (view.getId()) {
            case R.id.imDeadline:
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_task_menu, menu);
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        etDeadline.setText(calendar.getTime().toString());
    }

    public void onAddItem() {
        Intent data = new Intent();
        // Pass relevant data back as a result

        String itemName = etName.getText().toString();
        String itemDeadline = etDeadline.getText().toString();
        String itemDescription = etDescription.getText().toString();
        String itemPriority = snPriority.getText().toString();
//        Task newTask = new Task(itemName,new Date(),itemDescription,"",Integer.parseInt(itemStatus),Integer.parseInt(itemPriority));
        data.putExtra("name", itemName);
        data.putExtra("deadline", itemDeadline);
        data.putExtra("description", itemDescription);
        data.putExtra("priority", itemPriority);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
//         closes the activity and returns to first screen
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                // User chose the "Settings" item, show the app settings UI...
                onAddItem();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
