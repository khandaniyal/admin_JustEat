package com.example.admin_justeat;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // firebase session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance(); // initialise firebase

        setContentView(R.layout.activity_login);

        // Text fields
        TextView loginFieldEmail = findViewById(R.id.login_field_email);
        TextView loginFieldPassword = findViewById(R.id.login_field_password);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the values of the text fields and pass them to the login method
                tryLogin(loginFieldEmail.getText().toString(), loginFieldPassword.getText().toString());
            }
        });
    }

    private void tryLogin(String email, String password) {
        // Call the Firebase login with given email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // If Login is correct, load the Main activity
                            Log.d(TAG, "signInWithEmail:success");
                            Intent loginOK = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(loginOK);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), R.string.login_err,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}