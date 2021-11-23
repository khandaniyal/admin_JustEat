package com.example.admin_justeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth; // firebase session
    DatabaseReference mDatabase;

    private EditText EditTextEmail;
    private EditText EditTextPassword;
    private Button ButtonLogin;

    private String email="";
    private String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        EditTextEmail = findViewById(R.id.ETEmail);
        EditTextPassword= (EditText) findViewById(R.id.ETPassword);
        ButtonLogin= (Button) findViewById(R.id.register_button);


        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=EditTextEmail.getText().toString();
                password=EditTextPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()){
                    Log.i("prueba","ghoal");
                    RegisterUser(email,password);
                }


            }
        });

    }
    private void RegisterUser(String email, String password) {
        // Call the Firebase login with given email and password
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("prueba","hoal");
                        if (task.isSuccessful()) {
                            Map<String, Object> map =new HashMap<>();
                            map.put("email",email);
                            map.put("password",password);
                            String id= mAuth.getCurrentUser().getUid();

                            mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if(task2.isSuccessful()){
                                        Intent registerok = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(registerok);
                                    }else{

                                    }

                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this,"no se a creado",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}