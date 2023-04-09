package com.cw.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.notas.Notes.Note;
import com.cw.notas.Notes.NoteAddActivity;
import com.cw.notas.Notes.NoteListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChecklistListActivity extends AppCompatActivity {
    private Database db;

    static List<Checklist> checklistList = new ArrayList<Checklist>();
    List<String[]> checklistDB = null;

    ArrayAdapter<Checklist> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_list);

        Button btnAddNewList = findViewById(R.id.btnAddNewNote);

        btnAddNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChecklistListActivity.this);
                AlertDialog alert = builder.create();
                LayoutInflater inflater = getLayoutInflater();


                builder.setView(inflater.inflate(R.layout.dialog_add_list, null))
                        .setPositiveButton(R.string.app_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                EditText listTitle = alert.findViewById(R.id.listTitle);

                                UUID uuid = UUID.randomUUID(); // Generate random unique id
                                String checklistId = uuid.toString();

                                db = new Database(getApplicationContext());
                                db.checklistInsert(checklistId,String.valueOf(listTitle.getText()));

                                Toast.makeText(ChecklistListActivity.this, "Checklist successfully created!", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton(R.string.app_cancel, null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView pgEmpty = findViewById(R.id.pgEmpty);

        db = new Database(getApplicationContext());
        checklistDB = db.noteSelectAll();

        for (String[] list : checklistDB) {
            Checklist checklistObj = new Checklist();

            checklistObj.setId(list[0]);
            checklistObj.setTitle(list[1]);
            checklistList.add(checklistObj);

        }

        adapter = new ArrayAdapter<Checklist>(this, android.R.layout.simple_list_item_1, checklistList);
        adapter.notifyDataSetChanged();

        ListView checklistListView = (ListView)findViewById(R.id.checklistList);
        checklistListView.setAdapter(adapter);

        checklistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(ChecklistListActivity.this, checklistList.get(i).getId(), Toast.LENGTH_SHORT).show();


                /*Intent intent = new Intent(getApplicationContext(), NoteAddActivity.class);
                intent.putExtra("noteId", checklistList.get(i).getId());
                startActivity(intent);*/
            }
        });


    }


}