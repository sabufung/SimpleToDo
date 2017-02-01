package com.codepath.simpletodo.Model;

import java.util.Date;

/**
 * Created by Asus on 1/31/2017.
 */

public class Task {
    public String name;
    public Date deadline;
    public String description;
    public String imageUrl;
    public String priority;
    public int status;

    public Task(String name, Date deadline, String description, String imageUrl, String priority, int status) {
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.imageUrl = imageUrl;
        this.priority = priority;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
