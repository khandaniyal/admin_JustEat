package com.grup2.jaestic_admin.Fragments;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.jaestic_admin.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grup2.jaestic_admin.LoginActivity;
import com.grup2.jaestic_admin.MainActivity;

public class MenuFragment extends Fragment {
    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;
    CardView cardViewCategory;
    CardView cardViewAddCategory;
    CardView cardViewAddDish;
    CardView cardViewOrders;
    TextView userName;
    TextView infoEmail;
    ImageView imgUser;

    private FirebaseAuth mAuth; // firebase session
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        super.onCreate(savedInstanceState);
        createRequest();

        mAuth = FirebaseAuth.getInstance(); // initialise firebase
        mDatabase= FirebaseDatabase.getInstance().getReference();

        Button buttonLogout = view.findViewById(R.id.button_logout);
        infoEmail = view.findViewById(R.id.textViewEmail);
        userName = view.findViewById(R.id.textUserName);
        imgUser = view.findViewById(R.id.imgUser);

        Intent goToLogin = new Intent(getContext(), LoginActivity.class);

        if (null == mAuth.getCurrentUser()) {
            Log.i("Firebase", "No user logged in, redirecting to login screen");
            startActivity(goToLogin);
        } else {
            Log.i("Firebase", "Loaded user " + mAuth.getCurrentUser().getEmail());
            Toast.makeText(getContext(), "Welcome " + mAuth.getCurrentUser().getEmail(),
                    Toast.LENGTH_SHORT).show();
            infoEmail.setText(mAuth.getCurrentUser().getEmail());
            userName.setText(mAuth.getCurrentUser().getDisplayName());
            imgUser.setImageURI(mAuth.getCurrentUser().getPhotoUrl()); //no funciona ._.?

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
                            //MainActivity.this.finish();
                        }else{
                            Toast.makeText(getContext(),"No se a podido cerrar session con google",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                startActivity(goToLogin);
            }
        });
        //==========================================================//


        cardViewCategory = view.findViewById(R.id.cardViewCategory);
        cardViewAddCategory = view.findViewById(R.id.cardViewAddCategory);
        cardViewAddDish = view.findViewById(R.id.cardViewAddDish);
        cardViewOrders = view.findViewById(R.id.cardViewOrders);
        userName = view.findViewById(R.id.textUserName);
        imgUser = view.findViewById(R.id.imgProfile);

        // init constraintLayout
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayout);
        // initializing animation drawable by getting background from constraint layout
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        // setting enter fade animation duration to 4 seconds
        animationDrawable.setEnterFadeDuration(4000);
        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);


        cardViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Going to Category", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ListFoodFragment()).commit();

            }
        });

        cardViewAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Going to Add Category", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddCategory()).commit();
            }
        });

        cardViewAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Going to Add Dish", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddFragment()).commit();
            }
        });

        cardViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Going to Orders", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }
    }
    @Override
    public void onPause() {
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
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }
}