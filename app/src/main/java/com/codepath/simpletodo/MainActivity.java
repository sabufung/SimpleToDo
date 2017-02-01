package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.simpletodo.Adapter.TaskAdapter;
import com.codepath.simpletodo.Fragment.EditItemFragment;
import com.codepath.simpletodo.Model.Task;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements EditItemFragment.OnFragmentInteractionListener {
    ArrayList<Task> items;
    TaskAdapter itemsAdapter;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        // Construct the data source
        items = new ArrayList<Task>();
        // Create the adapter to convert the array to views
        itemsAdapter = new TaskAdapter(this, items);
        // Attach the adapter to a ListView
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();


    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                showEditDialog(items.get(pos), pos);
            }
        });

    }

    private void readItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            ArrayList<String> list = FileUtils.readLines(todoFile);
//            items = new ArrayList<Task>();
//        } catch (IOException e) {
//            items = new ArrayList<>();
//        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0) {
            String name = data.getExtras().getString("name");
            String deadline = data.getExtras().getString("deadline");
            String description = data.getExtras().getString("description");
            String priority = data.getExtras().getString("priority");
            Task newTask = new Task(name, new Date(deadline), description, "", priority, 0);
            items.add(newTask);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void showEditDialog(Task item, int pos) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemFragment editNameDialogFragment = EditItemFragment.newInstance(item,pos);
        editNameDialogFragment.show(fm, "fragment_edit_item");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_new_task:
                Intent i = new Intent(MainActivity.this, AddNewItemActivity.class);
                startActivityForResult(i, 0);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onFragmentInteraction(Task item, int pos) {
        items.remove(pos);
        items.add(pos,item);
        itemsAdapter.notifyDataSetChanged();
    }



}
