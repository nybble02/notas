package com.cw.notas.Todos;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

public class Task {
    private String id;
    private String title;
    private String state;

    private String dueDate;

    public Task() {}

    public Task(String id, String listId, String title, String state, String dueDate) {
        this.id = id;
        this.title = title;
        this.state = state;
    }

    public void setId(String id) {this.id = id;
    }
    public String getId () {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle () {
        return this.title;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState () {
        return this.state;
    }


    @Override
    public String toString() {
        return title;
    }
}
