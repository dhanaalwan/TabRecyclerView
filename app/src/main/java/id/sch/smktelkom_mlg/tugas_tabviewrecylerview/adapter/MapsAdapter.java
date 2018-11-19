package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.R;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Places;

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.ViewHolder> {

    List<Places> mapsModelArrayList;

    public MapsAdapter(List<Places> mapsModelArrayList) {
        this.mapsModelArrayList = mapsModelArrayList;
    }

    @NonNull
    @Override
    public MapsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.maps_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MapsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.img.setImageURI(Uri.parse(mapsModelArrayList.get(i).getImage()));
        viewHolder.txt.setText(mapsModelArrayList.get(i).getNama());
    }

    @Override
    public int getItemCount() {
        return mapsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageMaps);
            txt = itemView.findViewById(R.id.textMaps);
        }
    }
}
