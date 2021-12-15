package com.grup2.jaestic_admin.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaestic_admin.R;
import com.grup2.jaestic_admin.Model.FoodCategory;
//import com.grup2.jaestic_admin.R;
import com.grup2.jaestic_admin.DishesRVAdapter;
import com.grup2.jaestic_admin.recyclerview.CategoryRVAdapter;

import java.util.ArrayList;


public class ListFoodFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.category_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        CategoryRVAdapter adapter = new CategoryRVAdapter(FoodCategory.categories, getContext(), R.layout.cardview_category);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        //_______________________________//Dishes adapter
        //Names and images of the dishes
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Drawable> images = new ArrayList<>();
        //adding test images
        for (int i = 0; i<20; i++){
            names.add("Pollo a la brasa "+i);
            images.add(this.getActivity().getDrawable(R.drawable.pollo2));
        }
        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerviewDishes);
        Log.i("arrayList", ""+names.size());
        DishesRVAdapter adapterDishes = new DishesRVAdapter(names, images);
        recyclerView2.setAdapter(adapterDishes);
        recyclerView2.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        return view;
    }

}