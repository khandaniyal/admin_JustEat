package com.grup2.jaestic_admin;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grup2.jaestic_admin.Model.Dish;
import com.grup2.jaestic_admin.Model.FoodCategory;

// Class to manage database interactions
public class DBHelper {
    // public static void addOrModifyUser(User user) {
    // mUsers.child(user.getId());
    // }

    public static void addOrModifyFood(Dish food) {
        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference()
                .child("Foods")
                .child(""+food.getId());
        catRef.child("name").setValue(food.getName());
        catRef.child("description").setValue(food.getDescription());
        catRef.child("imagePath").setValue(food.getImagePath());
        catRef.child("price").setValue(food.getPrice());
    }

    public static void addOrModifyCategory(FoodCategory cat) {
        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference()
                .child("Categories")
                .child(""+cat.getId());
        catRef.child("name").setValue(cat.getName());
    }
}