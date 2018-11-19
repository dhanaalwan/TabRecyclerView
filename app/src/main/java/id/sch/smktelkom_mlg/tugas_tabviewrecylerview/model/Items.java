package id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Items {
    @SerializedName("meals")
    private ArrayList<itemFood> meal;

    public ArrayList<itemFood> getMeals() {
        return meal;
    }

    public class itemFood {
        @SerializedName("idMeal")
        private int idMeal;

        @SerializedName("strMeal")
        private String meal;

        @SerializedName("strMealThumb")
        private String thumbnail;

        @SerializedName("strArea")
        private String origin;

        @SerializedName("strCategory")
        private String category;

        @SerializedName("strTags")
        private String tags;

        @SerializedName("strInstructions")
        private String instructions;

        @SerializedName("strSource")
        private String source;

        @SerializedName("strYoutube")
        private String youtube;

        public Integer getIdMeal() {
            return idMeal;
        }

        public void setIdMeal(int idMeal) {
            this.idMeal = idMeal;
        }

        public String getMeal() {
            return meal;
        }

        public void setMeal(String meal) {
            this.meal = meal;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getYoutube() {
            return youtube;
        }

        public void setYoutube(String youtube) {
            this.youtube = youtube;
        }
    }
}
