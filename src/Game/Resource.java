package Game;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Resource {

    // ATTRIBUTS

    public String name;
    public String description;
    private String pathToImg;
    private int price;
    private long timeToRecolt;
    private Date recoltCompletionTime;

    // SETTERS

    void setCompletionTime(Date actualDate) {
        this.recoltCompletionTime = new Date(actualDate.getTime() + timeToRecolt);
    }

    // GETTERS

    String getProperties(){
        return pathToImg;
    }

    // CONSTRUCTEUR

    void Resource(String name, String description, int price, String pathToImg) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pathToImg = pathToImg;
    }

    // METHODES

    String getRemainingTime(){
        long remainingTime = recoltCompletionTime.getTime() - new Date().getTime();
        long hours = TimeUnit.SECONDS.toHours(remainingTime);
        long minutes = TimeUnit.SECONDS.toMinutes(remainingTime) % 60;
        long seconds = remainingTime % 60;

        if (hours == 0 && minutes == 0) {
            if (seconds <= 9) {
                return String.format("0%d seconds remaining", seconds);
            }
            else {
                return String.format("%d seconds remaining", seconds);
            }
        } else if (hours == 0) {
            return String.format("%d:%d time remaining", hours, seconds);
        }
        else {
            return String.format("%d hour and %d:%d time remaining", hours, minutes, seconds);
        }
    }



}

