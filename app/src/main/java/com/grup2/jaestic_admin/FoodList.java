package com.grup2.jaestic_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.jaestic_admin.R;
import com.grup2.jaestic_admin.Model.Dish;

public class FoodList extends AppCompatActivity {
//Deprecated class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

     //   RecyclerViewAdapter adapter = new RecyclerViewAdapter(Dish.foods);

   //     recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
}