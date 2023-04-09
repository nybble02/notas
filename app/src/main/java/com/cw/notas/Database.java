package com.cw.notas;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Database {

    // TODO: Add insert, delete and update methods for checklist
    // TODO: Add insert, delete and update methods for checkbox

    private static final String DATABASE_NAME = "notas.db";
    private static int DATABASE_VERSION = 6;
    static final String TABLE_NAME = "newtable";
    private static Context context;
    static SQLiteDatabase db;
    private SQLiteStatement insertNote;
    private SQLiteStatement insertChecklist;
    private SQLiteStatement insertCheckbox;

    private static final String INSERT_NOTE = "insert into notes (id, title, content, dateCreated) values (?,?,?,?)";
    private static final String INSERT_CHECKLIST = "insert into checklist (id, title) values (?,?)";
    //private static final String INSERT_CHECKBOX = "insert into checkbox (id, listId, title, state) values (?,?,?,?)";



    public Database(Context context) {
        Database.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        Database.db = openHelper.getWritableDatabase();

        this.insertNote = Database.db.compileStatement(INSERT_NOTE);
        this.insertChecklist = Database.db.compileStatement(INSERT_CHECKLIST);
        //this.insertCheckbox = Database.db.compileStatement(INSERT_CHECKBOX);


    }

    public long noteInsert(String id, String title, String content, String dateCreated) {
        this.insertNote.bindString(1, id);
        this.insertNote.bindString(2, title);
        this.insertNote.bindString(3, content);
        this.insertNote.bindString(4, dateCreated);
        return this.insertNote.executeInsert();
    }

    public long checklistInsert(String id, String title){
        this.insertChecklist.bindString(1, id);
        this.insertChecklist.bindString(2, title);
        return this.insertChecklist.executeInsert();
    }


   public void noteUpdate(String noteId, String title, String content, String dateCreated) {
        ContentValues noteValues = new ContentValues();
        noteValues.put("title", title);
        noteValues.put("content", content);
        noteValues.put("dateCreated", dateCreated);

        db.update("notes", noteValues, "id=?",new String[]{noteId});
        db.close();

   }

    public void noteDelete(String noteId) {
        db.delete("notes", "id=?", new String[]{noteId});
        db.close();
    }

    public List<String[]> noteSelectAll() {
        List<String[]> noteList = new ArrayList<String[]>();

        Cursor cursor = db.query("notes", new String[]{"id", "title", "content", "dateCreated"}, null, null, null, null, null);

        int count = 0;

        if (cursor.moveToFirst()) {
            do {
                String[] record = new String[]{
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                };

                noteList.add(record);
                count++;
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();
        return noteList;
    }

    public List<String[]> checklistSelectAll() {
        List<String[]> checklistList = new ArrayList<String[]>();

        Cursor cursor = db.query("checklist", new String[]{"id", "title"}, null, null, null, null, null);

        int count = 0;

        if (cursor.moveToFirst()) {
            do {
                String[] record = new String[]{
                        cursor.getString(0),
                        cursor.getString(1),
                };

                checklistList.add(record);
                count++;
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();
        return checklistList;
    }

    public static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE notes (id TEXT PRIMARY KEY, title TEXT, content TEXT, dateCreated TEXT)");
            db.execSQL("CREATE TABLE checklist (id TEXT PRIMARY KEY, title TEXT)");

        }

        // TODO: Why is this not working???
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DATABASE_VERSION = newVersion;
            db.execSQL("DROP TABLE IF EXISTS notes");
            db.execSQL("DROP TABLE IF EXISTS checklist");
            //db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }


}
