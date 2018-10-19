package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

public class Food {
    private String title, origin;
    private int image;

    public Food() {
    }

    public Food(String title, String origin, int image) {
        this.title = title;
        this.origin = origin;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
