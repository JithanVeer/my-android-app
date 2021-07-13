package com.example.ruraleducationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Student extends AppCompatActivity {
    private EditText weaksub;
    private Button enroll,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        weaksub=findViewById(R.id.stweak);
        enroll=findViewById(R.id.stenroll);
        home=findViewById(R.id.sthome);

    }
}