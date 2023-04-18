package com.cw.notas.Notes;

    /**
     * Represents a Note
     * */

public class Note {
    private String id;
    private String title;
    private String content;
    private String dateCreated;

    /**
     * Constructs a new Note object with default values for its properties.
     */
    public Note() {}

    /**
     * Constructs a new Note object with the specified values for its properties.
     *
     * @param id The unique identifier for the note.
     * @param title The title of the note.
     * @param content The content of the note.
     * @param dateCreated The date and time the note was created.
     */
    public Note(String id, String title, String content, String dateCreated) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    /**
     * Sets the unique identifier for the note.
     *
     * @param id The new unique identifier for the note.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier for the note.
     *
     * @return The unique identifier for the note.
     */
    public String getId () {
        return this.id;
    }

    /**
     * Sets the title of the note.
     *
     * @param title The new title for the note.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the title of the note.
     *
     * @return The title of the note.
     */
    public String getTitle () {
        return this.title;
    }

    /**
     * Sets the content of the note.
     *
     * @param content The new content for the note.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the content of the note.
     *
     * @return The content of the note.
     */
    public String getContent () {
        return this.content;
    }

    /**
     * Sets the date and time the note was created.
     *
     * @param dateCreated The new date and time the note was created.
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Returns the date and time the note was created.
     *
     * @return The date and time the note was created.
     */
    public String getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Returns a string representation of the note, in the format "dateCreated - title".
     *
     * @return A string representation of the note.
     */
    @Override
    public String toString() {
        return dateCreated + " - " + title;
    }

}
