package com.cw.notas;

import java.util.Calendar;
import java.util.List;

public class Checkbox {
    private String id;
    private String listId;
    private String title;
    private Boolean state;
//    private Calendar dueDate;

    public Checkbox() {}

    public Checkbox(String id, String listId, String title, Boolean state) {
        this.id = id;
        this.listId = listId;
        this.title = title;
        this.state = state;
        //this.dueDate = dueDate;
    }


    public void setId(String id) {
        this.id = id;
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

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getState () {
        return this.state;
    }



}
