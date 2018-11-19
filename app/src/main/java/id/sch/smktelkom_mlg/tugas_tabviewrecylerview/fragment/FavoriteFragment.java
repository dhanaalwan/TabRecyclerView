package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.R;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter.FavoriteAdapter;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.FavoriteModel;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.realm.RealmHelperFavorite;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteFragment extends android.support.v4.app.Fragment {

    RealmHelperFavorite realmHelper;
    RecyclerView rvFavorite;
    FavoriteAdapter.MyListener myListener;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<FavoriteModel> mListm = new ArrayList<>();
    int idMeals;

    public FavoriteFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.myListener = (FavoriteAdapter.MyListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavorite = view.findViewById(R.id.recycler_view_favorite);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshFavorite);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFavorite();
            }
        });
        rvFavorite.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvFavorite.setLayoutManager(linearLayoutManager);

        Realm.init(getContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);
        realmHelper = new RealmHelperFavorite(realm);

//        ApiInterface favoriteService = ApiService.getClient().create(ApiInterface.class);
//        Call<Items> callCategories = favoriteService.getDetail(getActivity().getIntent().getExtras().getInt("id"));
//        callCategories.enqueue(new Callback<Items>() {
//            @Override
//            public void onResponse(Call<Items> call, Response<Items> response) {
//                idMeals = response.body().getMeals().get(0).getIdMeal();
//            }
//
//            @Override
//            public void onFailure(Call<Items> call, Throwable t) {
//
//            }
//        });

//        rvFavorite.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvFavorite, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Snackbar snackbar = Snackbar
//                        .make(view, mListm.get(position).getStrMeal(), Snackbar.LENGTH_LONG)
//                        .setAction("Remove meal from Favorite", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //delete data
//                                realmHelper.delete("idMeal", idMeals);
//
//                                Toast.makeText(getContext(), "Meal removed", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                snackbar.show();
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        }));

        List<FavoriteModel> favoriteModels = realmHelper.getAllFood();
        FavoriteAdapter favoriteAdapter;
        favoriteAdapter = new FavoriteAdapter(favoriteModels, myListener);
        rvFavorite.setAdapter(favoriteAdapter);
    }

    private void refreshFavorite() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Stop animation (This will be after 3 seconds)
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);

        List<FavoriteModel> favoriteModels = realmHelper.getAllFood();
        FavoriteAdapter favoriteAdapter;
        favoriteAdapter = new FavoriteAdapter(favoriteModels, myListener);
        rvFavorite.setAdapter(favoriteAdapter);

    }

    private void setAdapter() {

        List<FavoriteModel> foodModels = realmHelper.getAllFood();
        FavoriteAdapter favoriteAdapter;
        favoriteAdapter = new FavoriteAdapter(foodModels, myListener);
        rvFavorite.setAdapter(favoriteAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }
}
