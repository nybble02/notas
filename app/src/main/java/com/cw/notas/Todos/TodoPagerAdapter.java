package com.cw.notas.Todos;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cw.notas.Todos.Fragments.DoingFragment;
import com.cw.notas.Todos.Fragments.DoneFragment;
import com.cw.notas.Todos.Fragments.TodoFragment;

public class TodoPagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public TodoPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tabFragment = null;

        switch (position) {
            case 0:
                tabFragment = new TodoFragment();
                Log.d("frag", String.valueOf(position));
                return tabFragment;
            case 1:
                tabFragment = new DoingFragment();
                Log.d("frag", String.valueOf(position));
                return tabFragment;
            case 2:
                tabFragment = new DoneFragment();
                Log.d("frag", String.valueOf(position));

                return tabFragment;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";

        switch (position) {
            case 0:
                return title = "To Do";
            case 1:
                return title = "Doing";
            case 2:
                return title = "Done";
            default:
                return null;
        }


       // return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
