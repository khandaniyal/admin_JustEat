package com.example.admin_justeat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;

    CardView cardViewCategory;
    CardView cardViewAddCategory;
    CardView cardViewAddDish;
    CardView cardViewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardViewCategory = findViewById(R.id.cardViewCategory);
        cardViewAddCategory = findViewById(R.id.cardViewAddCategory);
        cardViewAddDish = findViewById(R.id.cardViewAddDish);
        cardViewOrders = findViewById(R.id.cardViewOrders);


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
}