package com.cw.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NoteAddActivity extends AppCompatActivity {
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        View btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String noteTitle = ((EditText) findViewById(R.id.etNoteTitle)).getText().toString();
                String noteContent = ((EditText) findViewById(R.id.etNoteContent)).getText().toString();

                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedTime = dateTime.format(formatDateTime);

                db = new Database(getApplicationContext());
                db.noteInsert(noteTitle,noteContent,formattedTime);

                Toast.makeText(NoteAddActivity.this, "Note successfully created!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                startActivity(intent);




            }
        });
    }
}