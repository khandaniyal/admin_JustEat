package com.example.admin_justeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // firebase session
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createRequest();
        mAuth = FirebaseAuth.getInstance(); // initialise firebase

        setContentView(R.layout.activity_main);

        Button buttonLogout = findViewById(R.id.button_logout);
        TextView infoEmail = findViewById(R.id.info_email);

        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);

        if (null == mAuth.getCurrentUser()) {
            Log.i("Firebase", "No user logged in, redirecting to login screen");
            startActivity(goToLogin);
        } else {
            Log.i("Firebase", "Loaded user " + mAuth.getCurrentUser().getEmail());
            Toast.makeText(getApplicationContext(), "Welcome " + mAuth.getCurrentUser().getEmail(),
                    Toast.LENGTH_SHORT).show();
            infoEmail.setText(mAuth.getCurrentUser().getEmail());
        }



        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(goToLogin);
                            MainActivity.this.finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"No se a podido cerrar session con google",
                                Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                startActivity(goToLogin);
            }
        });
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
}