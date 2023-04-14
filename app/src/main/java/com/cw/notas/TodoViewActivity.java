package com.cw.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.cw.notas.Todos.TodoPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TodoViewActivity extends BaseActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_view);
        onCreateDrawer();

        tabLayout = findViewById(R.id.todoTabLayout);
        viewPager = findViewById(R.id.todoViewPager);

        TodoPagerAdapter pgAdapter = new TodoPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pgAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}
