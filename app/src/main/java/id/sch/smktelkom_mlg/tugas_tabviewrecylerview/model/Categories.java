package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Categories {
    @SerializedName("categories")
    private ArrayList<itemCategories> categories;

    public ArrayList<itemCategories> getCategories() {
        return categories;
    }

    public class itemCategories {
        @SerializedName("strCategory")
        private String name;

        @SerializedName("strCategoryThumb")
        private String image;

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }
    }
}
