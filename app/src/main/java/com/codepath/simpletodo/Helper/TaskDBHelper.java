package com.codepath.simpletodo.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codepath.simpletodo.Enum.StatusEnum;
import com.codepath.simpletodo.Model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BuuPV on 2/5/2017.
 */

public class TaskDBHelper {
    public static void saveEntityToDB(Task task, Context context) {
        TaskDatabase dbHelper = new TaskDatabase(context);
        SQLiteDatabase writeDB = dbHelper.getWritableDatabase();
        ContentValues newTask = new ContentValues();
        newTask.put("taskname", task.getName());
        newTask.put("description", task.getDescription());
        newTask.put("deadline", task.getDeadline().getTime());
        newTask.put("priority", task.getPriority());
        newTask.put("status", StatusEnum.ONGOING.getValue());
        writeDB.insert("tblTask", null, newTask);
    }

    public static List<Task> readAll(Context context) {
        TaskDatabase dbHelper = new TaskDatabase(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "taskname",
                "description",
                "deadline",
                "status",
                "priority"
        };
        String selection = "";

        String[] selectionArgs = {};

        Cursor cursor = readDB.query(
                "tblTask",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        List items = new ArrayList<Task>();
        cursor.getColumnNames();
        while (cursor.moveToNext()) {
            int taskId = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id"));
            String taskName = cursor.getString(
                    cursor.getColumnIndexOrThrow("taskname"));
            String taskDescription = cursor.getString(
                    cursor.getColumnIndexOrThrow("description"));
            Date deadline = new Date(cursor.getLong(
                    cursor.getColumnIndexOrThrow("deadline")));
            String priority = cursor.getString(
                    cursor.getColumnIndexOrThrow("priority"));
            int status = cursor.getInt(
                    cursor.getColumnIndexOrThrow("status"));
            Task task = new Task(taskName,deadline,taskDescription,priority,status);
            task.setId(taskId);
            items.add(task);
        }
        cursor.close();
        return items;
    }

    public static Task findTaskById(Context context, int taskId) {
        TaskDatabase dbHelper = new TaskDatabase(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "taskname",
                "description",
                "deadline",
                "status",
                "priority"
        };
        String selection = "id = ?";

        String[] selectionArgs = {"" + taskId};

        Cursor cursor = readDB.query(
                "tblTask",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        List items = new ArrayList<Task>();
        cursor.getColumnNames();
        if (cursor.moveToNext()) {
            String taskName = cursor.getString(
                    cursor.getColumnIndexOrThrow("taskname"));
            String taskDescription = cursor.getString(
                    cursor.getColumnIndexOrThrow("description"));
            Date deadline = new Date(cursor.getLong(
                    cursor.getColumnIndexOrThrow("deadline")));
            String priority = cursor.getString(
                    cursor.getColumnIndexOrThrow("priority"));
            int status = cursor.getInt(
                    cursor.getColumnIndexOrThrow("status"));
            Task task = new Task(taskName,deadline,taskDescription,priority,status);
            task.setId(taskId);
            items.add(task);
            return task;
        }
        cursor.close();
        return null;
    }

    public static void updateEntityToDB(Task task, Context context) {
        TaskDatabase dbHelper = new TaskDatabase(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        ContentValues newTask = new ContentValues();
        newTask.put("taskname", task.getName());
        newTask.put("description", task.getDescription());
        newTask.put("deadline", task.getDeadline().getTime());
        newTask.put("priority", task.getPriority());
        newTask.put("status", task.getStatus());

        String selection = "id = ?";
        String[] selectionArgs = {"" + task.getId()};

        int count = readDB.update(
                "tblTask",
                newTask,
                selection,
                selectionArgs);
    }
}
