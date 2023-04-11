package com.cw.notas.Checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cw.notas.CheckboxAdapter;
import com.cw.notas.Database;
import com.cw.notas.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChecklistViewActivity extends AppCompatActivity {
    private Database db;
    static ArrayList<Checkbox> checkboxList = new ArrayList<Checkbox>();
    List<String[]> checkboxDB = null;
    //ArrayAdapter<CheckBox> adapter;
    CheckboxAdapter cBoxAdpater;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.checklist_options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        Intent intent;
        switch (item.getItemId())
        {
            case R.id.option_add:
                //openCheckBoxDialogBox();
                break;
            case R.id.option_delete:
                //TODO: delete list
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_view);

        TextView pgTitle = findViewById(R.id.pgTitle);
        Button btnAddCheckbox = findViewById(R.id.btnAddCheckbox);

        Intent intent = getIntent();
        String listId = intent.getStringExtra("listId");

        //ListView checkboxListView = (ListView)findViewById(R.id.checkboxListView);


        btnAddCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChecklistViewActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                // Set view to show custom dialog
                View dialogView = inflater.inflate(R.layout.dialog_add_checkbox, null);
                // Get title of list from custom dialog box
                EditText etCheckboxTitle = dialogView.findViewById(R.id.checkboxTitle);

                builder.setView(dialogView)
                        .setTitle(R.string.chkbox_add_title)
                        .setPositiveButton(R.string.app_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                UUID uuid = UUID.randomUUID(); // Generate random unique id

                                String checkboxTitle = String.valueOf(etCheckboxTitle.getText());
                                String checkboxId = uuid.toString();

                                db = new Database(getApplicationContext());
                                db.checkboxInsert(checkboxId, listId, checkboxTitle,"0");

                                onDialogBoxClose(listId);
                            }
                        }).setNegativeButton(R.string.app_cancel, null).show();
            }
        });

        for (Checklist list : ChecklistListActivity.checklistList) {
            if (Objects.equals(list.getId(), listId)) {
                pgTitle.setText(list.getTitle());
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String listId = intent.getStringExtra("listId");

        populateCheckboxList(listId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeCheckbox();
    }

    private void onDialogBoxClose(String listId){
        removeCheckbox();
        populateCheckboxList(listId);
    }

    private void removeCheckbox() {
        checkboxList.removeAll(checkboxList);
        //adapter.notifyDataSetChanged();
    }

    private void populateCheckboxList(String listId) {


        ListView checkboxListView = (ListView)findViewById(R.id.checkboxListView);

        db = new Database(getApplicationContext());
        checkboxDB = db.checkboxSelect(listId);

        for (String[] checkbox : checkboxDB) {
            Checkbox checkboxObj = new Checkbox();

            checkboxObj.setId(checkbox[0]);
            checkboxObj.setListId(checkbox[1]);
            checkboxObj.setTitle(checkbox[2]);
            checkboxObj.setState(checkbox[3]);

            //CheckBox checkBox = new CheckBox(getApplicationContext());
            //checkBox.setText(checkboxObj.getTitle());

            checkboxList.add(checkboxObj);

            //checkboxLayout.addView(checkBox);

           //Toast.makeText(ChecklistViewActivity.this,  checkboxObj.getTitle(), Toast.LENGTH_SHORT).show();

        }
        cBoxAdpater = new CheckboxAdapter(ChecklistViewActivity.this, checkboxList);
        //adapter = new ArrayAdapter<CheckBox>(this, android.R.layout.simple_list_item_1, checkboxList);
        checkboxListView.setAdapter(cBoxAdpater);
        checkboxListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}