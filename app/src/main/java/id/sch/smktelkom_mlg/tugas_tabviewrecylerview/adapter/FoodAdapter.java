package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.Food;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    private ArrayList<Food> food;


    public FoodAdapter(ArrayList<Food> foodList) {
        this.food = food;
    }

    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_row_one, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food foods = food.get(position);
        holder.title.setText(foods.getStrMeal());
        holder.origin.setText(foods.getStrArea());
        holder.image.setImageResource(foods.getStrMealThumb());
    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, origin;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            origin = view.findViewById(R.id.origin);
            image = view.findViewById(R.id.image);
        }
    }
}
