package com.cw.notas.Todos.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cw.notas.Database;
import com.cw.notas.Notes.NoteAddActivity;
import com.cw.notas.Notes.NoteListActivity;
import com.cw.notas.R;
import com.cw.notas.Todos.Task;
import com.cw.notas.Todos.TodoViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TodoFragment extends Fragment {
    private Database db;

    static ArrayList<Task> taskList = new ArrayList<Task>();
    List<String[]> taskDB = null;
    ListView tasksListView;
    ArrayAdapter<Task> adapter;
    public TodoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment, container, false);
        tasksListView = view.findViewById(R.id.taskList);
        registerForContextMenu(tasksListView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getActivity(), "Start ", Toast.LENGTH_SHORT).show();
        populateTaskListView();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        menu.setHeaderTitle(getResources().getString(R.string.todos_menu_header));
        inflater.inflate(R.menu.task_item_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(getActivity(), getResources().getString(R.string.app_edit), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                deleteTaskDialog(taskList.get(info.position).getId());
                return true;
            case R.id.todo:
                Toast.makeText(getActivity(), getResources().getString(R.string.todos_menu_alreadyTodo), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.doing:
                //Toast.makeText(getActivity(), String.valueOf(taskList.get(info.position).getTitle()), Toast.LENGTH_SHORT).show();
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
    public void onStop() {
        super.onStop();
        removeTaskList();
    }

    private void populateTaskListView() {
        db = new Database(getActivity());
        taskDB = db.todoSelectAll();

        for (String[] task : taskDB) {
            Task taskObj = new Task();

            taskObj.setId(task[0]);
            taskObj.setTitle(task[1]);
            taskObj.setState(task[2]);

            if (Objects.equals(taskObj.getState(), "0")) {
                taskList.add(taskObj);
            }
        }
        adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, taskList);
        tasksListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void removeTaskList() {
        taskList.removeAll(taskList);
        adapter.notifyDataSetChanged();
    }


    private void updateTaskState(Task task, String state) {
        db = new Database(getActivity());
        db.todoUpdate(task.getId(),task.getTitle(), state);

        removeTaskList();
        populateTaskListView();
        //taskList.removeAll(taskList);
    }

    private void deleteTaskDialog(String taskId) {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Delete this Task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new Database(getActivity());
                        db.todoDelete(taskId);
                        Toast.makeText(getActivity(), "Task successfully deleted!", Toast.LENGTH_SHORT).show();
                        removeTaskList();
                        populateTaskListView();
                    }
                }).setNegativeButton("No", null).show();
    }

}
