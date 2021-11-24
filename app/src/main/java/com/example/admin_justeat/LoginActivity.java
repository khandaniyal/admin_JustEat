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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth; // firebase session
    GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance(); // initialise firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        setContentView(R.layout.activity_login);

        // Text fields
        createRequest();
        TextView loginFieldEmail = findViewById(R.id.ETEmail);
        TextView loginFieldPassword = findViewById(R.id.ETPassword);

        Button loginButton = findViewById(R.id.register_button);
        Button registerButton = findViewById(R.id.login_register);
        Button GoogleButton = findViewById(R.id.loginGoogle);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the values of the text fields and pass them to the login method
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the values of the text fields and pass them to the login method
                tryLogin(loginFieldEmail.getText().toString(), loginFieldPassword.getText().toString());
            }
        });

        GoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
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
    private void createRequest() {        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google sign in failed " + e.toString(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Google sign in failed " + e.toString());
            }
        }
    }
    private void firebaseAuthWithGoogle(String IdToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(IdToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    @Override
    protected void onStart() {
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        super.onStart();
    }
}