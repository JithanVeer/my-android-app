package com.example.ruraleducationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ruraleducationtracker.databinding.ActivitySignuppageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signuppage extends AppCompatActivity {
    private EditText username,id;
    private EditText email;
    private EditText password;
    private EditText contact;
    private Button signin;
    private EditText role;
    private FirebaseAuth fauth;
    private ProgressBar pro;
    FirebaseFirestore fs;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);

        username = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        contact = (EditText) findViewById(R.id.contact);
        signin = (Button)findViewById(R.id.sign);
        role = (EditText) findViewById(R.id.role);
        id=findViewById(R.id.uniq);
        fauth = FirebaseAuth.getInstance();
        fs= FirebaseFirestore.getInstance();
        pro = (ProgressBar)findViewById(R.id.progressBar);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useremail = email.getText().toString().trim();
                String userpass = password.getText().toString().trim();
                String fullname = username.getText().toString().trim();
                String phone = contact.getText().toString().trim();
                String pos = role.getText().toString().trim().toUpperCase();

                if(TextUtils.isEmpty(useremail)){
                    email.setError("invalid emailID");
                    return;
                }
                if(TextUtils.isEmpty(userpass)){
                    password.setError("invalid password");
                    return;
                }
                pro.setVisibility(View.VISIBLE);

                fauth.createUserWithEmailAndPassword(useremail,userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            uid=fauth.getCurrentUser().getUid();
                            String sid = id.getText().toString().trim().concat(uid);
                            Toast.makeText(signuppage.this , "Welcome to REC" , Toast.LENGTH_SHORT).show();

                                DocumentReference df = fs.collection("users").document(sid);
                                Map<String, Object> user = new HashMap<>();
                                user.put("username", fullname);
                                user.put("email", useremail);
                                user.put("role", pos);
                                user.put("contact", phone);
                                df.set(user);


                            startActivity(new Intent(signuppage.this , Welcome.class));
                        }
                        else
                        {
                            Toast.makeText(signuppage.this , "error!"+task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}