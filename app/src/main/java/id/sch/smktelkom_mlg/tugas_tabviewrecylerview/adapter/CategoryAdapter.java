package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.R;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Categories;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Response<Categories> mData;

    public CategoryAdapter(Response<Categories> response) {
        this.mData = response;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        holder.setData(mData.body().getCategories().get(position).getName(), mData.body().getCategories().get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.body().getCategories().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view);
            image = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), title.getText() + " clicked!!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(String xtitle, String ximage) {
            title.setText(xtitle);
            Picasso.get().load(ximage).into(image);
        }
    }
}
