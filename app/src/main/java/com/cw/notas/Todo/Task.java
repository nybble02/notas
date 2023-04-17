package com.cw.notas.Todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Task {
    private String id;
    private String title;
    private String state;

    private long date;

    public Task() {}

    public Task(String id, String listId, String title, String state, long date) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.date = date;
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

    public void setDate(long date) {
        this.date = date;
    }
    public long getDate () {
        return this.date;
    }


    @Override
    public String toString() {

        return title;
    }
}