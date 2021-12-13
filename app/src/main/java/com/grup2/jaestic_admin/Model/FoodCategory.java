package com.grup2.jaestic_admin.Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Random;

// Class to manage food categories
public class FoodCategory {
    // List of all categories. It will be updated when the DB changes
    public static LinkedList<FoodCategory> categories = new LinkedList<>();

    private int id;
    private String name;
    private String description;
    private String imagePath;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }

    // Constructor is called with info from the database and creates java object. To add new Food item, use
    // addToDatabase();
    //empty builder
    public FoodCategory() { }
    public FoodCategory(String name, String description, String imagePath, int id) {
        // Create the item
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }
    //temporary constructor
    public FoodCategory(String name) {
        this.name = name;
    }

    public static void addToDatabase(String name, String description, String imagePath) {
        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference()
                .child("Categories")
                .child(""+(greatestId()+1));
        catRef.child("name").setValue(name);
        catRef.child("description").setValue(description);
        catRef.child("imagePath").setValue(imagePath);
    }

    // Database reference for retrieving data
    public static void init() {
        // this will attach the reference to the food node so it will be updated when the DB changes
        FirebaseDatabase.getInstance().getReference().child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshotToList(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    // Method to retrieve the DB information as a Java list of objects
    static void snapshotToList(DataSnapshot snapshot) {
        FoodCategory.categories = new LinkedList<>();
        for (DataSnapshot catSnapshot : snapshot.getChildren()) {
            // For each category item, retrieve the data.

            // if there is data missing, the element will not be created
            boolean ok = true;
            for(String key : new String[]{"name", "description", "imagePath"}) {
                if (! catSnapshot.child(key).exists()) ok = false;
            }
            if (!ok) continue;

            int id = Integer.parseInt(catSnapshot.getKey());
            String name = catSnapshot.child("name").getValue().toString();
            String description = catSnapshot.child("description").getValue().toString();
            String imagePath = catSnapshot.child("imagePath").getValue().toString();

            FoodCategory newCategory = new FoodCategory(name, description, imagePath, id);
            // if it's already in the list, don't add it again (can happen due to connection problems)
            for (FoodCategory existing : categories) if (newCategory.equals(existing)) ok = false;

            // Finally if everything is OK, add it to the list
            if (ok) categories.add(newCategory);
            else FirebaseDatabase.getInstance().getReference().child("Categories").child(""+id).removeValue();
        }
    }

    static int greatestId() {
        int greatest = 0;
        for (FoodCategory cat : categories) if (cat.id > greatest) greatest = cat.id;
        if (categories.size()==0) greatest = new Random().nextInt();
        return greatest;
    }

    // Compare everything except the id (to scan for repeated items)
    public boolean equals(FoodCategory toCompare) {
        if (!name.equals(toCompare.name)) return false;
        if (!description.equals(toCompare.description)) return false;
        return true;
    }

    // Get a FoodCategory object from the ID
    public static FoodCategory fromID(int id) {
        for (FoodCategory cat : categories) if (id == cat.id) return cat;
        return null;
    }

    // Build an array of categories from a String of IDs
    public static FoodCategory[] categoriesFromIDs(String categoryIDs) {
        String[] IDs = categoryIDs.split(";");
        FoodCategory[] array = new FoodCategory[IDs.length];
        for (int i=0; i<array.length; i++) array[i] = FoodCategory.fromID(Integer.parseInt(IDs[i]));
        return array;
    }

    // Build a String of IDs from an array of categories
    public static String IDsFromCategories(FoodCategory[] array) {
        // The categories attribute will be the IDs of the categories separated by semicolon. There is always at least One category
        StringBuilder categoryIDs = new StringBuilder(array[0].getId());
        // if there are more, we add them
        for (int i=1; i<array.length; i++) categoryIDs.append(";"+array[i].getId());

        return categoryIDs.toString();
    }

    @Override
    public String toString(){
        return "ID " + id + " NAME " + name + " DESC " + description;
    }
}
