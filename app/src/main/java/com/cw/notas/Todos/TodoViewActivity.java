package com.cw.notas.Todos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.cw.notas.BaseActivity;
import com.cw.notas.Checklist.ChecklistViewActivity;
import com.cw.notas.Database;
import com.cw.notas.R;
import com.cw.notas.Todos.TodoPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.UUID;

public class TodoViewActivity extends BaseActivity {

    private Database db;
    TabLayout tabLayout;
    ViewPager viewPager;
    TodoPagerAdapter pgAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.todos_options_menu, menu);

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
                pgAdapter.notifyDataSetChanged();


                break;
            case R.id.option_delete_items:
                // Do something
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_view);
        onCreateDrawer();

        tabLayout = findViewById(R.id.todoTabLayout);
        viewPager = findViewById(R.id.todoViewPager);

        pgAdapter = new TodoPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pgAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

    }

    private void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TodoViewActivity.this);
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
                        String todoTitle = String.valueOf(etCheckboxTitle.getText());
                        String todoId = uuid.toString();

                        db = new Database(getApplicationContext());
                        db.todoInsert(todoId, todoTitle,"0");
                        pgAdapter.notifyDataSetChanged();
                        viewPager.setAdapter(pgAdapter);





                    }
                }).setNegativeButton(R.string.app_cancel, null).show();

    }
}
