package com.cw.notas;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.notas.Checklist.Checkbox;
import com.cw.notas.Checklist.ChecklistListActivity;
import com.cw.notas.Checklist.ChecklistViewActivity;
import com.cw.notas.Notes.NoteAddActivity;

import java.util.ArrayList;

public class CheckboxAdapter extends BaseAdapter{
    protected Database db;
    protected Context context;
    protected ArrayList<Checkbox> checkboxes;
    public boolean[] checkedHolder;
    protected LayoutInflater inflater;


    public CheckboxAdapter(Context context, ArrayList<Checkbox> checkboxes) {
        this.context = context;
        this.checkboxes = checkboxes;
        this.inflater = LayoutInflater.from(context);

        createCheckedHolder();
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
        }

        Checkbox currentCheckbox = getItem(position);

        TextView checboxTitle = view.findViewById(R.id.checkboxTitle);
        checboxTitle.setText(currentCheckbox.getTitle());

        CheckBox cBox =  view.findViewById(R.id.checboxBox);

        cBox.setChecked(checkedHolder[position]);

        if(currentCheckbox.getState() == "1")  {
            cBox.setChecked(true);
        }
        else {
            cBox.setChecked(false);
        }

        cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                checkedHolder[position] = isChecked;


            }
        });

       /* cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String strIsChecked;

                if (isChecked)  {
                     strIsChecked = "1";
                } else {
                     strIsChecked = "0";
                }

                db = new Database(context.getApplicationContext());
                db.checkboxUpdate(currentCheckbox.getId(),currentCheckbox.getTitle(), strIsChecked);




            }

        });*/

        /*cBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isChecked = cBox.isChecked();
                Toast.makeText(context.getApplicationContext(), currentCheckbox.getTitle() + " " + isChecked, Toast.LENGTH_SHORT).show();



            }
        });*/
        return view;
    }

    private void createCheckedHolder() {
        checkedHolder = new boolean[getCount()];
    }



}
