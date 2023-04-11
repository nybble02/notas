package com.cw.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.cw.notas.Checklist.ChecklistListActivity;
import com.cw.notas.Notes.NoteListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnNotes = (Button) findViewById(R.id.btnNotes);
        btnNotes.setOnClickListener(this::onClick);
        View btnCheckList = (Button) findViewById(R.id.btnChecklist);
        btnCheckList.setOnClickListener(this::onClick);
        View btnTodo = (Button) findViewById(R.id.btnTodo);
        btnTodo.setOnClickListener(this::onClick);

    }

    public void onClick(View view) {
        Intent intent;

        switch (view.getId())
        {
            case R.id.btnNotes:
                intent = new Intent(getApplicationContext(), NoteListActivity.class);
                startActivity(intent);
                break;
            case R.id.btnChecklist:
                intent = new Intent(getApplicationContext(), ChecklistListActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.language_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

}