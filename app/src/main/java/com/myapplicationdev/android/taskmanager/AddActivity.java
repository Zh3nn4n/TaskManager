package com.myapplicationdev.android.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    EditText etName, etDes, etTime;
    Button btnAdd, btnCancel;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etDes = findViewById(R.id.etDes);
        etTime = findViewById(R.id.etTime);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btncancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().length() == 0 ||
                        etDes.getText().toString().length() == 0 || etTime.getText().toString().length() == 0) {

                    Toast.makeText(AddActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                }else {
                    DBHelper dbh = new DBHelper(AddActivity.this);

                    String name = etName.getText().toString();
                    String description = etDes.getText().toString();
                    int time = Integer.parseInt(etTime.getText().toString());

                    long inserted_id = dbh.insertTask(name,description);
                    dbh.close();
                    if (inserted_id != -1) {
                        Toast.makeText(AddActivity.this, "Insert successful",
                                Toast.LENGTH_SHORT).show();
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, time);

                        Intent intent = new Intent(AddActivity.this,ScheduledNotificationReceiver.class);
                        intent.putExtra("name",name);
                        intent.putExtra("description",description);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                AddActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent);

                        finish();
                    }

                }
            }
        });
    }
}
