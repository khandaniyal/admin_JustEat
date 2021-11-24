package com.grup2.jaestic_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaestic_admin.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // firebase session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance(); // initialise firebase
        Food.init();
        FoodCategory.init();

        setContentView(R.layout.activity_main);

        TextView infoEmail = findViewById(R.id.info_email);

        // Redirect to login screen.
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

        Button buttonLogout = findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(goToLogin);
            }
        });

        // PROVISIONAL MENTRE NO IMPLEMENTEM EL MENÚ
        Button fuuds = findViewById(R.id.fuuds);
        Intent goToFoods = new Intent(getApplicationContext(), FoodList.class);
        fuuds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goToFoods);
            }
        });

        // PROVA DE CARREGAR DB
        FoodCategory pasta = new FoodCategory("pasta", "mmmm deliciosa", -1);
        pasta.addToDatabase();
        Food ravioli = new Food("ravioli", "pasta quadrada amb coses dins", "DEFAULT", "1", 850, -1);
        ravioli.addToDatabase();

    }
}