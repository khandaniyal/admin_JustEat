package com.grup2.jaestic_admin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

// Class to manage food categories
public class FoodCategory {
    // List of all categories. It will be updated when the DB changes
    public static LinkedList<FoodCategory> foods = new LinkedList<>();
    // To ensure not repeating IDs, we will use a new number for each session and it will increase with each object added
    private static long nextId = System.currentTimeMillis()-1700000000000L;

    private int id;
    private String name;
    private String description;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    // Constructor is called with info from the database and creates java object. To add new Food item, use
    // addToDatabase();
    public FoodCategory(String name, String description, int id) {
        // Create the item
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static void addToDatabase(String name, String description) {
        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference()
                .child("Categories")
                .child(""+nextId);
        catRef.child("name").setValue(name);
        catRef.child("description").setValue(description);
        // Increment next id
        nextId++;
    }

    // Database reference for retrieving data
    public static void init() {
        // this will attach the reference to the food node so it will be updated when the DB changes
        FirebaseDatabase.getInstance().getReference().child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
