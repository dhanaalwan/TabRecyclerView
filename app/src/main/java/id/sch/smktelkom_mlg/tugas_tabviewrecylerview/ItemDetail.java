package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.FavoriteModel;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Items;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.realm.RealmHelperFavorite;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.rest.ApiInterface;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.rest.ApiService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetail extends AppCompatActivity {

    String strMealThumb, strCategory, strInstructions, strMeal, strArea;
    int idMeals;
    Toolbar toolbar;
    ImageView addFavorite, removeFavorite;
    RealmHelperFavorite realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        toolbar = findViewById(R.id.toolbarDetail);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addFavorite = findViewById(R.id.ivAdd);
        removeFavorite = findViewById(R.id.ivRemove);

        final FavoriteModel favoriteModel = new FavoriteModel();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);
        realmHelper = new RealmHelperFavorite(realm);

        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteModel.setIdMeal(idMeals);
                favoriteModel.setStrMeal(strMeal);
                favoriteModel.setStrMealThumb(strMealThumb);
                favoriteModel.setStrOrigin(strArea);
                favoriteModel.setStrCategory(strCategory);
                favoriteModel.setStrInstructions(strInstructions);

                realmHelper.save(favoriteModel);
                addFavorite.setVisibility(View.GONE);
                removeFavorite.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Meal added to favorite :)", Toast.LENGTH_SHORT).show();
            }
        });

        removeFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmHelper.delete("idMeal", idMeals);
                removeFavorite.setVisibility(View.GONE);
                addFavorite.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Meal removed from favorite :(", Toast.LENGTH_SHORT).show();
            }
        });

        ApiInterface favoriteService = ApiService.getClient().create(ApiInterface.class);
        Call<Items> callCategories = favoriteService.getDetail(getIntent().getExtras().getInt("id"));
        callCategories.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                idMeals = response.body().getMeals().get(0).getIdMeal();
                strMeal = response.body().getMeals().get(0).getMeal();
                strMealThumb = response.body().getMeals().get(0).getThumbnail();
                strArea = response.body().getMeals().get(0).getOrigin();
                strCategory = response.body().getMeals().get(0).getCategory();
                strInstructions = response.body().getMeals().get(0).getInstructions();
                setData();
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {

            }
        });
    }

    private void setData() {
        if (realmHelper.isNotAvailable(idMeals)) {
            removeFavorite.setVisibility(View.GONE);
            addFavorite.setVisibility(View.VISIBLE);
        } else {
            removeFavorite.setVisibility(View.VISIBLE);
            addFavorite.setVisibility(View.GONE);
        }

        toolbar.setTitle(strMeal);

        ImageView thumbnail = findViewById(R.id.thumbnail);
        TextView title = findViewById(R.id.title);
        TextView area = findViewById(R.id.tvArea);
        TextView category = findViewById(R.id.tvCategory);
        EditText instructions = findViewById(R.id.tvInstructions);
        instructions.setEnabled(false);

        LinearLayout container = findViewById(R.id.container);

        if (title == null) {
            Toast.makeText(ItemDetail.this, "Make sure that you have an internet connection!", Toast.LENGTH_LONG).show();
            container.setVisibility(View.GONE);
        } else {
            container.setVisibility(View.VISIBLE);
        }

        title.setText(strMeal);
        area.setText(strArea);
        category.setText(strCategory);
        instructions.setText(strInstructions);
        Picasso.get().load(strMealThumb).into(thumbnail);
        Picasso.get().load(strMealThumb).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable = new BitmapDrawable(getBaseContext().getResources(), bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}
