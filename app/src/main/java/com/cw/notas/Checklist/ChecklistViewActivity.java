package com.cw.notas.Checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.notas.Database;
import com.cw.notas.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChecklistViewActivity extends AppCompatActivity {
    private Database db;
    static ArrayList<Checkbox> checkboxList = new ArrayList<Checkbox>();
    List<String[]> checkboxDB = null;

    CheckboxAdapter checkboxAdapter;
    TextView pgTitle;
    Intent intent;
    String listId;

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
                openDialog();
                break;
            case R.id.option_delete_list:
                onDeleteList(listId);
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_view);


        pgTitle = findViewById(R.id.pgTitle);
        //  Button btnAddCheckbox = findViewById(R.id.btnAddCheckbox);

        intent = getIntent();
        registerReceiver(cBoxDelete, new IntentFilter("Check_Box_Delete"));
        listId = intent.getStringExtra("listId");

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

   private BroadcastReceiver  cBoxDelete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onDialogBoxClose(listId);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        removeCheckbox();
    }

    private void openDialog(){
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

    private void onDialogBoxClose(String listId){
        removeCheckbox();
        populateCheckboxList(listId);
    }



    private void onDeleteList(String listId) {
        new android.app.AlertDialog.Builder(ChecklistViewActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Delete this list?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new Database(getApplicationContext());
                        db.checklistDelete(listId);
                        Toast.makeText(ChecklistViewActivity.this, "Checklist successfully deleted!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChecklistListActivity.class);
                        startActivity(intent);
                    }

                }).setNegativeButton("No", null).show();
    }


    private void removeCheckbox() {
        checkboxList.removeAll(checkboxList);
        checkboxAdapter.notifyDataSetChanged();
    }

    private void populateCheckboxList(String listId) {


        ListView checkboxListView = (ListView)findViewById(R.id.checkboxListView);
        //ListView checkboxListView_checked = (ListView)findViewById(R.id.checkboxListView_checked);


        db = new Database(getApplicationContext());
        checkboxDB = db.checkboxSelect(listId);

        for (String[] checkbox : checkboxDB) {
            Checkbox checkboxObj = new Checkbox();

            checkboxObj.setId(checkbox[0]);
            checkboxObj.setListId(checkbox[1]);
            checkboxObj.setTitle(checkbox[2]);
            checkboxObj.setState(checkbox[3]);

            checkboxList.add(checkboxObj);
        }
        checkboxAdapter = new CheckboxAdapter(ChecklistViewActivity.this, checkboxList);
        checkboxListView.setAdapter(checkboxAdapter);
        checkboxAdapter.notifyDataSetChanged();

        checkboxListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ChecklistViewActivity.this, checkboxList.get(i).getTitle() + " " + checkboxList.get(i).getState(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), ChecklistListActivity.class);
        startActivity(intent);

        this.finish();
    }
}

