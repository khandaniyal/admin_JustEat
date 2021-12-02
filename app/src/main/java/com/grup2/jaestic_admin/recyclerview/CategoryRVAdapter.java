package com.grup2.jaestic_admin.recyclerview;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jaestic_admin.R;
import com.grup2.jaestic_admin.Model.FoodCategory;
import java.util.LinkedList;

public  class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>  {
    private LinkedList<FoodCategory> foodCatName;
    private Context context;
    private int myLayoutID;
    private ItemClickListener myListener;
    private LayoutInflater myInflater;


    /*
     * Dynamic builder, we can use the same recyclerview to display categories but
     * in different ways or in different activities or fragments
     */
    public CategoryRVAdapter(LinkedList<FoodCategory> arrN, Context c, int layoutId){
        foodCatName = arrN;
        context = c;
        myLayoutID = layoutId;
        this.myInflater = LayoutInflater.from(context);

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

        //temporary image display using an integer -> change to string to geth db path
        //holder.catImgFood.setImageResource(foodCatName.get(position).getImageName());
    }

    @Override
    public int getItemCount() {
        return foodCatName.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView catFoodName;
        ImageView catImgFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catFoodName = itemView.findViewById(R.id.lblBurger);
            catImgFood = itemView.findViewById(R.id.imgCategory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) { if(myListener != null) myListener.OnItemClick(view, getAdapterPosition()); }
    }
    //get current id item (temporary)
    public FoodCategory getItem(int id){
        return foodCatName.get(id);
    }
    public void setClickListener(ItemClickListener itemClickListener){
        this.myListener = itemClickListener;
    }
    //temporary interface->moving into a interface package
    public interface ItemClickListener{
        void OnItemClick(View v, int pos);
    }
}