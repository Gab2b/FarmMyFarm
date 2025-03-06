package Game;

import java.util.ArrayList;

public class Player {
    protected int id;
    protected int coin;
    protected ArrayList<Resource> ownedResources = new ArrayList<Resource>();
    protected ArrayList<Field> ownedFields = new ArrayList<Field>();

    public boolean canPayThis(int price) {
        if (coin > price) {
            return true;
        }
        return false;
    }

    public void buyThisField(Field newField, int price) {
        if (canPayThis(price)) {
            coin -= price;
            ownedFields.add(newField);
        }
        else {
            System.out.println("You don't have enough money!");
        }
    }

    public void buyThisResource(Resource newResource, int price) {
        if (canPayThis(price)) {
            coin -= price;
            ownedResources.add(newResource);
        }
        else {
            System.out.println("You don't have enough money!");
        }
    }
}
