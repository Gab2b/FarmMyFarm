package Game;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Field {

    // ATTRIBUTS

    public int xcords;
    public int ycords;
    private int price;
    public boolean isDiscovered = true;
    private boolean isBought = false;
    private boolean isFilled = false;
    private String pathToImg;
    private Resource resourceFilled;

    // SETTERS

    void setFilled (boolean newState, Resource newResource) {
        isFilled = newState;
        resourceFilled = newResource;
    }

    // GETTERS

    public String getProperties(){
        return pathToImg;
    }

    public Resource getResource() {
        return resourceFilled;
    }

    public String getResourceFilled() {
        return resourceFilled.getName();
    }

    public boolean isFilled(){return isFilled;}

    public int getPrice() {
        return price;
    }
    // CONSTRUCTEUR

    public Field(int x, int y, int price, boolean isOwned, String pathToImg) {
        this.xcords = x;
        this.ycords = y;
        this.price = price;
        this.isBought = isOwned;
        this.pathToImg = pathToImg;
    }
}

