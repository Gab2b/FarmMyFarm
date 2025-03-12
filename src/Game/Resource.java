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
    protected Date recoltCompletionTime;
    protected boolean retrieveState = false;
    protected int timeToRecoltInMinutes;

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

    String getGrownOne(){
        return pathToImg.substring(0, pathToImg.indexOf("Almost")) + "Grown";}

    String getName() {
        return name;
    }

    int getTimeToRecoltInMinutes() {
        return timeToRecoltInMinutes;
    }

    // CONSTRUCTEUR

    public Resource(String name, String description, int price, String pathToImg, boolean isSaleable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pathToImg = pathToImg;
        this.isSaleable = isSaleable;
        this.timeToRecoltInMinutes = 1;
        setCompletionTime(this.timeToRecoltInMinutes);
    }

    // METHODES

    abstract boolean isReady();
    abstract boolean reduceNumberOfResources();

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
            retrieveState = true;
            return "Time's up";
        }

        long hours = TimeUnit.MILLISECONDS.toHours(remainingTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60;
        long seconds = (remainingTime/1000) % 60;

        if (hours == 0 && minutes == 0) {
            return String.format("%02d seconds remaining", seconds);
        } else if (hours == 0) {
            return String.format("%02d:%02d time remaining", minutes, seconds);
        }
        else {
            return String.format("%d hour(s) and %02d:%02d time remaining", hours, minutes, seconds);
        }
    }

    boolean checkIfReady() {
        if (getRemainingTime().equals("Time's up")) {
            retrieveState = true;
            return true;
        }
        return false;
    }

    abstract ArrayList<Resource> returnResources();

}

class Wheat extends Resource {
    public Wheat() {
        super("Wheat", "A common grain crop used to make bread and animal feed. Grows in fields and is harvested for its golden seeds." , 5 , "wheatAlmostGrown", false);
    }

    @Override
    ArrayList<Resource> returnResources() {
        ArrayList<Resource> resources = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            resources.add(new Wheat());
        }
        return resources;
    }

    @Override
    boolean reduceNumberOfResources() {
        return false;
    }

    @Override
    boolean isReady() {
        return getRemainingTime().equals("Time's up");
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

    @Override
    boolean reduceNumberOfResources() {
        return false;
    }

    @Override
    ArrayList<Resource> returnResources() {
        ArrayList<Resource> resources = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            resources.add(new RedBeet());
        }
        return resources;
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

    @Override
    boolean reduceNumberOfResources() {
        return false;
    }

    @Override
    ArrayList<Resource> returnResources() {
        ArrayList<Resource> resources = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            resources.add(new Carrot());
        }
        return resources;
    }
}

class Seeds extends Resource {
    public Seeds() {
        super("Seeds", "Small seeds planted to grow crops like wheat or corn, mainly used to feed chickens on the farm.", 5, "filledField", false);
    }

    @Override
    boolean isReady() {
        return true;
    }

    @Override
    boolean reduceNumberOfResources() {
        return false;
    }

    @Override
    ArrayList<Resource> returnResources() {
        return new ArrayList<>();
    }
}