package Game;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Resource {

    // ATTRIBUTS

    public String name;
    public String description;
    protected String pathToImg;
    protected int price;
    protected boolean isSaleable;
    protected long timeToRecolt;
    protected Date recoltCompletionTime;

    // SETTERS

    void setCompletionTime(int timeInMinutes) {
        if (timeInMinutes < 0) {
            throw new IllegalArgumentException("Le temps en minutes ne peut pas être négatif.");
        }
        this.recoltCompletionTime = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(timeInMinutes));
    }

    // GETTERS

    String getProperties(){
        return pathToImg;
    }

    String getName() {
        return name;
    }

    // CONSTRUCTEUR

    public Resource(String name, String description, int price, String pathToImg, boolean isSaleable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pathToImg = pathToImg;
        this.isSaleable = isSaleable;
    }

    // METHODES

    abstract boolean isReady();

    static void resourceFromFile(String resource, int quantity, ArrayList<Resource> ownedResources) {
        for (int i = 0; i < quantity; i++) {
            switch (resource) {
                case "Wheat":
                    ownedResources.add(new Wheat());
                    break;
                case "Red Beet":
                    ownedResources.add(new RedBeet());
                    break;
                case "Carrot":
                    ownedResources.add(new Carrot());
                    break;
                case "Small Seeds":
                    ownedResources.add(new Seeds());
                    break;
                case "Cow":
                    ownedResources.add(new Cow());
                    break;
                case "Pig":
                    ownedResources.add(new Pig());
                    break;
                case "Chicken":
                    ownedResources.add(new Chicken());
                    break;
            }
        }
    }


    String getRemainingTime(){
        long remainingTime = recoltCompletionTime.getTime() - new Date().getTime();
        if (remainingTime <= 0) {
            return "Time's up";
        }

        long hours = TimeUnit.MILLISECONDS.toHours(remainingTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    String printRemainingTime(){
        String remainingTime = getRemainingTime();
        if (Integer.parseInt(remainingTime.substring(0, 2)) == 0 && Integer.parseInt(remainingTime.substring(3,5)) == 0) {
            if (Integer.parseInt(remainingTime.substring(6,8)) <= 9) {
                return String.format("0%d seconds remaining", Integer.parseInt(remainingTime.substring(6,8)));
            }
            else {
                return String.format("%d seconds remaining", Integer.parseInt(remainingTime.substring(6,8)));
            }
        } else if (Integer.parseInt(remainingTime.substring(0, 2)) == 0) {
            return String.format("%d:%d time remaining", Integer.parseInt(remainingTime.substring(0, 2)), Integer.parseInt(remainingTime.substring(6,8)));
        }
        else {
            return String.format("%d hour and %d:%d time", Integer.parseInt(remainingTime.substring(0, 2)), Integer.parseInt(remainingTime.substring(3,5)), Integer.parseInt(remainingTime.substring(6,8)));
        }
    }

}

class Wheat extends Resource {
    public Wheat() {
        super("Wheat", "A common grain crop used to make bread and animal feed. Grows in fields and is harvested for its golden seeds." , 5 , "wheatAlmostGrown", false);
    }

    @Override
    boolean isReady() {
        return getRemainingTime().equals("Wheat");
    }
}
class RedBeet extends Resource {
    public RedBeet() {
        super("RedBeet", "A root vegetable with a deep red color. Grown underground and used in salads, soups, and animal feed." , 15 , "redBeetAlmostGrown", false);
    }

    @Override
    boolean isReady() {
        return true;
    }
}

class Carrot extends Resource {
    public Carrot() {
        super("Carrot", "A root vegetable, typically orange, that grows underground. Used in meals, juices, and often as animal feed.", 35, "carrotAlmostGrown", false);
    }

    @Override
    boolean isReady() {
        return true;
    }
}

class Seeds extends Resource {
    public Seeds() {
        super("Seeds", "Small seeds planted to grow crops like wheat or corn, mainly used to feed chickens on the farm.", 5, "", false);
    }

    @Override
    boolean isReady() {
        return true;
    }
}