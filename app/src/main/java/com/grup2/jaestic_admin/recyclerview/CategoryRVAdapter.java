package com.grup2.jaestic_admin.recyclerview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grup2.jaestic_admin.Model.FoodCategory;
import com.grup2.jaestic_admin.Model.FoodCategory2;
import com.example.jaestic_admin.R;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;


public  class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private LinkedList<FoodCategory> foodCatName;
    private Context context;
    int myLayoutID;

    StorageReference mStorage = FirebaseStorage.getInstance().getReference();

    //In case we want to use the same view in diferent recycler views
    public CategoryRVAdapter(LinkedList<FoodCategory> arrN, Context c, int layoutId){
        foodCatName = arrN;
        context = c;
        myLayoutID = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(myLayoutID, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.catFoodName.setText(foodCatName.get(position).getName());
        //String imgName = foodCatName.get(position).getName();
        //Drawable imgDrawable = context.getResources().getDrawable(context.getResources()
        //                       .getIdentifier(imgName, "drawable", context.getPackageName()));
        //holder.catImgFood.setImageDrawable(imgDrawable);
        //setImageResource(foodCatName.get(position).getImagePath());;
    }

    @Override
    public int getItemCount() {
        return foodCatName.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView catFoodName;
        ImageView catImgFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catFoodName = itemView.findViewById(R.id.lblBurger);
            catImgFood = itemView.findViewById(R.id.imgCategory);
        }
    }
}