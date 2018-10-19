package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment {

    public View view;
    private RecyclerView recyclerView;
    private List<Food> foodList = new ArrayList<>();
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
        mAdapter = new FoodAdapter(foodList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Food food = foodList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), food.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();
        return view;
    }

    private void prepareMovieData() {
        Food food = new Food("Soto Lamongan", "Lamongan, East Java", R.drawable.soto);
        foodList.add(food);

        food = new Food("Pecel Blitar", "Blitar, East Java", R.drawable.pecel);
        foodList.add(food);

        food = new Food("Sate Madura", "Madura, East Java", R.drawable.sate);
        foodList.add(food);

        food = new Food("Coto Makassar", "Makassar, South Sulawesi", R.drawable.soto);
        foodList.add(food);

        food = new Food("Pecel Nganjuk", "Nganjuk, East Java", R.drawable.pecel);
        foodList.add(food);

        food = new Food("Sate Jakarta", "Jakarta, Indonesia", R.drawable.sate);
        foodList.add(food);

        // notify adapter about data set changes
        // so that it will render the list with new data
        mAdapter.notifyDataSetChanged();
    }
}
