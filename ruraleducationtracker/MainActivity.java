package com.example.ruraleducationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button login;
    private Button signin;
    private FirebaseAuth fauth;
    private  int counter=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.ename);
        password = (EditText)findViewById(R.id.etpassword);
        login = (Button)findViewById(R.id.button);
        signin = (Button)findViewById(R.id.signup);
        fauth= FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = name.getText().toString().trim();
                String userpass = password.getText().toString().trim();

                if(TextUtils.isEmpty(useremail)){
                    name.setError("invalid emailID");
                    return;
                }
                if(TextUtils.isEmpty(userpass)){
                    password.setError("invalid password");
                    return;
                }

                fauth.signInWithEmailAndPassword(useremail,userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this , "Login successful" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this , Welcome.class));
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this , "error!"+task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , signuppage.class);
                startActivity(intent);
            }
        });
    }

    /*private void validate(String username ,String password){
        if(username.equals("admin") && password.equals("12345")){
            Intent intent = new Intent(MainActivity.this , Welcome.class);
            startActivity(intent);
            Context context = getApplicationContext();
            CharSequence text = "login successful";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Incorrect Credentials";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            counter--;
            if(counter==0)
            {
                CharSequence text1 = "account locked";
                Toast toast1 = Toast.makeText(context , text1,duration);
                login.setEnabled(false);
            }
        }
    }*/
}