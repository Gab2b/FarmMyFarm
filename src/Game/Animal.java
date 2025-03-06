package Game;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class Animal extends Resource {

    // ATTRIBUTS

    protected boolean isFed;
    protected Date lastTimeFed;
    protected boolean isDead;
    protected Resource nourishment;

    // SETTERS

    // GETTERS

    String getProperties(){
        return this.pathToImg;
    }

    // CONSTRUCTEUR

    public Animal(String name, String description, int price, String pathToImg, Resource  nourishment) {
        super(name, description, price, pathToImg);
        this.nourishment = nourishment;
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

    void feed(ArrayList<Resource> inventory) {
        if (isDead) {
            System.out.println("Animal is dead you can't feed him");
        }
        else  {
            boolean nourishmentInInventory = false;
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i) == nourishment) {
                    nourishmentInInventory = true;
                    i = inventory.size();
                }
            }
            inventory.remove(nourishment);
            isFed = true;
            lastTimeFed = new Date();
        }
    }

    void checkIsAlive() {
        if (lastTimeFed.getTime() - new Date().getTime() > TimeUnit.MINUTES.toMillis(1))
        {
            isDead = true;
        }

    }

}

class Cow extends Animal {
    public Cow(Resource wheat) {
        super("Cow", "A large farm animal raised for its milk, meat, and leather. Cows are essential for producing dairy products like cheese and butter.", 300, "../data/resources/infoCards/cow.png", wheat);
    }
}

class Pig extends Animal {
    public Pig(Resource carrot) {
        super("Pig", "A farm animal raised for meat (pork) and sometimes for its leather. Pigs are known for being intelligent and often live in pens.", 100, "../data/resources/infoCards/pig.png", carrot);
    }
}

class Chicken extends Animal {
    public Chicken(Resource seeds) {
        super("Chicken", "A common farm animal raised for its eggs and meat. Chickens are easy to care for and lay eggs daily.", 40, "../data/resources/infoCards/cow.png", seeds);
    }
}