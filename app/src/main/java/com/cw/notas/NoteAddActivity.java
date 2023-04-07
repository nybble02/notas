package com.cw.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class NoteAddActivity extends AppCompatActivity {
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        Button btnDelete = (Button)findViewById(R.id.btnDelete);
        Button btnSave = (Button)findViewById(R.id.btnSave);
        EditText noteTitle = (EditText) findViewById(R.id.etNoteTitle);
        EditText noteContent = (EditText) findViewById(R.id.etNoteContent);
        TextView pgTitle = (TextView) findViewById(R.id.pgTitle);



        Intent intent = getIntent();
        String noteId = intent.getStringExtra("noteId");

        if (noteId != null) {
            pgTitle.setText(getResources().getString(R.string.notes_btn_editNote));
            btnDelete.setVisibility(View.VISIBLE);
            for (Note note : NoteListActivity.noteList) {
                if (Objects.equals(note.getId(), noteId)) {
                    noteTitle.setText(note.getTitle());
                    noteContent.setText(note.getContent());
                    break;
                }
            }
            // Edit Note
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    String formattedTime = dateTime.format(formatDateTime);

                    db = new Database(getApplicationContext());
                    db.noteUpdate(noteId,String.valueOf(noteTitle.getText()),String.valueOf(noteContent.getText()),formattedTime);
                    Toast.makeText(NoteAddActivity.this, "Note successfully Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                    startActivity(intent);
                }
            });
            // Delete Note
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(NoteAddActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are you sure?")
                            .setMessage("Delete this note?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db = new Database(getApplicationContext());
                                    db.noteDelete(noteId);
                                    Toast.makeText(NoteAddActivity.this, "Note successfully deleted!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                                    startActivity(intent);
                                }

                            }).setNegativeButton("No", null).show();
                }
            });


        } else {
            // Save a new Note
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    String formattedTime = dateTime.format(formatDateTime);

                    UUID uuid = UUID.randomUUID(); // Generate random unique id
                    String noteId = uuid.toString();

                    db = new Database(getApplicationContext());
                    db.noteInsert(noteId,String.valueOf(noteTitle.getText()),String.valueOf(noteContent.getText()),formattedTime);

                    Toast.makeText(NoteAddActivity.this, "Note successfully created!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}