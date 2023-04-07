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

    private static final String DATABASE_NAME = "notas.db";
    private static int DATABASE_VERSION = 5;
    static final String TABLE_NAME = "newtable";
    private static Context context;
    static SQLiteDatabase db;
    private SQLiteStatement insertNote;

    private static final String INSERT_NOTE = "insert into notes (id, title, content, dateCreated) values (?,?,?,?)";

    public Database(Context context) {
        Database.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        Database.db = openHelper.getWritableDatabase();

        this.insertNote = Database.db.compileStatement(INSERT_NOTE);

    }

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

    public static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE notes (id TEXT PRIMARY KEY, title TEXT, content TEXT, dateCreated TEXT)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DATABASE_VERSION = newVersion;
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}
