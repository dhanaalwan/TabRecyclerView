package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteModel extends RealmObject {

    @PrimaryKey

    private int id;
    private int idMeal;
    private String strMeal, strMealThumb, strOrigin, strCategory, strInstructions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(int idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrOrigin() {
        return strOrigin;
    }

    public void setStrOrigin(String strOrigin) {
        this.strOrigin = strOrigin;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }
}
