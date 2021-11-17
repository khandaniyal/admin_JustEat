package com.example.admin_justeat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final ArrayList<String> array_names;
    private final ArrayList<View> array_imagePaths;
   // private ArrayList<String> array_definition;

    public RecyclerViewAdapter(ArrayList<String> arrN, ArrayList<View> arrI){
        array_names = arrN;
        array_imagePaths = arrI;
        //array_definition = arrD;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistfoodcategory, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(array_names.get(position));
    }

    @Override
    public int getItemCount() {
        return array_names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tagName;
        ImageView tagImage;
        //TextView Definition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.txtDishName);
            tagImage = itemView.findViewById(R.id.imageView2);
            //Definition = itemView.findViewById(R.id.userName);
        }
    }
}
