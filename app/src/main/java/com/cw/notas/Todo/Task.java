package com.cw.notas.Todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * The Task class represents a task in a to do list or a project.
 */
public class Task {
    private String id;
    private String title;
    private String state;

    /**
     * Constructs a new Task object with default values for properties.
     */
    public Task() {}

    /**
     * Constructs a new Task object with the given values for properties.
     * @param id The unique identifier for the task.
     * @param listId The unique identifier for the list that this task belongs to.
     * @param title The title of the task.
     * @param state The state of the task, such as "todo" or "done".
     */
    public Task(String id, String listId, String title, String state) {
        this.id = id;
        this.title = title;
        this.state = state;
    }

    /**
     * Sets the unique identifier for the task.
     * @param id The unique identifier for the task.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the unique identifier for the task.
     * @return The unique identifier for the task.
     */
    public String getId () {
        return this.id;
    }

    /**
     * Sets the title of the task.
     * @param title The title of the task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the title of the task.
     * @return The title of the task.
     */
    public String getTitle () {
        return this.title;
    }

    /**
     * Sets the state of the task, 1 = todo, 2 = doing, 3 = done
     * @param state The state of the task.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the state of the task, 1 = todo, 2 = doing, 3 = done
     * @return The state of the task.
     */
    public String getState () {
        return this.state;
    }

    @Override
    public String toString() {
        return title;
    }
}

