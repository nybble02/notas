package com.cw.notas.Checklist;

import java.util.List;

    /** Represents a Checklist object*/

public class Checklist {
    private String id;
    private String title;


    /**
     * Constructs a new Checklist object with default values for its properties.
     */
    public Checklist() {}

    /**
     * Constructs a new Checklist object with the specified values for its properties.
     *
     * @param id The unique identifier for the checklist.
     * @param title The title of the checklist.
     * @param checkboxList The list of checkboxes in the checklist.
     */
    public Checklist(String id, String title, List<Checkbox> checkboxList) {
        this.id = id;
        this.title = title;
    }

    /**
     * Sets the unique identifier for the checklist.
     *
     * @param id The new unique identifier for the checklist.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier for the checklist.
     *
     * @return The unique identifier for the checklist.
     */
    public String getId () {
        return this.id;
    }

    /**
     * Sets the title of the checklist.
     *
     * @param title The new title for the checklist.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the title of the checklist.
     *
     * @return The title of the checklist.
     */
    public String getTitle () {
        return this.title;
    }

    /**
     * Returns a string representation of the checklist, which is its title.
     *
     * @return A string representation of the checklist.
     */
    @Override
    public String toString() {
        return title;
    }

}
