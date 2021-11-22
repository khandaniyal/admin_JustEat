package com.example.admin_justeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<Drawable> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //View view = findViewById(getDrawable(R.drawable.pollo2));
        names.add("Pollo a la brasa");
        names.add("pollo a la brasa 2");
        images.add(getDrawable(R.drawable.pollo2));//Me he quedado aquí
        images.add(getDrawable(R.drawable.pollo2));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        Log.i("arrayList", ""+names.size());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(names, images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

}