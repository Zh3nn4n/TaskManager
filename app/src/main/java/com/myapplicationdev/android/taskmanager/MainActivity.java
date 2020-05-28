package com.myapplicationdev.android.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button btnAdd;
    ArrayAdapter aa;
    ArrayList<Tasks> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        btnAdd = findViewById(R.id.btnAdd);

        DBHelper db = new DBHelper(MainActivity.this);
        al = db.getAllTasks();
        db.close();

        aa = new TaskAdapter(this, R.layout.row, al);
        lv.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        DBHelper db = new DBHelper(MainActivity.this);
        al.clear();
        al = db.getAllTasks();
        db.close();

        aa = new TaskAdapter(this, R.layout.row, al);
        lv.setAdapter(aa);
    }
}
