package com.grup2.jaestic_admin.Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Random;

// Class to manage Food items
public class Dish {
    // List of all foods. It will be updated when the DB changes
    public static LinkedList<Dish> foods = new LinkedList<>();

    private int id;
    private String name;
    private String description;
    private String imagePath;
    private int price;
    private String categoryIDs;
    private FoodCategory[] categories;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public int getPrice(){ return price; }

    // Constructor is called with info from the database and creates java object. To add new Food item, use
    // addToDatabase();
    public Dish(String name, String description, String imagePath, String categoryIDs, int price, int id) {
        // Create the item
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.categoryIDs = categoryIDs;
        categories = FoodCategory.categoriesFromIDs(categoryIDs);
    }

    public static void addToDatabase(String name, String description, String categoryImagePath, FoodCategory[] categories, int price) {
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference()
                .child("Categories")
                .child("Foods");
        foodRef.child("name").setValue(name);
        foodRef.child("description").setValue(description);
        foodRef.child("imagePath").setValue(categoryImagePath);
        foodRef.child("price").setValue(price);
        foodRef.child("categories").setValue(FoodCategory.IDsFromCategories(categories));
    }

    // Database reference for retrieving data
    public static void init() {
        // this will attach the reference to the food node so it will be updated when the DB changes
        FirebaseDatabase.getInstance().getReference().child("Categories").child("Foods").addValueEventListener(new ValueEventListener() {
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
        Dish.foods = new LinkedList<>();
        for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
            // For each food item, retrieve the data.

            // if there is data missing, the element will not be created
            boolean ok = true;
            for(String key : new String[]{"name", "description", "imagePath", "categories", "price"}) {
                if (! foodSnapshot.child(key).exists()) ok = false;
            }
            if (!ok) continue;

            int id = Integer.parseInt(foodSnapshot.getKey());
            String name = foodSnapshot.child("name").getValue().toString();
            String description = foodSnapshot.child("description").getValue().toString();
            String imagePath = foodSnapshot.child("imagePath").getValue().toString();
            String categories = foodSnapshot.child("categories").getValue().toString();
            int price = Integer.parseInt(foodSnapshot.child("price").getValue().toString());

            Dish newFood = new Dish(name, description, imagePath, categories, price, id);
            // if it's already in the list, don't add it again (can happen due to connection problems)
            for (Dish existing : foods) if (newFood.equals(existing)) ok = false;

            // Finally if everything is OK, add it to the list
            if (ok) foods.add(newFood);
            else FirebaseDatabase.getInstance().getReference().child("Foods").child(""+id).removeValue();
        }
    }

    static int greatestId() {
        int greatest = 0;
        for (Dish food : foods) if (food.id > greatest) greatest = food.id;
        // in case there is a connection problem and it doesn't detect the categories, use a random number istead (to prevent overwriting)
        if (foods.size()==0) greatest = new Random().nextInt();
        return greatest;
    }

    // Compare everything except the id (to scan for repeated items)
    public boolean equals(Dish toCompare) {
        if (!name.equals(toCompare.name)) return false;
        if (!description.equals(toCompare.description)) return false;
        if (!imagePath.equals(toCompare.imagePath)) return false;
        if (price != toCompare.price) return false;
        return true;
    }

    // Get a Food object from the ID
    public static Dish fromID(int id) {
        for (Dish food : foods) if (id == food.id) return food;
        return null;
    }

    @Override
    public String toString(){
        return "ID " + id + " NAME " + name + " DESC " + description + " PRICE " + price + " CATEGORIES " + categories;
    }
}
