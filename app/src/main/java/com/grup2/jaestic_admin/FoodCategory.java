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
    // List of all categories
    public static LinkedList<FoodCategory> categories = new LinkedList<>();

    private int id;
    private String name;
    private String description;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public FoodCategory(String name, String description, int id) {
        // Calculate the greatest "id" used
        int greatestId = 0;
        // for (FoodCategory cat : categories) if (cat.getId() > greatestId) greatestId = cat.getId();

        // Create the item
        this.id = id < 0 ? greatestId+1 : id;
        this.name = name;
        this.description = description;

        // If this constructor is called to create a new item, it shall be added to the database
        if (id < 0) this.addToDatabase();
        // otherwise it means it is called just for update
    }

    public void addToDatabase() {
        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference()
                .child("Categories")
                .child(""+this.id);
        catRef.child("name").setValue(this.name);
        catRef.child("description").setValue(this.description);
    }

    // Database reference for retrieving data
    public static void init() {
        // this will attach the reference to the food node so it will be updated when the DB changes
        FirebaseDatabase.getInstance().getReference().child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categories = new LinkedList<>();
                // Snapshot corresponds to the Foods node, so we have to look at all child nodes (the food items themselves)
                for (DataSnapshot catSnapshot : snapshot.getChildren()) {
                    // For each food item, retrieve the data.
                    int id = Integer.parseInt("0");
                    String name = catSnapshot.child("name").getValue().toString();
                    String description = catSnapshot.child("description").getValue().toString();

                    categories.add(new FoodCategory(name, description, id));
                }
                Log.i("DIVAC", "There are " +categories.size()+ " categories");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
