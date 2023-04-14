package com.cw.notas.Checklist;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

public class Checkbox {
    private String id;
    private String listId;
    private String title;
    private String state;

    private String dueDate;

    public Checkbox() {}

    public Checkbox(String id, String listId, String title, String state, String dueDate) {
        this.id = id;
        this.listId = listId;
        this.title = title;
        this.state = state;
        this.dueDate = dueDate;
    }


    public void setId(String id) {this.id = id;
    }
    public String getId () {
        return this.id;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getListId () {
        return this.listId;
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

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate () {
        return this.dueDate;
    }


    @Override
    public String toString() {
        return title;
    }
}
