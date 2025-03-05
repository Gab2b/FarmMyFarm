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

    // SETTERS

    void setDiscovered (boolean newState) {
        isDiscovered = newState;
    }

    void setBought (boolean newState) {
        isBought = newState;
    }

    void setFilled (boolean newState) {
        isFilled = newState;

    }

    // GETTERS

    String getProperties(){
        return pathToImg;
    }

    // CONSTRUCTEUR

    void Field(int x, int y, int price, String pathToImg) {
        this.xcords = x;
        this.ycords = y;
        this.price = price;
        this.pathToImg = pathToImg;
    }
}

