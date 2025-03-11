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
        super(name, description, price, pathToImg, false);
        this.nourishment = nourishment;
        isFed = true;
        lastTimeFed = new Date();
        setCompletionTime(1);
    }

    // METHODES

    String getRemainingTime(){
        long remainingTime = recoltCompletionTime.getTime() - new Date().getTime();
        System.out.println("Remaining time: " + remainingTime);
        System.out.println("Remaining time: " + recoltCompletionTime);
        System.out.println("Remaining time: " + new Date().getTime());
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
        else if (isFed) {
            System.out.println("Animal is feeding");
            boolean nourishmentInInventory = false;
            System.out.println("Inv before that loop" + inventory);
            for (int i = 0; i < inventory.size(); i++) {
                    System.out.println("Nourishment in loop vs we got: " + inventory.get(i).getClass() + nourishment.getClass());
                if (inventory.get(i).getClass() == nourishment.getClass()) {
                    nourishmentInInventory = true;
                    inventory.remove(i);
                    System.out.println("Ending here");
                    break;
                }
            }
            System.out.println("Inv after that loop" + inventory);
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

    @Override
    boolean isReady() {
        return isFed && lastTimeFed.getTime() - new Date().getTime() > TimeUnit.SECONDS.toMillis(10);
    }

}

class Cow extends Animal {
    public Cow() {
        super("Cow", "A large farm animal raised for its milk, meat, and leather. Cows are essential for producing dairy products like cheese and butter.", 300, "healthyCows", new Wheat());
    }
}

class Pig extends Animal {
    public Pig() {
        super("Pig", "A farm animal raised for meat (pork) and sometimes for its leather. Pigs are known for being intelligent and often live in pens.", 100, "healthyPigs", new Carrot());
    }
}

class Chicken extends Animal {
    public Chicken() {
        super("Chicken", "A common farm animal raised for its eggs and meat. Chickens are easy to care for and lay eggs daily.", 40, "healthyChickens", new Seeds());
    }
}