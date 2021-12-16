package com.grup2.jaestic_admin.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.grup2.jaestic_admin.R;
import com.example.jaestic_admin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grup2.jaestic_admin.Model.FoodCategory;
import com.grup2.jaestic_admin.recyclerview.CategoryRVAdapter;


public class AddCategory extends Fragment implements CategoryRVAdapter.ItemClickListener {
    private CategoryRVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        FloatingActionButton addCatButton = (FloatingActionButton) view.findViewById(R.id.floatAddButton);
        //recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.rvCategoryEditor);
        adapter = new CategoryRVAdapter(FoodCategory.categories, getContext(), R.layout.added_category_cardview);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        addCatButton.setOnClickListener(e->{
            FoodCategory addCategory = new FoodCategory();
            showAddCategoryDialog(addCategory);
        });
        return view;
    }
    //for the recyclerview on itemview click
    @Override
    public void OnItemClick(View v, int pos) {
        Toast.makeText(getContext(), "ITEM CLICKED " + adapter.getItem(pos), Toast.LENGTH_SHORT).show();
    }
    void showAddCategoryDialog(FoodCategory addCategory){
        LayoutInflater dialog = LayoutInflater.from(getContext());
        View dialogView = dialog.inflate(R.layout.dialog_add_category, null);
        //
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(dialogView);
        //user input
        final EditText catName = (EditText) dialogView.findViewById(R.id.etxtInputCatName);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addCategory.categories.add(new FoodCategory(catName.getText().toString()));
                        Toast.makeText(getContext(), catName.getText().toString() + " category saved", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) { dialog.cancel(); }});
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}