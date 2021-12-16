package com.grup2.jaestic_admin;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaestic_admin.R;

import java.util.ArrayList;

public class DishesRVAdapter extends RecyclerView.Adapter<DishesRVAdapter.ViewHolder> {
    private static FragmentManager fragmentManager;
    private ArrayList<String> array_names;
    private ArrayList<Drawable> array_imagePaths;
    // private ArrayList<String> array_definition;

    public DishesRVAdapter(ArrayList<String> arrN , ArrayList<Drawable> arrI){
        array_names = arrN;
        array_imagePaths = arrI;
        //array_definition = arrD;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_dishes, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(array_names.get(position));
        //holder.tagImage.setImageDrawable(array_imagePaths.get(position));
    }
    @Override
    public int getItemCount() {
        return array_names.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;
        ImageView tagImage;
        //TextView Definition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i("arrayList2", ""+array_names.size());
            tagName = itemView.findViewById(R.id.txtDishName2);
            tagImage = itemView.findViewById(R.id.imageDishPhoto3);
        }
    }
}
