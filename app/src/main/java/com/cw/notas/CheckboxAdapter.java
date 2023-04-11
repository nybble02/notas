package com.cw.notas;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cw.notas.Checklist.Checkbox;

import java.util.ArrayList;

public class CheckboxAdapter extends BaseAdapter implements RadioGroup.OnCheckedChangeListener {

    private Context context;
    private ArrayList<Checkbox> checkboxes;
    private LayoutInflater inflater;


    public CheckboxAdapter(Context context, ArrayList<Checkbox> checkboxes) {
        this.context = context;
        this.checkboxes = checkboxes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return checkboxes.size();
    }

    @Override
    public Checkbox getItem(int i) {
        return checkboxes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LinearLayout linearLayout = (LinearLayout) view;
        if (view == null) {
            view = (LinearLayout) inflater.inflate(R.layout.view_checkbox_list_adapter, viewGroup, false);
            //view =  (LinearLayout) inflater.inflate(R.layout.dialog_add_checkbox, null);
        }

        Checkbox currentCheckbox = getItem(position);

        TextView checboxTitle = view.findViewById(R.id.checkboxTitle);
        checboxTitle.setText(currentCheckbox.getTitle());

        CheckBox cBox =  view.findViewById(R.id.checboxBox);

        if(currentCheckbox.getState() == "1")  {
            cBox.setChecked(true);
        }
        else {
            cBox.setChecked(false);
        }
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }



}
