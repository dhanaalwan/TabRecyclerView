package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter.FoodAdapter;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.rest.ApiInterface;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.rest.JSONResponses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OneFragment extends Fragment {

    public View view;
    private RecyclerView recyclerView;
    private ArrayList<Food> data;
    private FoodAdapter mAdapter;

    public OneFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new FoodAdapter(data);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Food food = data.get(position);
                Toast.makeText(getActivity().getApplicationContext(), food.getStrMeal() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        loadJSON();
        return view;
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JSONResponses> call = apiInterface.getJSON();
        call.enqueue(new Callback<JSONResponses>() {
            @Override
            public void onResponse(Call<JSONResponses> call, Response<JSONResponses> response) {
                JSONResponses jsonResponses = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponses.getFood()));
                mAdapter = new FoodAdapter(data);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponses> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
