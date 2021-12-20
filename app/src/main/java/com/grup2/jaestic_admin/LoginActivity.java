package com.grup2.jaestic_admin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaestic_admin.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth; // firebase session
    GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 101;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance(); // initialise firebase

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_login);

        ////
        ////

        // Text fields
        createRequest();
        TextView loginFieldEmail = findViewById(R.id.ETEmail);
        TextView loginFieldPassword = findViewById(R.id.ETPassword);

        Button loginButton = findViewById(R.id.register_button);
        Button registerButton = findViewById(R.id.login_register);

        SignInButton signInButton = findViewById(R.id.google_login_button);
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

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert();
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
        startActivityForResult(signInIntent,RC_SIGN_IN );
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
                            DatosGoogle();
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
    private void DatosGoogle() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String personName = acct.getDisplayName();
        String personEmail = acct.getEmail();
        Map<String, Object> map =new HashMap<>();
        map.put("Name",personName);
        map.put("Email",personEmail);
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map);

    }

    @Override
    protected void onStart() {
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
        super.onStart();
    }
    private void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vincular cuenta de gmail con google");
        builder.setMessage("Si tienes una cuenta con gmail se sobreescribiran los datos de la cuenta a la de google.\n" +
                "¿Estas seguro que quiere que se sobreescriban?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                signIn();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}