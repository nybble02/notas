package com.cw.notas.Todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cw.notas.BaseActivity;

import com.cw.notas.DatabaseHelper;
import com.cw.notas.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TodoActivity extends BaseActivity {

    private DatabaseHelper db;
    static ArrayList<Task> taskList = new ArrayList<Task>();
    List<String[]> taskDB = null;
    ArrayAdapter<Task> adapter;

    ListView todoListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        onCreateDrawer();

        todoListView = (ListView)findViewById(R.id.taskList);
        registerForContextMenu(todoListView);

        Button btnDoing = findViewById(R.id.btnMoveDoing);
        btnDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DoingActivity.class);
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
            case R.id.delete:
                deleteTaskDialog(taskList.get(info.position).getId());
                return true;
            case R.id.todo:
               Toast.makeText(TodoActivity.this, getResources().getString(R.string.todos_menu_alreadyTodo), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.doing:
                updateTaskState(taskList.get(info.position), "1");
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

        Intent intent;
        switch (item.getItemId())
        {
            case R.id.option_add:
                intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.option_delete_items:
                deleteAll();
                break;
            case R.id.option_clear_items:
                clearBoard("0");
                break;
        }
        return false;
    }

    private void populateTaskList(){

        db = new DatabaseHelper(getApplicationContext());
        taskDB = db.todoSelectAll();

        for (String [] task : taskDB) {
            Task taskObj = new Task();

            taskObj.setId(task[0]);
            taskObj.setTitle(task[1]);
            taskObj.setState(task[2]);

            if (Objects.equals(taskObj.getState(), "0")) {
                taskList.add(taskObj);
            }

            adapter = new ArrayAdapter<Task>(TodoActivity.this, android.R.layout.simple_list_item_1, taskList);
            todoListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    private void removeTaskList() {
        taskList.removeAll(taskList);
    }

    private void updateTaskState(Task task, String state) {
        db = new DatabaseHelper(TodoActivity.this);
        db.todoUpdate(task.getId(),task.getTitle(), state);

        removeTaskList();
        populateTaskList();
    }

    private void deleteTaskDialog(String taskId) {
        new AlertDialog.Builder(TodoActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_sure)
                .setMessage(R.string.todos_dialog_delete)
                .setPositiveButton(R.string.app_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new DatabaseHelper(TodoActivity.this);
                        db.todoDelete(taskId);
                        Toast.makeText(TodoActivity.this, R.string.todos_deleteAll_Success, Toast.LENGTH_SHORT).show();
                        removeTaskList();
                        adapter.notifyDataSetChanged();
                        populateTaskList();
                    }
                }).setNegativeButton(R.string.app_no, null).show();
    }

    private void clearBoard(String state) {
        new AlertDialog.Builder(TodoActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_sure)
                .setMessage(R.string.todos_dialog_Clear)
                .setPositiveButton(R.string.app_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new DatabaseHelper(TodoActivity.this);
                        db.todoDeleteBoard(state);
                        removeTaskList();
                        adapter.notifyDataSetChanged();
                        populateTaskList();
                        Toast.makeText(TodoActivity.this, R.string.todos_delete_taskSuccess, Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton(R.string.app_no, null).show();
    }

    private void deleteAll() {
        new AlertDialog.Builder(TodoActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_sure)
                .setMessage(R.string.todos_dialog_deleteAll)
                .setPositiveButton(R.string.app_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new DatabaseHelper(TodoActivity.this);
                        db.todoDeleteAll();
                        removeTaskList();
                        adapter.notifyDataSetChanged();
                        populateTaskList();
                        Toast.makeText(TodoActivity.this, R.string.todos_deleteAll_Success, Toast.LENGTH_SHORT).show();

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