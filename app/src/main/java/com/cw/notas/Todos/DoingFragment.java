package com.cw.notas.Todos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cw.notas.R;

public class DoingFragment extends Fragment {

    public DoingFragment(){}

    TextView test;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment, container, false);

        test = (TextView) view.findViewById(R.id.title);
        test.setText("Fragment two");

        return view;
    }
}
