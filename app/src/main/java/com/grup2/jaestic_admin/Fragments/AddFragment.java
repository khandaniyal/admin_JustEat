package com.grup2.jaestic_admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

//import com.grup2.jaestic_admin.R;
import com.example.jaestic_admin.R;

public class AddFragment extends Fragment {

    private CardView addDish, addCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        //initialise cardviews
        addCategory = view.findViewById(R.id.crdAddCategory);
        addDish = view.findViewById(R.id.crdAddDish);
        addCategory.setOnClickListener(e->{
            getParentFragmentManager().beginTransaction()
                                      .replace(R.id.fragment_container, new AddCategory()).commit();

        });
        addDish.setOnClickListener(e->{
            //go to dish fragment
        });

        return view;
    }

}