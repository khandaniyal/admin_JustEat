package com.grup2.jaestic_admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

//import com.grup2.jaestic_admin.R;
import com.example.jaestic_admin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AddCategory extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        FloatingActionButton addCatButton = (FloatingActionButton) view.findViewById(R.id.floatAddButton);

        addCatButton.setOnClickListener(e->{
            //add category
        });
        return view;
    }
}