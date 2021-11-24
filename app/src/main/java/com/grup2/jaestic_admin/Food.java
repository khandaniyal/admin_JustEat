package com.grup2.jaestic_admin;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.Consumer;

// Class to manage Food items
public class Food {
    // List of all foods. It will be updated when the DB changes
    public static LinkedList<Food> foods = new LinkedList<>();
    // To ensure not repeating IDs, we will use a new number for each session and it will increase with each object added
    private static long nextId = System.currentTimeMillis()-1600000000000L;

    private int id;
    private String name;
    private String description;
    private String imagePath;
    private int price;
    private String categories;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public int getPrice(){ return price; }

    // Constructor is called with info from the database and creates java object. To add new Food item, use
    // addToDatabase();
    public Food(String name, String description, String imagePath, String categories, int price, int id) {
        // Create the item
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.categories = categories;
    }

    public static void addToDatabase(String name, String description, String imagePath, String categories, int price) {
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference()
                .child("Foods")
                .child(""+nextId);
        foodRef.child("name").setValue(name);
        foodRef.child("description").setValue(description);
        foodRef.child("imagePath").setValue(imagePath);
        foodRef.child("price").setValue(price);
        foodRef.child("categories").setValue(categories);
        // Increment next id
        nextId++;
    }

    // Database reference for retrieving data
    public static void init() {
        // this will attach the reference to the food node so it will be updated when the DB changes
        FirebaseDatabase.getInstance().getReference().child("Foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshotToList(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Method to retrieve the DB information as a Java list of objects
    static void snapshotToList(DataSnapshot snapshot) {
        Food.foods = new LinkedList<>();
        for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
            // For each food item, retrieve the data.
            int id = Integer.parseInt(foodSnapshot.getKey());
            String name = foodSnapshot.child("name").getValue().toString();
            String description = foodSnapshot.child("description").getValue().toString();
            String imagePath = foodSnapshot.child("imagePath").getValue().toString();
            String categories = foodSnapshot.child("categories").getValue().toString();
            int price = Integer.parseInt(foodSnapshot.child("price").getValue().toString());

            Food.foods.add(new Food(name, description, imagePath, categories, price, id));
        }
    }

    static int greatestId() {
        // returns the highest value in the keys of the food
        int greatest = 0;
        if (null != foods) for (Food food : foods) if (food.id > greatest) greatest = food.id;
        Log.i("DIVAC", "greatest food id found: " + greatest);
        return greatest;
    }
}
