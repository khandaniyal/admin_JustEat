package com.grup2.jaestic_admin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.function.Consumer;

// Class to manage Food items
public class Food {
    // List of all categories. It will be updated when the DB changes
    public static LinkedList<Food> foods;

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

    // Constructor should be called with a negative ID when creating a new Food item. If id argument is positive it means
    // that this is called from the database to update the info of an already existing item
    public Food(String name, String description, String imagePath, String categories, int price, int id) {
        // Calculate the greatest "id" used
        int greatestId = 0;
        // for (Food food : foods) if (food.getId() > greatestId) greatestId = food.getId();

        // Create the item
        this.id = id < 0 ? greatestId+1 : id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.categories = categories;

        // If this constructor is called to create a new item, it shall be added to the database
        if (id < 0) this.addToDatabase();
        // otherwise it means it is called just for update
    }

    public void addToDatabase() {
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference()
                .child("Foods")
                .child(""+this.id);
        foodRef.child("name").setValue(this.name);
        foodRef.child("description").setValue(this.description);
        foodRef.child("imagePath").setValue(this.imagePath);
        foodRef.child("price").setValue(this.price);
        foodRef.child("categories").setValue(this.categories);
    }

    // Database reference for retrieving data
    public static void init() {
        // this will attach the reference to the food node so it will be updated when the DB changes
        FirebaseDatabase.getInstance().getReference().child("Foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foods = new LinkedList<>();
                // Snapshot corresponds to the Foods node, so we have to look at all child nodes (the food items themselves)
                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    // For each food item, retrieve the data.
                    int id = Integer.parseInt("0");
                    String name = foodSnapshot.child("name").getValue().toString();
                    String description = foodSnapshot.child("description").getValue().toString();
                    String imagePath = foodSnapshot.child("imagePath").getValue().toString();
                    String categories = foodSnapshot.child("categories").getValue().toString();
                    int price = Integer.parseInt(foodSnapshot.child("price").getValue().toString());

                    foods.add(new Food(name, description, imagePath, categories, price, id));
                }
                Log.i("DIVAC", "There are " +foods.size()+ " dishes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
