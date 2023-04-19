package com.cw.notas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cw.notas.Checklist.ChecklistListActivity;
import com.cw.notas.Notes.NoteListActivity;
import com.cw.notas.Todo.TodoActivity;
import com.google.android.material.navigation.NavigationView;


public class BaseActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navDrawer;

    protected void onCreateDrawer() {

        // Create nav drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navDrawer = findViewById(R.id.navDrawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId())
                {
                    case R.id.navHome:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navNotes:
                        intent = new Intent(getApplicationContext(), NoteListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navChecklist:
                        intent = new Intent(getApplicationContext(), ChecklistListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navTodos:
                        intent = new Intent(getApplicationContext(), TodoActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Close the drawer when the back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       if( drawerLayout.isDrawerOpen(Gravity.LEFT)) {
           drawerLayout.closeDrawer(Gravity.LEFT);
       } else {
           super.onBackPressed();
       }
    }





}
