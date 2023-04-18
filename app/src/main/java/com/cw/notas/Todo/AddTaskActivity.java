package com.cw.notas.Todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.cw.notas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class AddTaskActivity extends AppCompatActivity {

    private Database db;
    Calendar calendar;
    final int REQUEST_PERMISSIONS = 100;
    String taskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        EditText etTaskTitle = findViewById(R.id.etTaskTitle);
        Button btnDate = findViewById(R.id.btnDate);
        Button btnTime = findViewById(R.id.btnTime);
        Button btnSave = findViewById(R.id.btnSave);
        TextView dateText = findViewById(R.id.tvDate);
        TextView timeText = findViewById(R.id.tvTime);
        TextView pgTitle = findViewById(R.id.pgTitle);


        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId");

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

              taskTitle = etTaskTitle.getText().toString().trim();

            if (taskTitle.isEmpty()) {
                Toast.makeText(AddTaskActivity.this, R.string.todos_add_dueDateError, Toast.LENGTH_SHORT).show();
            }
            else {
                UUID uuid = UUID.randomUUID(); // Generate random unique id
                String taskId = uuid.toString();

                if (ContextCompat.checkSelfPermission(AddTaskActivity.this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(AddTaskActivity.this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    // Permissions granted, proceed with event creation
                    long startTime = calendar.getTimeInMillis();
                    long endTime = startTime + 3600000; // 1 hour
                    long calId = CalendarHelper.getCalendarId(AddTaskActivity.this);
                    CalendarHelper.setEvent(AddTaskActivity.this, String.valueOf(taskTitle), "", startTime, endTime, calId);

                    db = new Database(getApplicationContext());
                    db.todoInsert(taskId, String.valueOf(taskTitle), "0");

                    Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                    startActivity(intent);
                } else {
                    // Request permissions
                    ActivityCompat.requestPermissions(AddTaskActivity.this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, REQUEST_PERMISSIONS);
                }


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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                UUID uuid = UUID.randomUUID(); // Generate random unique id
                String taskId = uuid.toString();

                // Permissions granted, proceed with event creation
                long startTime = calendar.getTimeInMillis();
                long endTime = startTime + 3600000; // 1 hour
               // long calId = CalendarHelper.getCalendarId(AddTaskActivity.this);
                long calId = 2;
                CalendarHelper.setEvent(AddTaskActivity.this, String.valueOf(taskTitle), "", startTime, endTime, calId);

                db = new Database(getApplicationContext());
                db.todoInsert(taskId, String.valueOf(taskTitle), "0");

                Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                startActivity(intent);
            } else {
                // Permissions denied, show an error message or disable the feature
                Toast.makeText(AddTaskActivity.this, "Calendar permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
