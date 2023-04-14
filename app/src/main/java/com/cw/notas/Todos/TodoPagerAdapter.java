package com.cw.notas.Todos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Objects;

public class TodoPagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public TodoPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TodoFragment todoTab = new TodoFragment();
                return todoTab;
            case 1:
                DoingFragment doingTab = new DoingFragment();
                return doingTab;
            case 2:
                DoneFragment doneTab = new DoneFragment();
                return doneTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
