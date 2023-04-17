package com.cw.notas.Todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cw.notas.BaseActivity;
import com.cw.notas.Database;
import com.cw.notas.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DoingActivity extends BaseActivity {
    private Database db;
    static ArrayList<Task> taskList = new ArrayList<Task>();
    List<String[]> taskDB = null;
    ArrayAdapter<Task> adapter;

    ListView todoListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing);
        onCreateDrawer();

        todoListView = (ListView)findViewById(R.id.taskList);
        registerForContextMenu(todoListView);

        Button btnTodo = findViewById(R.id.btnMoveTodo);
        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                startActivity(intent);
            }
        });

        Button btnDone = findViewById(R.id.btnMoveDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DoneActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateTaskList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeTaskList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        menu.setHeaderTitle(getResources().getString(R.string.todos_menu_header));
        inflater.inflate(R.menu.task_item_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                //Toast.makeText(getActivity(), getResources().getString(R.string.app_edit), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                deleteTaskDialog(taskList.get(info.position).getId());
                return true;
            case R.id.todo:
                updateTaskState(taskList.get(info.position), "0");
                return true;
            case R.id.doing:
                Toast.makeText(DoingActivity.this, getResources().getString(R.string.todos_menu_alreadyDoing), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.done:
                updateTaskState(taskList.get(info.position), "2");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todos_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.option_add:
                openDialog();
                break;
            case R.id.option_delete_items:
                deleteAll();
                break;
            case R.id.option_clear_items:
                clearBoard("1");
                break;
        }
        return false;
    }

    private void openDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DoingActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        // Set view to show custom dialog
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        // Get title of list from custom dialog box
        EditText etTaskTitle = dialogView.findViewById(R.id.taskTitle);


        builder.setView(dialogView)
                .setTitle(R.string.todos_add_task)
                .setPositiveButton(R.string.app_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        UUID uuid = UUID.randomUUID(); // Generate random unique id

                        String taskTitle = String.valueOf(etTaskTitle.getText());
                        String taskId = uuid.toString();

                        db = new Database(getApplicationContext());
                     //   db.todoInsert(taskId, taskTitle,"0");

                        onDialogBoxClose();
                    }
                }).setNegativeButton(R.string.app_cancel, null).show();

    }

    private void onDialogBoxClose(){
        removeTaskList();
        populateTaskList();
    }

    private void populateTaskList(){

        db = new Database(getApplicationContext());
        taskDB = db.todoSelectAll();

        for (String [] task : taskDB) {
            Task taskObj = new Task();

            taskObj.setId(task[0]);
            taskObj.setTitle(task[1]);
            taskObj.setState(task[2]);

            if (Objects.equals(taskObj.getState(), "1")) {
                taskList.add(taskObj);
            }

            adapter = new ArrayAdapter<Task>(DoingActivity.this, android.R.layout.simple_list_item_1, taskList);
            todoListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    private void removeTaskList() {
        taskList.removeAll(taskList);
       // adapter.notifyDataSetChanged();
    }

    private void updateTaskState(Task task, String state) {
        db = new Database(DoingActivity.this);
      //  db.todoUpdate(task.getId(),task.getTitle(), state);

        removeTaskList();
        populateTaskList();
    }

    private void deleteTaskDialog(String taskId) {
        new AlertDialog.Builder(DoingActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_sure)
                .setMessage(R.string.todos_dialog_delete)
                .setPositiveButton(R.string.app_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new Database(DoingActivity.this);
                        db.todoDelete(taskId);
                        Toast.makeText(DoingActivity.this, R.string.todos_dialog_delete, Toast.LENGTH_SHORT).show();
                        removeTaskList();
                        populateTaskList();
                    }
                }).setNegativeButton(R.string.app_no, null).show();
    }

    private void clearBoard(String state) {
        new AlertDialog.Builder(DoingActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_sure)
                .setMessage(R.string.todos_dialog_Clear)
                .setPositiveButton(R.string.app_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new Database(DoingActivity.this);
                        db.todoDeleteBoard(state);
                        removeTaskList();
                        adapter.notifyDataSetChanged();
                        populateTaskList();
                        Toast.makeText(DoingActivity.this, R.string.todos_delete_taskSuccess, Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton(R.string.app_no, null).show();
    }

    private void deleteAll() {
        new AlertDialog.Builder(DoingActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_sure)
                .setMessage(R.string.todos_dialog_deleteAll)
                .setPositiveButton(R.string.app_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new Database(DoingActivity.this);
                        db.todoDeleteAll();
                        removeTaskList();
                        adapter.notifyDataSetChanged();
                        populateTaskList();
                        Toast.makeText(DoingActivity.this, R.string.todos_deleteAll_Success, Toast.LENGTH_SHORT).show();


                    }
                }).setNegativeButton(R.string.app_no, null).show();

    }

    // Stop the animation
    @Override
    public void recreate() {
        super.recreate();
        overridePendingTransition(0, 0); // prevents transition animation when recreating activity
    }
}