package com.grup2.jaestic_admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaestic_admin.R;

import java.util.LinkedList;

public class DishesRVAdapter extends RecyclerView.Adapter<DishesRVAdapter.ViewHolder> {
    //private static FragmentManager fragmentManager;
    //private  ArrayList<String> array_names;
    //private  ArrayList<Drawable> array_imagePaths;
    private LinkedList<Dish> dishes;
    private Context context;
    private int myLayoutID;
    private ItemClickListener myListener;
    private LayoutInflater myInflater;

    // private ArrayList<String> array_definition;

    public DishesRVAdapter(LinkedList<Dish> currentDish, Context c, int layoutId){
       // array_names = arrN;
        //array_imagePaths = arrI;
        dishes = currentDish;
        context = c;
        myLayoutID = layoutId;
        this.myInflater = LayoutInflater.from(context);
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
        holder.catFoodName.setText(dishes.get(position).getName());//Return the Names
        holder.catImgFood.setImageDrawable(dishes.get(position).getImagePath());//Return the path
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView catFoodName;
        ImageView catImgFood;
        //TextView Definition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i("arrayList2", ""+dishes.size());
            catFoodName = itemView.findViewById(R.id.txtDishName2);
            catImgFood = itemView.findViewById(R.id.imageDishPhoto3);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) { if(myListener != null) myListener.OnItemClick(view, getAdapterPosition()); }
    }
    //get current id item (temporary)
    public Dish getItem(int id){
        return dishes.get(id);
    }
    public void setClickListener(ItemClickListener itemClickListener){
        this.myListener = itemClickListener;
    }
    //temporary interface->moving into a interface package
    public interface ItemClickListener{
        void OnItemClick(View v, int pos);
    }
}