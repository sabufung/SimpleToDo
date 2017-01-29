package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    Button btnSave;
    EditText etItem;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String username = getIntent().getStringExtra("item");
        pos = getIntent().getIntExtra("position",0);
        etItem = (EditText) findViewById(R.id.etItem);
        etItem.setText(username);
    }

    public void onSave(View v) {
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("item", etItem.getText().toString());
        data.putExtra("position", pos);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        // closes the activity and returns to first screen
        this.finish();
    }
}
