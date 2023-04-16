package com.cw.notas;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Database {

    private static final String DATABASE_NAME = "notas.db";
    private static int DATABASE_VERSION = 29;
    private static Context context;
    static SQLiteDatabase db;
    private SQLiteStatement insertNote;
    private SQLiteStatement insertChecklist;
    private SQLiteStatement insertCheckbox;
    private SQLiteStatement insertTask;


    private static final String INSERT_NOTE = "INSERT INTO notes (id, title, content, dateCreated) VALUES (?,?,?,?)";
    private static final String INSERT_CHECKLIST = "INSERT INTO checklist (id, title) VALUES (?,?)";
    private static final String INSERT_CHECKBOX = "INSERT INTO checkbox (id, listId, title, state) VALUES (?,?,?,?)";
    private static final String INSERT_TASK = "INSERT INTO todo (id, title, state) VALUES (?,?,?)";
    private final String SELECT_CHECKBOX = "SELECT checkbox.id, listId, checkbox.title, state FROM checkbox INNER JOIN checklist ON checkbox.listId = checklist.id WHERE checklist.id=?";

    public Database(Context context) {
        Database.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        Database.db = openHelper.getWritableDatabase();

        this.insertNote = Database.db.compileStatement(INSERT_NOTE);
        this.insertChecklist = Database.db.compileStatement(INSERT_CHECKLIST);
        this.insertCheckbox = Database.db.compileStatement(INSERT_CHECKBOX);
        this.insertTask = Database.db.compileStatement(INSERT_TASK);
    }

    // Notes
    public long noteInsert(String id, String title, String content, String dateCreated) {
        this.insertNote.bindString(1, id);
        this.insertNote.bindString(2, title);
        this.insertNote.bindString(3, content);
        this.insertNote.bindString(4, dateCreated);
        return this.insertNote.executeInsert();
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


    // Checklist
    public long checklistInsert(String id, String title){
        this.insertChecklist.bindString(1, id);
        this.insertChecklist.bindString(2, title);
        return this.insertChecklist.executeInsert();
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
    public void checklistDelete(String listId) {
        db.delete("checkbox", "listId=?", new String[]{listId});
        db.delete("checklist", "id=?", new String[]{listId});
        db.close();
    }


    // Checkbox SQl
    public long checkboxInsert(String id, String listId, String title, String state) {
        this.insertCheckbox.bindString(1, id);
        this.insertCheckbox.bindString(2, listId);
        this.insertCheckbox.bindString(3, title);
        this.insertCheckbox.bindString(4, state);
        return this.insertCheckbox.executeInsert();
    }

    public List<String[]> checkboxSelect(String listId) {
        List<String[]> checkboxList = new ArrayList<String[]>();

        Cursor cursor;
        try {
             cursor = db.rawQuery(SELECT_CHECKBOX, new String[]{listId});
            int count = 0;

            if (cursor.moveToFirst()) {
                do {
                    String[] record = new String[]{
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    };

                    checkboxList.add(record);
                    count++;
                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            cursor.close();
            return checkboxList;
        } catch (SQLException ex) {
            Log.d("error",ex.toString());
            return checkboxList;
        }

    }

    public void checkboxUpdate(String checkboxId, String title, String state) {
        ContentValues checkboxValues = new ContentValues();
        checkboxValues.put("id", checkboxId);
        checkboxValues.put("title", title);
        checkboxValues.put("state", state);

        db.update("checkbox", checkboxValues, "id=?",new String[]{checkboxId});
        db.close();
    }

    public void checkboxDelete(String id) {
        db.delete("checkbox", "id=?", new String[]{id});
        db.close();
    }


    // Todos
    public long todoInsert(String id, String title, String state) {
        this.insertTask.bindString(1, id);
        this.insertTask.bindString(2, title);
        this.insertTask.bindString(3, state);
        return this.insertTask.executeInsert();
    }

    public List<String[]> todoSelectAll() {
        List<String[]> taskList = new ArrayList<String[]>();

        Cursor cursor = db.query("todo", new String[]{"id", "title", "state"}, null, null, null, null, null);

        int count = 0;

        if (cursor.moveToFirst()) {
            do {
                String[] record = new String[]{
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                };

                taskList.add(record);
                count++;
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();
        return taskList;
    }

    public void todoUpdate(String taskId, String title, String state) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("id", taskId);
        taskValues.put("title", title);
        taskValues.put("state", state); // 0 - to do | 1 - doing | 2 - done

        db.update("todo", taskValues, "id=?",new String[]{taskId});
        db.close();
    }

    public void todoDelete(String taskId) {
        db.delete("todo", "id=?", new String[]{taskId});
        db.close();
    }


    public static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE notes (id TEXT PRIMARY KEY, title TEXT, content TEXT, dateCreated TEXT)");
            db.execSQL("CREATE TABLE checklist (id TEXT PRIMARY KEY, title TEXT)");
            db.execSQL("CREATE TABLE checkbox (id TEXT PRIMARY KEY, listId TEXT, title TEXT, state TEXT)");
            db.execSQL("CREATE TABLE todo (id TEXT PRIMARY KEY, title TEXT, state TEXT)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DATABASE_VERSION = newVersion;
            db.execSQL("DROP TABLE IF EXISTS notes");
            db.execSQL("DROP TABLE IF EXISTS checklist");
            db.execSQL("DROP TABLE IF EXISTS checkbox");
            db.execSQL("DROP TABLE IF EXISTS todo");
            onCreate(db);
        }
    }


}
