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

/**
 *  A helper class for modifying a SQLite database
 */

public class DatabaseHelper {

    private static final String DATABASE_NAME = "notas.db";
    private static int DATABASE_VERSION = 33;
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


    /**
     * Constructs a new instance of the database
     * @param context
     */
    public DatabaseHelper(Context context) {
        DatabaseHelper.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        DatabaseHelper.db = openHelper.getWritableDatabase();

        this.insertNote = DatabaseHelper.db.compileStatement(INSERT_NOTE);
        this.insertChecklist = DatabaseHelper.db.compileStatement(INSERT_CHECKLIST);
        this.insertCheckbox = DatabaseHelper.db.compileStatement(INSERT_CHECKBOX);
        this.insertTask = DatabaseHelper.db.compileStatement(INSERT_TASK);
    }

    // Notes

    /**
     * Insert a new note in the database
     * @param id
     * @param title
     * @param content
     * @param dateCreated
     * @return the id of the row inserted
     */
    public long noteInsert(String id, String title, String content, String dateCreated) {
        this.insertNote.bindString(1, id);
        this.insertNote.bindString(2, title);
        this.insertNote.bindString(3, content);
        this.insertNote.bindString(4, dateCreated);
        return this.insertNote.executeInsert();
    }

    /**
     * Update a note in the database
     * @param noteId
     * @param title
     * @param content
     * @param dateCreated
     */
    public void noteUpdate(String noteId, String title, String content, String dateCreated) {
        ContentValues noteValues = new ContentValues();
        noteValues.put("title", title);
        noteValues.put("content", content);
        noteValues.put("dateCreated", dateCreated);

        db.update("notes", noteValues, "id=?",new String[]{noteId});
        db.close();

   }

    /**
     * Deletes a note in the database
     * @param noteId
     */
    public void noteDelete(String noteId) {
        db.delete("notes", "id=?", new String[]{noteId});
        db.close();
    }

    /**
     * Selects all the notes in the database
     * @return a list of notes
     */
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


    // Checklis

    /**
     * Inserts a checklist into the database
     * @param id
     * @param title
     * @return the id of the row inserted
     */
    public long checklistInsert(String id, String title){
        this.insertChecklist.bindString(1, id);
        this.insertChecklist.bindString(2, title);
        return this.insertChecklist.executeInsert();
    }

    /**
     * Selects all the checklist in the database
     * @return a list of checklists
     */
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

    /**
     * Deletes a checklist and all of its checkboxes
     * @param listId
     */
    public void checklistDelete(String listId) {
        db.delete("checkbox", "listId=?", new String[]{listId});
        db.delete("checklist", "id=?", new String[]{listId});
        db.close();
    }

    /**
     * Inserts a checkbox in a specific checklist
     * @param id
     * @param listId
     * @param title
     * @param state
     * @return id of row inserted
     */
    // Checkbox SQl
    public long checkboxInsert(String id, String listId, String title, String state) {
        this.insertCheckbox.bindString(1, id);
        this.insertCheckbox.bindString(2, listId);
        this.insertCheckbox.bindString(3, title);
        this.insertCheckbox.bindString(4, state);
        return this.insertCheckbox.executeInsert();
    }

    /**
     * Selects all the checkboxes in a checklist
     * @param listId
     * @return list of checkboxes
     */
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

    /**
     * Updates a checkbox
     * @param checkboxId
     * @param title
     * @param state
     */
    public void checkboxUpdate(String checkboxId, String title, String state) {
        ContentValues checkboxValues = new ContentValues();
        checkboxValues.put("id", checkboxId);
        checkboxValues.put("title", title);
        checkboxValues.put("state", state);

        db.update("checkbox", checkboxValues, "id=?",new String[]{checkboxId});
        db.close();
    }

    /**
     * Deletes a checkbox
     * @param id
     */
    public void checkboxDelete(String id) {
        db.delete("checkbox", "id=?", new String[]{id});
        db.close();
    }


    // Todos

    /**
     * Inserts a task into the database
     * @param id
     * @param title
     * @param state
     * @return an Id of the row that was inserted
     */
    public long todoInsert(String id, String title, String state) {
        this.insertTask.bindString(1, id);
        this.insertTask.bindString(2, title);
        this.insertTask.bindString(3, state);
        return this.insertTask.executeInsert();
    }

    /**
     * Selects all the todos task in the database
     * @return a list of todo tasks
     */
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

    /**
     * Updates a todo task
     * @param taskId
     * @param title
     * @param state
     */
    public void todoUpdate(String taskId, String title, String state) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("id", taskId);
        taskValues.put("title", title);
        taskValues.put("state", state); // 0 - to do | 1 - doing | 2 - done

        db.update("todo", taskValues, "id=?",new String[]{taskId});
        db.close();
    }

    /**
     * Deletes a todo task
     * @param taskId
     */
    public void todoDelete(String taskId) {
        db.delete("todo", "id=?", new String[]{taskId});
        db.close();
    }

    /**
     * Deletes all the task on a specific column
     * @param state
     */
    public void todoDeleteBoard(String state) {
        db.delete("todo", "state=?", new String[]{state});
        db.close();
    }

    /**
     * Deletes all the tasks
     */
    public void todoDeleteAll() {
        db.delete("todo", null, null);
        db.close();
    }

    /**
     * Helper class that creates the database and does version management
     */
    public static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Creates a database with the specified tables
         * @param db
         */
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE notes (id TEXT PRIMARY KEY, title TEXT, content TEXT, dateCreated TEXT)");
            db.execSQL("CREATE TABLE checklist (id TEXT PRIMARY KEY, title TEXT)");
            db.execSQL("CREATE TABLE checkbox (id TEXT PRIMARY KEY, listId TEXT, title TEXT, state TEXT)");
            db.execSQL("CREATE TABLE todo (id TEXT PRIMARY KEY, title TEXT, state TEXT)");
        }

        /**
         * Deletes the tables if the version number changes
         * @param db
         * @param oldVersion
         * @param newVersion
         */

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
