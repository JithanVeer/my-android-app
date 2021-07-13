package com.example.ruraleducationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class teacher extends AppCompatActivity {

    private EditText aoe;
    private Button enroll;
    private Button home;
    private FirebaseAuth fauth;
    private FirebaseFirestore fs;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        aoe=(EditText)findViewById(R.id.aoe);
        enroll=(Button)findViewById(R.id.enroll);
        home=(Button)findViewById(R.id.home1);
        fauth=FirebaseAuth.getInstance();
        fs=FirebaseFirestore.getInstance();
        uid=fauth.getCurrentUser().getUid();
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useraoe = aoe.getText().toString().toUpperCase().trim();
                DocumentReference df=fs.collection("users").document(uid);
                Map<String,Object> user=new HashMap<>();
                user.put("AOE",useraoe);
                user.put("Availability","yes");
                df.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(teacher.this,"You are enrolled",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                enroll.setEnabled(false);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(teacher.this,Welcome.class));
            }
        });
    }
}