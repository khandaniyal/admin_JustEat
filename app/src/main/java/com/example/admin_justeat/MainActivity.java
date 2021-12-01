package com.example.admin_justeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener   {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<Drawable> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //View view = findViewById(getDrawable(R.drawable.pollo2));
        for (int i = 0; i<20; i++){
            names.add("Pollo a la brasa "+i);
            images.add(getDrawable(R.drawable.pollo2));
        }
        /*
        names.add("Pollo a la brasa");
        names.add("pollo a la brasa 2");
        images.add(getDrawable(R.drawable.pollo2));//Me he quedado aquí
        images.add(getDrawable(R.drawable.pollo2));
        names.add("pollo a la brasa 3");
        images.add(getDrawable(R.drawable.pollo2));
        names.add("pollo a la brasa 4");
        images.add(getDrawable(R.drawable.pollo2));
    */
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Log.i("arrayList", ""+names.size());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(names, images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));



        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemClick(int position) {
        names.get(position);
        Intent intent = new Intent(this, NewActivity.java );
        startActivity(intent);
    }


    /*
    public void showDish(FragmentManager fragmentManager){
        getFragmentManager().beginTransaction().replace(R.id.container,new Fragment_Dish()).commit();
    }

     */

}