package com.example.admin_justeat;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;

    CardView cardViewCategory;
    CardView cardViewAddCategory;
    CardView cardViewAddDish;
    CardView cardViewOrders;
    TextView textUserName;

    private FirebaseAuth mAuth; // firebase session
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createRequest();

        mAuth = FirebaseAuth.getInstance(); // initialise firebase
        mDatabase= FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_main);

        //Button buttonLogout = findViewById(R.id.button_logout);
        TextView infoEmail = findViewById(R.id.textViewEmail);
        //ImageView infoImg = findViewById(R.id.imgUser);

        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);

        if (null == mAuth.getCurrentUser()) {
            Log.i("Firebase", "No user logged in, redirecting to login screen");
            startActivity(goToLogin);
        } else {
            Log.i("Firebase", "Loaded user " + mAuth.getCurrentUser().getEmail());
            Toast.makeText(getApplicationContext(), "Welcome " + mAuth.getCurrentUser().getEmail(),
                    Toast.LENGTH_SHORT).show();
            infoEmail.setText(mAuth.getCurrentUser().getEmail());
            //infoName.setText(mAuth.getCurrentUser().getDisplayName());

        }



        /*buttonLogout.setOnClickListener(new View.OnClickListener() {
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
        });*/
        //==========================================================//


        cardViewCategory = findViewById(R.id.cardViewCategory);
        cardViewAddCategory = findViewById(R.id.cardViewAddCategory);
        cardViewAddDish = findViewById(R.id.cardViewAddDish);
        cardViewOrders = findViewById(R.id.cardViewOrders);
        textUserName = findViewById(R.id.textUserName);

        // init constraintLayout
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        // initializing animation drawable by getting background from constraint layout
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        // setting enter fade animation duration to 4 seconds
        animationDrawable.setEnterFadeDuration(4000);
        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);


        cardViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"test", Toast.LENGTH_SHORT).show();
                //clicklistener para ir a otra actividad o fragment
            }
        });

        cardViewAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"test", Toast.LENGTH_SHORT).show();
            }
        });

        cardViewAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"test", Toast.LENGTH_SHORT).show();
            }
        });

        cardViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"test", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }
    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }
}