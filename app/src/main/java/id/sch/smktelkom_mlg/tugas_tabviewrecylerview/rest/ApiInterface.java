package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.rest;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Categories;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Items;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("latest.php")
    Call<Items> getData();

    @GET("categories.php")
    Call<Categories> getCat();

    @GET("lookup.php")
    Call<Items> getDetail(@Query("i") int i);
}
