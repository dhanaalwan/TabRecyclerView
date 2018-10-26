package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.Items;

import com.google.gson.annotations.SerializedName;

public class Items {
    @SerializedName("strMeal")
    private String meal;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strMealThumb")
    private String thumbnail;

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
