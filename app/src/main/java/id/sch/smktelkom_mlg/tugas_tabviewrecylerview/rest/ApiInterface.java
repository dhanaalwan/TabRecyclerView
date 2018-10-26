package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.rest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("api/json/v1/1/latest.php")
    Call<JSONResponses> getJSON();
}
