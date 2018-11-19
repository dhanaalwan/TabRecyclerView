package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.R;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Items;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    ArrayList<Items.itemFood> mData;
    mClickListener mListener;

    public FoodAdapter(ArrayList<Items.itemFood> mData, mClickListener mListener) {
        this.mData = mData;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_list_row_one, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(mData.get(position).getMeal(), mData.get(position).getOrigin(), mData.get(position).getThumbnail());
        final int idMeals = mData.get(position).getIdMeal();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.mOnClickListener(idMeals);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface mClickListener {
        void mOnClickListener(int id);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, origin;
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            origin = view.findViewById(R.id.origin);
            ConstraintLayout constraintLayout = itemView.findViewById(R.id.container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), title.getText() + " clicked!", Toast.LENGTH_SHORT).show();
                }
            });

            if (title == null) {
                Toast.makeText(itemView.getContext(), "Make sure that you have an internet connection!", Toast.LENGTH_SHORT).show();
                constraintLayout.setVisibility(View.GONE);
            } else {
                constraintLayout.setVisibility(View.VISIBLE);
            }
        }

        public void setData(String xtitle, String xorigin, String ximage) {
            title.setText(xtitle);
            Picasso.get().load(ximage).into(image);
            origin.setText(xorigin);
        }
    }
}
