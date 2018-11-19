package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.realm;

import android.util.Log;

import java.util.List;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.FavoriteModel;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Places;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelperFavorite {

    Realm realm;

    public RealmHelperFavorite(Realm realm) {
        this.realm = realm;
    }

    public void save(final FavoriteModel favoriteModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "DB was created");
                    Number currentIdNum = realm.where(FavoriteModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    favoriteModel.setId(nextId);
                    FavoriteModel model = realm.copyToRealm(favoriteModel);
                } else {
                    Log.e("", "DB not Found");
                }
            }
        });
    }

    public boolean isNotAvailable(int idMeals) {
        RealmResults<FavoriteModel> results = realm.where(FavoriteModel.class).equalTo("idMeal", idMeals).findAll();
        return results.size() == 0;
    }

    // untuk memanggil semua data
    public List<FavoriteModel> getAllFood() {
        RealmResults<FavoriteModel> results = realm.where(FavoriteModel.class).findAll();
        return results;
    }

    // untuk menghapus data
    public void delete(String kolom, int idnya) {
        final RealmResults<FavoriteModel> results = realm.where(FavoriteModel.class).equalTo(kolom, idnya).findAll();
        results.size();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteFromRealm(0);
            }
        });
    }

    public void save_place(final Places places) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "DB was created");
                    Number currentIdNum = realm.where(Places.class).max("id");
                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    places.setId(nextId);
                    realm.copyToRealm(places);
                } else {
                    Log.e("", "DB not Found");
                }
            }
        });
    }

    public List<Places> getAllPlaces() {
        RealmResults<Places> results = realm.where(Places.class).findAll();
        return results;
    }
}
