package com.cw.notas.Checklist;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

/** Represents a checkbox object in a checklist. */
public class Checkbox {
    private String id;
    private String listId;
    private String title;
    private String state;
    public Checkbox() {}

    /**
     Creates a new Checkbox object with the specified values.
     @param id the ID of the checkbox
     @param listId the ID of the checklist that contains the checkbox
     @param title the title of the checkbox
     @param state the state of the checkbox (e.g. "completed" or "not completed")
     */

    public Checkbox(String id, String listId, String title, String state, String dueDate) {
        this.id = id;
        this.listId = listId;
        this.title = title;
        this.state = state;
    }

    /**
     Sets the ID of the checkbox.
     @param id the ID of the checkbox
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     Returns the ID of the checkbox.
     @return the ID of the checkbox
     */
    public String getId() {
        return this.id;
    }

    /**
     Sets the ID of the checklist that contains the checkbox.
     @param listId the ID of the checklist that contains the checkbox
     */
    public void setListId(String listId) {
        this.listId = listId;
    }

    /**
     Returns the ID of the checklist that contains the checkbox.
     @return the ID of the checklist that contains the checkbox
     */
    public String getListId() {
        return this.listId;
    }

    /**
     Sets the title of the checkbox.
     @param title the title of the checkbox
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     Returns the title of the checkbox.
     @return the title of the checkbox
     */
    public String getTitle() {
        return this.title;
    }

    /**
     Sets the state of the checkbox.
     @param state the state of the checkbox (e.g. "Checked" or "not checked")
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     Returns the state of the checkbox.
     @return the state of the checkbox (e.g. "Checked" or "not checked")
     */
    public String getState() {
        return this.state;
    }
    @Override
    public String toString() {
        return title;
    }
}
