package com.cw.notas.Notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.notas.DatabaseHelper;
import com.cw.notas.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class NoteAddActivity extends AppCompatActivity {
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnSave = findViewById(R.id.btnSave);
        EditText noteTitle = findViewById(R.id.etNoteTitle);
        EditText noteContent = findViewById(R.id.etNoteContent);
        TextView pgTitle = findViewById(R.id.pgTitle);

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

                    db = new DatabaseHelper(getApplicationContext());
                    db.noteUpdate(noteId,String.valueOf(noteTitle.getText()),String.valueOf(noteContent.getText()),formattedTime);
                    Toast.makeText(NoteAddActivity.this, R.string.notes_success, Toast.LENGTH_SHORT).show();
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
                            .setTitle(R.string.app_sure)
                            .setMessage(R.string.notes_delete)
                            .setPositiveButton(R.string.app_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db = new DatabaseHelper(getApplicationContext());
                                    db.noteDelete(noteId);
                                    Toast.makeText(NoteAddActivity.this, R.string.notes_successDelete, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                                    startActivity(intent);
                                }

                            }).setNegativeButton(R.string.app_cancel, null).show();
                }
            });


        } else {
            // Save a new Note
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String noteTitleStr = noteTitle.getText().toString().trim();

                    if(noteTitleStr.isEmpty()) {
                        Toast.makeText(NoteAddActivity.this, R.string.notes_error, Toast.LENGTH_SHORT).show();

                    } else {
                        LocalDateTime dateTime = LocalDateTime.now();
                        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                        String formattedTime = dateTime.format(formatDateTime);

                        UUID uuid = UUID.randomUUID(); // Generate random unique id
                        String noteId = uuid.toString();

                        db = new DatabaseHelper(getApplicationContext());
                        db.noteInsert(noteId,String.valueOf(noteTitle.getText()),String.valueOf(noteContent.getText()),formattedTime);

                        Toast.makeText(NoteAddActivity.this, R.string.notes_successCreated, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                        startActivity(intent);
                    }

                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
        startActivity(intent);

        this.finish();
    }
}