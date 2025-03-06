package Game;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Resource {

    // ATTRIBUTS

    public String name;
    public String description;
    protected String pathToImg;
    protected int price;
    protected long timeToRecolt;
    protected Date recoltCompletionTime;

    // SETTERS

    void setCompletionTime(Date actualDate) {
        this.recoltCompletionTime = new Date(actualDate.getTime() + timeToRecolt);
    }

    // GETTERS

    String getProperties(){
        return pathToImg;
    }

    // CONSTRUCTEUR

    public Resource(String name, String description, int price, String pathToImg) {
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

class Wheat extends Resource {
    public Wheat() {
        super("Wheat", "A common grain crop used to make bread and animal feed. Grows in fields and is harvested for its golden seeds." , 5 , "../data/resources/infoCards/wheat.png");
    }
}
class RedBeet extends Resource {
    public RedBeet() {
        super("Red Beet", "A root vegetable with a deep red color. Grown underground and used in salads, soups, and animal feed." , 15 , "../data/resources/infoCards/redbeet.png");
    }
}

class Carrot extends Resource {
    public Carrot() {
        super("Carrot", "A root vegetable, typically orange, that grows underground. Used in meals, juices, and often as animal feed.", 35, "../data/resources/infoCards/carrot.png");
    }
}

class Seeds extends Resource {
    public Seeds() {
        super("Small Seeds", "Small seeds planted to grow crops like wheat or corn, mainly used to feed chickens on the farm.", 5, "../data/resources/infoCards/seeds.png");
    }
}