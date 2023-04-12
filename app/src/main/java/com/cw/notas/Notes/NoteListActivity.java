package com.cw.notas.Notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cw.notas.BaseActivity;
import com.cw.notas.Database;
import com.cw.notas.MainActivity;
import com.cw.notas.Notes.Note;
import com.cw.notas.Notes.NoteAddActivity;
import com.cw.notas.R;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends BaseActivity {
    Database db;
    static List<Note> noteList = new ArrayList<Note>();
    List<String[]> notesDB = null;

    ArrayAdapter<Note> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        onCreateDrawer();

        View btnAddNewNote = findViewById(R.id.btnAddNewNote);

         btnAddNewNote.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), NoteAddActivity.class);
                 startActivity(intent);

             }
         });

    }

    @Override
    protected void onStart() {
        super.onStart();

        db = new Database(getApplicationContext());
        notesDB = db.noteSelectAll();

        for (String[] note : notesDB) {
            Note noteObj = new Note();

            noteObj.setId(note[0]);
            noteObj.setTitle(note[1]);
            noteObj.setContent(note[2]);
            noteObj.setDateCreated(note[3]);

            noteList.add(noteObj);

        }

        adapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, noteList);
        adapter.notifyDataSetChanged();

        ListView noteListView = (ListView)findViewById(R.id.noteList);
        noteListView.setAdapter(adapter);

        // TODO: LongClick Listener - delete
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), NoteAddActivity.class);
                intent.putExtra("noteId", noteList.get(i).getId());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        noteList.removeAll(noteList);
        adapter.notifyDataSetChanged();
    }


}