package com.cw.notas;

import java.util.List;

public class Checklist {

    private String id;
    private String title;
    private List<Checkbox> checkboxList;
    //private String dateCreated;

    public Checklist() {}

    public Checklist(String id, String title, List<Checkbox> checkboxList) {
        this.id = id;
        this.title = title;
        this.checkboxList = checkboxList;
       // this.dateCreated = dateCreated;
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

    public void setContent(List<Checkbox> checkboxList) {
        this.checkboxList = checkboxList;
    }

    public List<Checkbox> getContent () {
        return this.checkboxList;
    }

    //public void setDateCreated(String dateCreated) {
     //   this.dateCreated = dateCreated;
    //}

   // public String getDateCreated() {
   //     return this.dateCreated;
    //}

    @Override
    public String toString() {
        return title;
    }
}
