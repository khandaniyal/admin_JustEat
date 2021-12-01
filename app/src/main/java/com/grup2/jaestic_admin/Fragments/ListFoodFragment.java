package com.grup2.jaestic_admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaestic_admin.R;
import com.grup2.jaestic_admin.Model.FoodCategory;
import com.grup2.jaestic_admin.Model.FoodCategory2;
//import com.grup2.jaestic_admin.R;
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

        return view;
    }

}