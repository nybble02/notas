package com.cw.notas.Todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cw.notas.CalendarHelper;
import com.cw.notas.Database;
import com.cw.notas.MainActivity;
import com.cw.notas.Notes.NoteListActivity;
import com.cw.notas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTaskActivity extends AppCompatActivity {

    private Database db;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText taskTitle = findViewById(R.id.etTaskTitle);
        Button btnDate = findViewById(R.id.btnDate);
        Button btnTime = findViewById(R.id.btnTime);
        Button btnSave = findViewById(R.id.btnSave);
        TextView dateText = findViewById(R.id.tvDate);
        TextView timeText = findViewById(R.id.tvTime);
        TextView pgTitle = findViewById(R.id.pgTitle);


        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId");

        if (taskId != null) {
            pgTitle.setText(getResources().getString(R.string.todos_menu_edit));
        }


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = dateFormat.format(calendar.getTime());

                        dateText.setText(formattedDate);
                    }
                });
                datePickerDialog.show();
            }
        });
      btnTime.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              calendar = Calendar.getInstance();

              TimePickerDialog timePickerDialog = new TimePickerDialog(
                      AddTaskActivity.this,
                      new TimePickerDialog.OnTimeSetListener() {
                          @Override
                          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                              SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                              calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                              calendar.set(Calendar.MINUTE, minute);
                              String formattedTime = timeFormat.format(calendar.getTime());

                              timeText.setText(formattedTime);
                          }
                      },
                      calendar.get(Calendar.HOUR_OF_DAY),
                      calendar.get(Calendar.MINUTE),
                      true
              );
              timePickerDialog.show();
          }
      });


      btnSave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            if(taskTitle.getText() != null) {
                UUID uuid = UUID.randomUUID(); // Generate random unique id
                String taskId = uuid.toString();

                long startTime = calendar.getTimeInMillis();
                Log.d("TAG", "Milliseconds: " + startTime);
                long endTime = startTime + 3600000; // 1 hour
                long calId =  CalendarHelper.getCalendarId(AddTaskActivity.this);
                CalendarHelper.setEvent(AddTaskActivity.this, String.valueOf(taskTitle.getText()),"", startTime, endTime, calId);

                db = new Database(getApplicationContext());
                db.todoInsert(taskId, String.valueOf(taskTitle.getText()),"0", startTime);


                Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(AddTaskActivity.this, "Enter a title, due date and reminder time", Toast.LENGTH_SHORT);
            }

          }
      });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
        startActivity(intent);

        this.finish();
    }
}
