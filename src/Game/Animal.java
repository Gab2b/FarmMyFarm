package Game;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public abstract class Animal extends Resource {

    // ATTRIBUTS

    protected boolean isFed = true;
    protected Date lastTimeFed = new Date();
    protected boolean isDead = false;
    protected Resource nourishment;
    protected int numberOfResources = 1;

    // SETTERS


    // GETTERS

    int getNumberOfResources() {
        return numberOfResources;
    }

    String getProperties(){
        return this.pathToImg;
    }

    boolean isAlive()  {
        return isDead;
    }

    boolean isFed() {
        return isFed;
    }

    // CONSTRUCTEUR

    public Animal(String name, String description, int price, String pathToImg, Resource  nourishment) {
        super(name, description, price, pathToImg, false);
        this.nourishment = nourishment;
        this.timeToRecoltInMinutes = 2;
        setCompletionTime(this.timeToRecoltInMinutes);
    }

    @Override
    ArrayList<Resource> returnResources() {
        return null;
    }

    // METHODES

    boolean reduceNumberOfResources() {
        numberOfResources = numberOfResources - 1;
        return numberOfResources == 0;
    }

    boolean feed(ArrayList<Resource> inventory) {
        if (isDead) {
            System.out.println("Animal is dead you can't feed him");
            return false;
        }
        else if (!isFed) {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).getClass() == nourishment.getClass()) {
                    inventory.remove(i);
                    isFed = true;
                    lastTimeFed = new Date();
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkIfHungry() {
            System.out.println(lastTimeFed.getTime());
            System.out.println(new Date().getTime() - lastTimeFed.getTime());
            System.out.println(TimeUnit.MINUTES.toMillis(1));
        if ((new Date().getTime() - lastTimeFed.getTime()) > TimeUnit.MINUTES.toMillis(1))
        {
            isFed = false;
            if ((new Date().getTime() - lastTimeFed.getTime()) > TimeUnit.MINUTES.toMillis(2)) {
                isDead = true;
            }
            return true;
        }
        return false;
    }

    abstract Resource retrieveResource();

    abstract Animal retrieveAnimal();

    @Override
    boolean isReady() {
        return checkIfReady();
    }

}

class Cow extends Animal {
    public Cow() {
        super("Cow", "A large farm animal raised for its milk, meat, and leather. Cows are essential for producing dairy products like cheese and butter.", 300, "healthyCows", new Wheat());
    }

    Resource retrieveResource() {
        return new Milk();
    }
    Animal retrieveAnimal() {
        return new Cow();
    }

}

class Pig extends Animal {
    public Pig() {
        super("Pig", "A farm animal raised for meat (pork) and sometimes for its leather. Pigs are known for being intelligent and often live in pens.", 100, "healthyPigs", new Carrot());
    }

    Resource retrieveResource() {
        return new Manure();
    }
    Animal retrieveAnimal() {
        return new Pig();
    }

}

class Chicken extends Animal {
    public Chicken() {
        super("Chicken", "A common farm animal raised for its eggs and meat. Chickens are easy to care for and lay eggs daily.", 40, "healthyChickens", new Seeds());
    }

    Resource retrieveResource() {
        return new Eggs();
    }
    Animal retrieveAnimal() {
        return new Chicken();
    }

}

class Milk extends Resource {
    public Milk() {
        super("Milk", "Freshly picked milk, straight from the cow. Pure, natural, and packed with nutrients—just like it was meant to be.", 0, "milk", false);
    }

    @Override
    boolean reduceNumberOfResources() {
        return false;
    }

    @Override
    ArrayList<Resource> returnResources() {
        return null;
    }

    @Override
    boolean isReady() {
        return retrieveState;
    }
}

class Manure extends Resource {
    public Manure() {
        super("Manure", "Manure is animal waste used as fertilizer to enrich soil and promote plant growth.", 0, "manure", false);
    }

    @Override
    boolean reduceNumberOfResources() {
        return false;
    }

    @Override
    ArrayList<Resource> returnResources() {
        return null;
    }

    @Override
    boolean isReady() {
        return retrieveState;
    }
}

class Eggs extends Resource {
    public Eggs() {
        super("Eggs", "Eggs are the oval reproductive bodies produced by birds, commonly used as food.", 0, "eggs", false);
    }

    @Override
    boolean reduceNumberOfResources() {
        return false;
    }

    @Override
    ArrayList<Resource> returnResources() {
        return null;
    }

    @Override
    boolean isReady() {
        return retrieveState;
    }
}