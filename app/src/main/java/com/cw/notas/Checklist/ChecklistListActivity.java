package com.cw.notas.Checklist;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.cw.notas.BaseActivity;
import com.cw.notas.Database;
import com.cw.notas.MainActivity;
import com.cw.notas.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChecklistListActivity extends BaseActivity {
    private Database db;

    static List<Checklist> checklistList = new ArrayList<Checklist>();
    List<String[]> checklistDB = null;

    ArrayAdapter<Checklist> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_list);
        onCreateDrawer();


        Button btnAddNewList = findViewById(R.id.btnAddNewChecklist);


        btnAddNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChecklistListActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                // Set view to show custom dialog
                View dialogView = inflater.inflate(R.layout.dialog_add_list, null);
                // Get title of list from custom dialog box
                EditText etListTitle = (EditText)dialogView.findViewById(R.id.listTitle);

                builder.setView(dialogView)
                .setTitle(R.string.chkList_create_list)
                .setPositiveButton(R.string.app_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        UUID uuid = UUID.randomUUID(); // Generate random unique id

                        String listTitle = String.valueOf(etListTitle.getText());
                        String listId = uuid.toString();

                        if(listTitle.isEmpty()) {
                            Toast.makeText(ChecklistListActivity.this,  R.string.chkList_error, Toast.LENGTH_SHORT).show();
                        } else {
                            db = new Database(getApplicationContext());
                            db.checklistInsert(listId, listTitle);
                            Toast.makeText(ChecklistListActivity.this, R.string.chkList_successCreated, Toast.LENGTH_SHORT).show();
                            dialogBoxOnClose();
                        }
                    }
                }).setNegativeButton(R.string.app_cancel, null).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        populateChecklistList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeChecklistList();
    }


    private void dialogBoxOnClose() {
        removeChecklistList();
        populateChecklistList();
    }

    private void populateChecklistList() {

        db = new Database(getApplicationContext());
        checklistDB = db.checklistSelectAll();

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
                Intent intent = new Intent(getApplicationContext(), ChecklistViewActivity.class);
                intent.putExtra("listId", checklistList.get(i).getId());
                startActivity(intent);
            }
        });
    }

    private void removeChecklistList() {
        checklistList.removeAll(checklistList);
        adapter.notifyDataSetChanged();
    }



}