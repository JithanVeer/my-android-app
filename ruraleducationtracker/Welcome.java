package com.example.ruraleducationtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

public class Welcome extends AppCompatActivity {
    private ImageButton btn;
    private TextView role;
    private Button logout;
    private FirebaseAuth fauth;
    private ProgressBar p1;
    private FirebaseFirestore fs;
    private ImageButton prof;
    private String uid;
    private EditText id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        id=findViewById(R.id.uniq);
        role=(TextView)findViewById(R.id.rview);
        btn=(ImageButton)findViewById(R.id.mentor);
        logout=(Button)findViewById(R.id.logout);
        p1=(ProgressBar)findViewById(R.id.round);
        fauth=FirebaseAuth.getInstance();
        fs=FirebaseFirestore.getInstance();
        prof=(ImageButton)findViewById(R.id.profile);
        uid=fauth.getCurrentUser().getUid();
        String sid=id.getText().toString().trim().concat(uid);
        DocumentReference df = fs.collection("users").document(sid);
        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                role.setText(value.getString("role"));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1.setVisibility(View.VISIBLE);
                String userrole;
                userrole=role.getText().toString().trim();
                if(userrole.equals("TEACHER"))
                {
                    startActivity(new Intent(Welcome.this,teacher.class));
                }
                else
                {
                    startActivity(new Intent(Welcome.this,Student.class));
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Welcome.this,MainActivity.class));
                //finish();
            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this,Profilepage.class));
            }
        });
    }
}