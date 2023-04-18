package com.cw.notas.Checklist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.notas.DatabaseHelper;
import com.cw.notas.R;

import java.util.ArrayList;
import java.util.Objects;

public class CheckboxAdapter extends BaseAdapter{
    private DatabaseHelper db;
    private Context context;
    private ArrayList<Checkbox> checkboxes;
    private LayoutInflater inflater;
    CheckBox cBox;
    TextView checkboxTitle;
    ImageButton checkboxDelete;


    public CheckboxAdapter(Context context, ArrayList<Checkbox> checkboxes) {
      //  super(context, resource, checkboxes);
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
        }

        Checkbox currentCheckbox = (Checkbox)getItem(position);

        checkboxTitle = view.findViewById(R.id.checkboxTitle);
        checkboxTitle.setText(currentCheckbox.getTitle());

        checkboxDelete = (ImageButton) view.findViewById(R.id.btnDeleteCheckbox);
        //checkboxTitle.setText(currentCheckbox.getDueDate());


        cBox =  view.findViewById(R.id.checkboxBox);

        if(Objects.equals(currentCheckbox.getState(), "1")) {
            cBox.setChecked(true);
        } else {
            cBox.setChecked(false);
        }

        checkboxDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.app_sure)
                        .setMessage(R.string.chk_options_menu_delChkBox)
                        .setPositiveButton(R.string.app_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db = new DatabaseHelper(context.getApplicationContext());
                                db.checkboxDelete(currentCheckbox.getId());
                                notifyDataSetChanged();
                                Intent intent = new Intent("Check_Box_Delete");
                                context.sendBroadcast(intent);

                            }
                        }).setNegativeButton(R.string.app_cancel, null).show();


            }
        });

        cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    currentCheckbox.setState("1");

                } else {
                    currentCheckbox.setState("0");
                }

               db = new DatabaseHelper(context.getApplicationContext());
               db.checkboxUpdate(String.valueOf(currentCheckbox.getId()), String.valueOf(currentCheckbox.getTitle()), String.valueOf(currentCheckbox.getState()));
            }
        });


        return view;
    }







}
