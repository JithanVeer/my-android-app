package com.example.ruraleducationtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profilepage extends AppCompatActivity {
    private TextView name,email,role,subj;
    private FirebaseAuth fauth;
    private FirebaseFirestore fs;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        name=findViewById(R.id.view_name);
        email=findViewById(R.id.view_email);
        role=findViewById(R.id.view_role);
        subj=findViewById(R.id.view_sub);
        fauth=FirebaseAuth.getInstance();
        fs=FirebaseFirestore.getInstance();
        uid=fauth.getCurrentUser().getUid();
        DocumentReference df = fs.collection("users").document(uid);
        df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("username"));
                email.setText(value.getString("email"));
                role.setText(value.getString("role"));
                subj.setText(value.getString("AOE"));
            }
        });
    }
}