package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapterTwo extends RecyclerView.Adapter<FoodAdapterTwo.ViewHolder> {
    private static final String TAG = "FoodAdapterTwo";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private Context mContext;

    public FoodAdapterTwo(Context mContext, ArrayList<String> mNames, ArrayList<Integer> mImageUrls) {
        this.mNames = mNames;
        this.mImageUrls = mImageUrls;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_row_two, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);
        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
        }
    }
}
