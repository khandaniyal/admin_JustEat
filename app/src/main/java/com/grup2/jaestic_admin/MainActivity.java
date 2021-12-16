package com.grup2.jaestic_admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.jaestic_admin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.grup2.jaestic_admin.Fragments.AddFragment;
import com.grup2.jaestic_admin.Fragments.BlankFragment;
import com.grup2.jaestic_admin.Fragments.ListFoodFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grup2.jaestic_admin.Model.Dish;
import com.grup2.jaestic_admin.Model.FoodCategory;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // firebase session
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance(); // initialise firebase
        Dish.init();
        FoodCategory.init();
        //Log.i("DIVAC","Fuuds: " + Dish.foods.size());
        //Log.i("DIVAC","Catagoreries: " + FoodCategory.categories.size());
        // It takes a little while to load the database, until then we'll wait
        setContentView(R.layout.loading);
        SystemClock.sleep(1000);

        //if ((Dish.foods.size()==0 || FoodCategory.categories.size()==0))startActivity(new Intent(getApplicationContext(), MainActivity.class));
        /*else {
            setContentView(R.layout.home_bottom_nav);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListFoodFragment()).commit();
            BottomNavigationView bottomNav = findViewById(R.id.main_menu);
            bottomNav.setOnItemSelectedListener(item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new BlankFragment();
                        break;
                    case R.id.nav_list:
                        selectedFragment = new ListFoodFragment();
                        break;

                    case R.id.nav_add:
                        selectedFragment = new AddFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            });
        }*/
        setContentView(R.layout.home_bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListFoodFragment()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.main_menu);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new BlankFragment();
                    break;
                case R.id.nav_list:
                    selectedFragment = new ListFoodFragment();
                    break;

                case R.id.nav_add:
                    selectedFragment = new AddFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });
    }

}