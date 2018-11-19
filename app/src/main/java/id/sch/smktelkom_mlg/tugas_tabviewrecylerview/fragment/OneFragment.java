package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.R;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter.FoodAdapter;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter.MapsAdapter;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Items;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Places;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.realm.RealmHelperFavorite;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.rest.ApiInterface;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.rest.ApiService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneFragment extends Fragment {

    RealmHelperFavorite realmHelperFavorite;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FoodAdapter.mClickListener mListener;
    private RecyclerView recyclerViewVertical, recyclerViewHorizontal;

    public OneFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mListener = (FoodAdapter.mClickListener) context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        recyclerViewVertical = view.findViewById(R.id.recycler_view);
        recyclerViewHorizontal = view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutOne);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });


        ApiInterface apiInterface2 = ApiService.getClient().create(ApiInterface.class);

        Call<Items> mealcall = apiInterface2.getData();
        mealcall.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {

                Log.wtf("Data", response.body().getMeals().get(0).getMeal());
                setRecyclerViewVertical(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                Log.wtf("hasil with param recycler view vertical", t.getMessage());
            }
        });

        Realm.init(getContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);
        realmHelperFavorite = new RealmHelperFavorite(realm);

        setRvMaps();

        return view;
    }

    private void refreshContent() {
        Toast.makeText(getContext(), "Wait a second", Toast.LENGTH_SHORT).show();

        if (recyclerViewVertical.getAdapter() == null) {
            Toast.makeText(getContext(), "Pastikan Jaringan Anda Lancar", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Stop animation (This will be after 3 seconds)
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

        ApiInterface apiInterface2 = ApiService.getClient().create(ApiInterface.class);

        Call<Items> mealcall = apiInterface2.getData();
        mealcall.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {

                Log.wtf("Data", response.body().getMeals().get(0).getMeal());
                setRecyclerViewVertical(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                Log.wtf("hasil with param recycler view vertical", t.getMessage());
            }
        });
    }

    private void setRecyclerViewVertical(ArrayList<Items.itemFood> meals) {
        recyclerViewVertical.setHasFixedSize(false);
        recyclerViewVertical.setBackgroundColor(Color.rgb(204, 204, 204));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewVertical.setLayoutManager(linearLayoutManager);
        FoodAdapter recyclerFoodAdapter = new FoodAdapter(meals, this.mListener);
        recyclerViewVertical.setAdapter(recyclerFoodAdapter);
    }

    public void setRvMaps() {
        List<Places> mapsModelArrayList = realmHelperFavorite.getAllPlaces();
        recyclerViewHorizontal.setHasFixedSize(false);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHorizontal.setAdapter(new MapsAdapter(mapsModelArrayList));
    }
}
