package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter.FavoriteAdapter;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter.FoodAdapter;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.fragment.FavoriteFragment;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.fragment.OneFragment;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.FavoriteModel;

public class MainActivity extends AppCompatActivity implements FoodAdapter.mClickListener, FavoriteAdapter.MyListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Meals");
        adapter.addFragment(new FavoriteFragment(), "Favorite");
        adapter.addFragment(new MapsActivity(), "Maps");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void mOnClickListener(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        Intent intent = new Intent(MainActivity.this, ItemDetail.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void mOnClickListener(FavoriteModel model) {
        Bundle bundle = new Bundle();
        bundle.putInt("Id", model.getId());
        bundle.putInt("IdMakanan", model.getIdMeal());
        bundle.putString("StrGambarMakanan", model.getStrMealThumb());
        bundle.putString("StrNamaMakanan", model.getStrMeal());
        bundle.putString("StrKategori", model.getStrCategory());
        bundle.putString("StrArea", model.getStrOrigin());
        bundle.putString("StrInstruksi", model.getStrInstructions());
        Intent i = new Intent(MainActivity.this, ItemDetail.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_data_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_menu) {
            Intent i = new Intent(getApplicationContext(), SharedPreferenceActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
