package com.cw.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    public int idToModify;
    Database db;
    List<String[]> list = new ArrayList<String[]>();
    List<String[]> notesDB = null;
    String[] stg1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        View btnAddNewNote = (Button)findViewById(R.id.btnAddNewNote);

         btnAddNewNote.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), NoteAddActivity.class);
                 startActivity(intent);
             }
         });

        db = new Database(getApplicationContext());
        notesDB = db.noteSelectAll();
        stg1 = new String[notesDB.size()];

        int count = 0;
        String stg;

        for (String[] note : notesDB) {
            stg = note[1] + " - "
                    + note[2] + " - "
                    + note[3];
            stg1[count] = stg;
            count++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stg1);

        ListView noteList = (ListView)findViewById(R.id.noteList);
        noteList.setAdapter(adapter);
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        Toast.makeText(NoteListActivity.this, "Clicked " + v.toString(), Toast.LENGTH_SHORT).show();
    }




}