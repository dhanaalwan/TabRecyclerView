package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.R;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.FavoriteModel;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.realm.RealmHelperFavorite;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    List<FavoriteModel> modelList;
    MyListener myListener;

    public FavoriteAdapter(List<FavoriteModel> favoriteModels, MyListener myListener) {
        this.modelList = favoriteModels;
        this.myListener = myListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);
        final RealmHelperFavorite realmHelper = new RealmHelperFavorite(realm);
        final int idMeals = modelList.get(position).getIdMeal();

        Picasso.get().load(modelList.get(position).getStrMealThumb()).into(holder.image);
        holder.title.setText(modelList.get(position).getStrMeal());
        holder.origin.setText(modelList.get(position).getStrOrigin());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public interface MyListener {
        void mOnClickListener(FavoriteModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, origin, hapus;
        ConstraintLayout showDataFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            origin = itemView.findViewById(R.id.origin);
            hapus = itemView.findViewById(R.id.deleteFromFavorite);
            showDataFavorite = itemView.findViewById(R.id.container);
        }
    }
}
