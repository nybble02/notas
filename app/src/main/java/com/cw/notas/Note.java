package com.cw.notas;

public class Note {
    private String id;
    private String title;
    private String content;
    private String dateCreated;

    public Note() {}

    public Note(String id, String title, String content, String dateCreated) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent () {
        return this.content;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }

    @Override
    public String toString() {
        return dateCreated + " - " + title;
    }


}
