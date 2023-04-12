package com.cw.notas;

import android.content.Context;
import android.graphics.Paint;
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
import java.util.Objects;

public class CheckboxAdapter extends ArrayAdapter<Checkbox>{
    protected Database db;
    protected Context context;
    protected ArrayList<Checkbox> checkboxes;
    public Boolean[] checkedHolder;
    protected LayoutInflater inflater;
    CheckBox cBox;
    TextView checkboxTitle;


    public CheckboxAdapter(Context context, int resource, ArrayList<Checkbox> checkboxes) {
        super(context, resource, checkboxes);
        this.context = context;
        this.checkboxes = checkboxes;
        this.inflater = LayoutInflater.from(context);
        this.checkedHolder = new Boolean[checkboxes.size()];
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

        Checkbox currentCheckbox = (Checkbox)getItem(position);

        checkboxTitle = view.findViewById(R.id.checkboxTitle);
        checkboxTitle.setText(currentCheckbox.getTitle());

        cBox =  view.findViewById(R.id.checkboxBox);

        if(Objects.equals(currentCheckbox.getState(), "1")) {
            cBox.setChecked(true);
       //   Toast.makeText(context.getApplicationContext(), String.valueOf(currentCheckbox.getState()), Toast.LENGTH_SHORT).show();

        } else {
            cBox.setChecked(false);
   //        Toast.makeText(context.getApplicationContext(), String.valueOf(currentCheckbox.getState()), Toast.LENGTH_SHORT).show();

        }

        cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                if(isChecked) {
                    currentCheckbox.setState("1");
                    //checkboxTitle.setPaintFlags(checkboxTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    //Toast.makeText(context.getApplicationContext(), String.valueOf(currentCheckbox.getTitle()) + " " + String.valueOf(currentCheckbox.getState()) , Toast.LENGTH_SHORT).show();


                } else {
                    currentCheckbox.setState("0");
                    //checkboxTitle.setPaintFlags(checkboxTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    //Toast.makeText(context.getApplicationContext(), String.valueOf(currentCheckbox.getTitle()) + " " + String.valueOf(currentCheckbox.getState()) , Toast.LENGTH_SHORT).show();

                }

               db = new Database(context.getApplicationContext());
               db.checkboxUpdate(String.valueOf(currentCheckbox.getId()), String.valueOf(currentCheckbox.getTitle()), String.valueOf(currentCheckbox.getState()));
               Toast.makeText(context.getApplicationContext(), String.valueOf(currentCheckbox.getTitle()) + " " + String.valueOf(currentCheckbox.getState()) , Toast.LENGTH_SHORT).show();


            }
        });


        return view;
    }






}
