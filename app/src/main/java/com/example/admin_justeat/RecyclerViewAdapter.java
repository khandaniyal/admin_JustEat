package com.example.admin_justeat;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private  ArrayList<String> array_names;
    private  ArrayList<Drawable> array_imagePaths;
   // private ArrayList<String> array_definition;

    public RecyclerViewAdapter(ArrayList<String> arrN , ArrayList<Drawable> arrI){
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
        holder.tagImage.setImageDrawable(array_imagePaths.get(position));


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
            Log.i("arrayList2", ""+array_names.size());
            tagName = itemView.findViewById(R.id.txtDishName);
            tagImage = itemView.findViewById(R.id.imageDishPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("RecyclerView", "onClick：" + getAdapterPosition());
                    new Fragment_Dish();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Layout_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();


                }
            });

        }
    }
}
