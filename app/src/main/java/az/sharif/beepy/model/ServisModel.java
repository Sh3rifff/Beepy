package az.sharif.beepy.model;

public class ServisModel {

    String name,category;
    int image;

    public ServisModel(String name, String category, int image) {
        this.name = name;
        this.category = category;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public int getImage() {
        return image;
    }
}
