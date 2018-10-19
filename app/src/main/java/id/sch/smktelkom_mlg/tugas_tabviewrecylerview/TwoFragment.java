package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TwoFragment extends Fragment {
    private static final String TAG = "TwoFragment";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    public TwoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add(R.drawable.soto);
        mNames.add("Soto");

        mImageUrls.add(R.drawable.pecel);
        mNames.add("Pecel");

        mImageUrls.add(R.drawable.sate);
        mNames.add("Sate");

        mImageUrls.add(R.drawable.soto);
        mNames.add("Soto Kambing");

        mImageUrls.add(R.drawable.sate);
        mNames.add("Sate Sapi");

        mImageUrls.add(R.drawable.pecel);
        mNames.add("Pecel Pincuk");

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapterTwo adapter = new FoodAdapterTwo(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
}
